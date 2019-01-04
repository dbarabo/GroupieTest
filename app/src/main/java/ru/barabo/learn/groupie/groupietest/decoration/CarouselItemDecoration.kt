package ru.barabo.learn.groupie.groupietest.decoration

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView


class CarouselItemDecoration(@ColorInt backgroundColor: Int, private val padding: Int) : RecyclerView.ItemDecoration() {

    private val grayBackgroundPaint: Paint

    init {
        grayBackgroundPaint = Paint()
        grayBackgroundPaint.setColor(backgroundColor)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.right = padding
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount
        val lm = parent.layoutManager
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            var right = (lm!!.getDecoratedRight(child) + child.translationX).toInt()
            if (i == childCount - 1) {
                // Last item
                right = Math.max(right, parent.width)
            }

            // Right border
            c.drawRect(child.right + child.translationX,
                0.0f, right.toFloat(), parent.height.toFloat(), grayBackgroundPaint)
        }
    }
}