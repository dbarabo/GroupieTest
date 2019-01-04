package ru.barabo.learn.groupie.groupietest.binding.items

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.annotation.ColorInt


class SwipeToDeleteItem(@ColorInt colorRes: Int) : CardItem(colorRes) {

    override fun getSwipeDirs(): Int {
        return ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    }
}