package ru.barabo.learn.groupie.groupietest.binding

import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import ru.barabo.learn.groupie.groupietest.binding.items.INSET
import ru.barabo.learn.groupie.groupietest.binding.items.INSET_TYPE_KEY


class InsetItemDecoration(@ColorInt backgroundColor: Int, @Dimension padding: Int) :
    ru.barabo.learn.groupie.groupietest.decoration.InsetItemDecoration(
        backgroundColor,
        padding,
        INSET_TYPE_KEY,
        INSET
    )