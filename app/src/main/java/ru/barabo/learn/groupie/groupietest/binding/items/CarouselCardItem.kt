package ru.barabo.learn.groupie.groupietest.binding.items

import androidx.annotation.ColorInt
import com.xwray.groupie.databinding.BindableItem
import ru.barabo.learn.groupie.groupietest.R
import ru.barabo.learn.groupie.groupietest.databinding.ItemSquareCardBinding


/**
 * A card item with a fixed width so it can be used with a horizontal layout manager.
 */
class CarouselCardItem(@param:ColorInt @field:ColorInt private val colorRes: Int) :
    BindableItem<ItemSquareCardBinding>() {

    override fun getLayout(): Int {
        return R.layout.item_square_card
    }

    override fun bind(viewBinding: ItemSquareCardBinding, position: Int) {
        viewBinding.root.setBackgroundColor(colorRes)
    }
}