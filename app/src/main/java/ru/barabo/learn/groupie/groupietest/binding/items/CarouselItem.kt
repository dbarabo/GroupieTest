package ru.barabo.learn.groupie.groupietest.binding.items

import android.view.View
import ru.barabo.learn.groupie.groupietest.databinding.ItemCarouselBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.databinding.ViewHolder
import com.xwray.groupie.databinding.BindableItem
import ru.barabo.learn.groupie.groupietest.R


/**
 * A horizontally scrolling RecyclerView, for use in a vertically scrolling RecyclerView.
 */
class CarouselItem(private val carouselDecoration: RecyclerView.ItemDecoration, private val adapter: GroupAdapter<*>) :
    BindableItem<ItemCarouselBinding>(), OnItemClickListener {

    init {
        adapter.setOnItemClickListener(this)
    }

    override fun createViewHolder(itemView: View): ViewHolder<ItemCarouselBinding> {

        val viewHolder = super.createViewHolder(itemView)
        val recyclerView = viewHolder.binding.recyclerView
        recyclerView.addItemDecoration(carouselDecoration)
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
        return viewHolder
    }

    override fun bind(viewBinding: ItemCarouselBinding, position: Int) {
        viewBinding.recyclerView.adapter = adapter
    }

    override fun getLayout(): Int {
        return R.layout.item_carousel
    }

    override fun onItemClick(item: Item<*>, view: View) {
        adapter.remove(item)
    }
}