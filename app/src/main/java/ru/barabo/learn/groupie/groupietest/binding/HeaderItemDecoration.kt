package ru.barabo.learn.groupie.groupietest.binding

import androidx.annotation.ColorInt
import ru.barabo.learn.groupie.groupietest.R


class HeaderItemDecoration(@ColorInt background: Int, sidePaddingPixels: Int) :
    ru.barabo.learn.groupie.groupietest.decoration.HeaderItemDecoration(background, sidePaddingPixels,  R.layout.item_header)