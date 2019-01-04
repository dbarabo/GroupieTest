package ru.barabo.learn.groupie.groupietest.binding.items

import android.annotation.SuppressLint
import androidx.annotation.ColorRes


@SuppressLint("ResourceAsColor")
class UpdatableItem(@ColorRes colorRes: Int, private val index: Int) : SmallCardItem(colorRes, index.toString()) {

    override fun getId(): Long {
        return index.toLong()
    }
}