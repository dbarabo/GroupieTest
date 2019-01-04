package ru.barabo.learn.groupie.groupietest.binding.items

import androidx.annotation.ColorInt


open class SmallCardItem : CardItem {

    constructor(@ColorInt colorRes: Int) : super(colorRes) {}

    constructor(@ColorInt colorRes: Int, text: CharSequence) : super(colorRes, text) {}

    override fun getSpanSize(spanCount: Int, position: Int): Int {
        return spanCount / 3
    }
}