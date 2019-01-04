package ru.barabo.learn.groupie.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Checkable
import androidx.appcompat.widget.AppCompatImageView


class CheckableImageView : AppCompatImageView, Checkable {

    private var checked = false

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun isChecked(): Boolean {
        return checked
    }

    override fun setChecked(b: Boolean) {
        if (b != checked) {
            checked = b
            refreshDrawableState()
        }
    }

    override fun toggle() {
        isChecked = !checked
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            View.mergeDrawableStates(drawableState, CheckedStateSet)
        }
        return drawableState
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        invalidate()
    }

    companion object {

        private val CheckedStateSet = intArrayOf(android.R.attr.state_checked)
    }

}