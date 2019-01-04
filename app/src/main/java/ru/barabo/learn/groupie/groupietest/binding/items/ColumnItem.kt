package ru.barabo.learn.groupie.groupietest.binding.items

import androidx.annotation.ColorInt


class ColumnItem(@ColorInt colorRes: Int, index: Int) : CardItem(colorRes, index.toString()) {

    init {
        extras[INSET_TYPE_KEY] = INSET
    }

    override fun getSpanSize(spanCount: Int, position: Int): Int {
        return spanCount / 2
    }
}