package ru.barabo.learn.groupie.groupietest

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.*
import ru.barabo.learn.groupie.groupietest.binding.*
import ru.barabo.learn.groupie.groupietest.binding.items.*
import ru.barabo.learn.groupie.groupietest.databinding.ActivityMainBinding
import ru.barabo.learn.groupie.groupietest.decoration.CarouselItemDecoration
import ru.barabo.learn.groupie.groupietest.decoration.DebugItemDecoration
import ru.barabo.learn.groupie.groupietest.decoration.SwipeTouchCallback
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var groupAdapter: GroupAdapter<*>? = null
    private var layoutManager: GridLayoutManager? = null
    private var prefs: Prefs? = null

    private var gray: Int = 0
    private var betweenPadding: Int = 0
    private var rainbow200: IntArray? = null
    private var rainbow500: IntArray? = null

    private var infiniteLoadingSection: Section? = null
    private var swipeSection: Section? = null

    // Normally there's no need to hold onto a reference to this list, but for demonstration
    // purposes, we'll shuffle this list and post an update periodically
    private lateinit var updatableItems: ArrayList<UpdatableItem>

    // Hold a reference to the updating group, so we can, well, update it
    private var updatingGroup: Section? = null

    private val onItemClickListener = OnItemClickListener { item, _ ->
        if (item is CardItem) {
            if (!TextUtils.isEmpty(item.text)) {
                Toast.makeText(this@MainActivity, item.text, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val onItemLongClickListener = OnItemLongClickListener { item, _ ->
        if (item is CardItem) {
            if (!TextUtils.isEmpty(item.text)) {
                Toast.makeText(this@MainActivity, "Long clicked: " + item.text!!, Toast.LENGTH_SHORT).show()
                return@OnItemLongClickListener true
            }
        }
        false
    }

    private val touchCallback = object : SwipeTouchCallback(gray) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val item = groupAdapter!!.getItem(viewHolder.adapterPosition)
            // Change notification to the adapter happens automatically when the section is
            // changed.
            swipeSection!!.remove(item)
        }
    }

    private val onSharedPrefChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, _ ->
            // This is pretty evil, try not to do this
            groupAdapter!!.notifyDataSetChanged()
        }

    private val handler = Handler()
    private val onFavoriteListener = object : HeartCardItem.OnFavoriteListener {
        override fun onFavorite(item: HeartCardItem, favorite: Boolean) {
            // Pretend to make a network request
            handler.postDelayed(
                {
                // Network request was successful!
                item.setFavorite(favorite)
                item.notifyChanged(HeartCardItem.FAVORITE)
            }, 1000L)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        prefs = Prefs[this]

        gray = ContextCompat.getColor(this, R.color.background)
        betweenPadding = resources.getDimensionPixelSize(R.dimen.padding_small)
        rainbow200 = resources.getIntArray(R.array.rainbow_200)
        rainbow500 = resources.getIntArray(R.array.rainbow_500)

        groupAdapter = GroupAdapter<ViewHolder>()
        groupAdapter!!.setOnItemClickListener(onItemClickListener)
        groupAdapter!!.setOnItemLongClickListener(onItemLongClickListener)
        groupAdapter!!.spanCount = 12
        populateAdapter()
        layoutManager = GridLayoutManager(this, groupAdapter!!.spanCount)
        layoutManager!!.spanSizeLookup = groupAdapter!!.spanSizeLookup

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(HeaderItemDecoration(gray, betweenPadding))
        recyclerView.addItemDecoration(InsetItemDecoration(gray, betweenPadding))
        recyclerView.addItemDecoration(DebugItemDecoration(this))
        recyclerView.adapter = groupAdapter
        recyclerView.addOnScrollListener(object : InfiniteScrollListener(layoutManager) {

            override fun onLoadMore(currentPage: Int) {

                val color = rainbow200!![currentPage % rainbow200!!.size]
                for (i in 0..4) {
                    infiniteLoadingSection!!.add(CardItem(color))
                }
            }
        })

        val itemTouchHelper = ItemTouchHelper(touchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        binding.fab.setOnClickListener { startActivity(Intent(this@MainActivity, SettingsActivity::class.java)) }

        prefs!!.registerListener(onSharedPrefChangeListener)
    }

    private fun populateAdapter() {

        // Full bleed item
        val fullBleedItemSection = Section(HeaderItem(R.string.full_bleed_item))
        fullBleedItemSection.add(FullBleedCardItem(R.color.purple_200))
        groupAdapter!!.add(fullBleedItemSection)

        // Update in place group
        val updatingSection = Section()

        val onShuffleClicked = View.OnClickListener {
            val shuffled = ArrayList(updatableItems)
            shuffled.shuffle()
            updatingGroup!!.update(shuffled)

            // You can also do this by forcing a change with payload
            binding.recyclerView.post { binding.recyclerView.invalidateItemDecorations() }
        }
        val updatingHeader = HeaderItem(
            R.string.updating_group,
            R.string.updating_group_subtitle,
            R.drawable.shuffle,
            onShuffleClicked
        )
        updatingSection.setHeader(updatingHeader)
        updatingGroup = Section()
        updatableItems = ArrayList()
        for (i in 1..12) {
            updatableItems.add(UpdatableItem(rainbow200!![i], i))
        }
        updatingGroup!!.update(updatableItems)
        updatingSection.add(updatingGroup!!)
        groupAdapter!!.add(updatingSection)

        // Expandable group
        val expandableHeaderItem = ExpandableHeaderItem(R.string.expanding_group, R.string.expanding_group_subtitle)
        val expandableGroup = ExpandableGroup(expandableHeaderItem)
        for (i in 0..1) {
            expandableGroup.add(CardItem(rainbow200!![1]))
        }
        groupAdapter!!.add(expandableGroup)

        // Columns
        val columnSection = Section(HeaderItem(R.string.vertical_columns))
        val columnGroup = makeColumnGroup()
        columnSection.add(columnGroup)
        groupAdapter!!.add(columnSection)

        // Group showing even spacing with multiple columns
        val multipleColumnsSection = Section(HeaderItem(R.string.multiple_columns))
        for (i in 0..11) {
            multipleColumnsSection.add(SmallCardItem(rainbow200!![5]))
        }
        groupAdapter!!.add(multipleColumnsSection)

        // Swipe to delete (with add button in header)
        swipeSection = Section(HeaderItem(R.string.swipe_to_delete))
        for (i in 0..2) {
            swipeSection!!.add(SwipeToDeleteItem(rainbow200!![6]))
        }
        groupAdapter!!.add(swipeSection!!)

        // Horizontal carousel
        val carouselSection = Section(HeaderItem(R.string.carousel, R.string.carousel_subtitle))
        carouselSection.setHideWhenEmpty(true)
        val carousel = makeCarouselGroup()
        carouselSection.add(carousel)
        groupAdapter!!.add(carouselSection)

        // Update with payload
        val updateWithPayloadSection =
            Section(HeaderItem(R.string.update_with_payload, R.string.update_with_payload_subtitle))
        for (i in rainbow500!!.indices) {
            updateWithPayloadSection.add(HeartCardItem(rainbow200!![i], i.toLong(), onFavoriteListener))

        }
        groupAdapter!!.add(updateWithPayloadSection)

        // Infinite loading section
        infiniteLoadingSection = Section(HeaderItem(R.string.infinite_loading))
        groupAdapter!!.add(infiniteLoadingSection!!)
    }

    private fun makeColumnGroup(): ColumnGroup {
        val columnItems = ArrayList<ColumnItem>()
        for (i in 1..5) {
            // First five items are red -- they'll end up in a vertical column
            columnItems.add(ColumnItem(rainbow200!![0], i))
        }
        for (i in 6..10) {
            // Next five items are pink
            columnItems.add(ColumnItem(rainbow200!![1], i))
        }
        return ColumnGroup(columnItems)
    }

    private fun makeCarouselGroup(): Group {
        val carouselDecoration = CarouselItemDecoration(gray, betweenPadding)
        val carouselAdapter = GroupAdapter<ViewHolder>()
        for (i in 0..9) {
            carouselAdapter.add(CarouselCardItem(rainbow200!![i]))
        }
        return CarouselGroup(carouselDecoration, carouselAdapter)
    }

    override fun onDestroy() {
        prefs!!.unregisterListener(onSharedPrefChangeListener)
        super.onDestroy()
    }

}