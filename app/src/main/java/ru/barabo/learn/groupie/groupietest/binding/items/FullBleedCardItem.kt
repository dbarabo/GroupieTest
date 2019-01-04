package ru.barabo.learn.groupie.groupietest.binding.items

import android.annotation.SuppressLint
import androidx.annotation.ColorRes


@SuppressLint("ResourceAsColor")
class FullBleedCardItem(@ColorRes colorRes: Int) : CardItem(colorRes) {

    init {
        extras.remove(INSET_TYPE_KEY)
    }
}