package ru.barabo.learn.groupie.groupietest.binding

import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupDataObserver
import com.xwray.groupie.Item
import ru.barabo.learn.groupie.groupietest.binding.items.CarouselItem


/**
 * A group that contains a single carousel item and is empty when the carousel is empty
 */
class CarouselGroup(itemDecoration: RecyclerView.ItemDecoration, adapter: GroupAdapter<*>) : Group {

    private var isEmpty = true
    private val adapter: RecyclerView.Adapter<*>
    private var groupDataObserver: GroupDataObserver? = null

    private val carouselItem: CarouselItem = CarouselItem(itemDecoration, adapter)

    private val adapterDataObserver = object : RecyclerView.AdapterDataObserver() {

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            val empty = adapter.itemCount == 0
            if (empty && !isEmpty) {
                isEmpty = empty
                groupDataObserver!!.onItemRemoved(carouselItem, 0)
            }
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            val empty = adapter.itemCount == 0
            if (isEmpty && !empty) {
                isEmpty = empty
                groupDataObserver!!.onItemInserted(carouselItem, 0)
            }
        }
    }

    override fun getItemCount(): Int = if (isEmpty) 0 else 1

    init {
        this.adapter = adapter
        isEmpty = adapter.itemCount == 0
        adapter.registerAdapterDataObserver(adapterDataObserver)
    }

    override fun getItem(position: Int): Item<*> {
        return if (position == 0 && !isEmpty)
            carouselItem
        else
            throw IndexOutOfBoundsException()
    }

    override fun getPosition(item: Item<*>): Int {
        return if (item === carouselItem && !isEmpty) 0 else -1
    }

    override fun registerGroupDataObserver(groupDataObserver: GroupDataObserver) {
        this.groupDataObserver = groupDataObserver
    }

    override fun unregisterGroupDataObserver(groupDataObserver: GroupDataObserver) {
        this.groupDataObserver = null
    }
}