package ru.barabo.learn.groupie.groupietest.decoration

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.ViewHolder


/**
 * An ItemDecoration which applies an even visual padding on the left and right edges of a grid and
 * between each item, while also applying an even amount of inset to each item.  This ensures that
 * all items remain the same size.
 *
 * It assumes all items in a row have the same span size, and it assumes it's the only item
 * decorator.
 */
open class InsetItemDecoration(
    @ColorInt backgroundColor: Int, @param:Dimension private val padding: Int, private val key: String,
    private val value: String
) : RecyclerView.ItemDecoration() {

    private val paint: Paint

    init {
        paint = Paint()
        paint.setColor(backgroundColor)
    }

    private fun isInset(view: View, parent: RecyclerView): Boolean {
        val viewHolder = parent.getChildViewHolder(view) as ViewHolder
        return if (viewHolder.getExtras().containsKey(key)) {
            viewHolder.getExtras().get(key)?.equals(value) ?: false
        } else {
            false
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (!isInset(view, parent)) return

        val layoutParams = view.getLayoutParams() as GridLayoutManager.LayoutParams
        val gridLayoutManager = parent.layoutManager as GridLayoutManager?
        val spanSize = layoutParams.spanSize.toFloat()
        val totalSpanSize = gridLayoutManager!!.spanCount.toFloat()

        val n = totalSpanSize / spanSize // num columns
        val c = layoutParams.spanIndex / spanSize // column index

        val leftPadding = padding * ((n - c) / n)
        val rightPadding = padding * ((c + 1) / n)

        outRect.left = leftPadding.toInt()
        outRect.right = rightPadding.toInt()
        outRect.bottom = padding
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val lm = parent.layoutManager

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            if (!isInset(child, parent)) continue

            //
            if (child.translationX != 0.0f || child.translationY != 0.0f) {
                c.drawRect(
                    lm!!.getDecoratedLeft(child).toFloat(),
                    lm.getDecoratedTop(child).toFloat(),
                    lm.getDecoratedRight(child).toFloat(),
                    lm.getDecoratedBottom(child).toFloat(),
                    paint
                )
                continue
            }

            val isLast = i == childCount - 1
            val top = child.top + child.translationY
            val bottom = child.bottom + child.translationY

            // Left border
            c.drawRect(
                lm!!.getDecoratedLeft(child).toFloat() + child.translationX.toFloat(),
                top,
                child.left.toFloat() + child.translationX.toFloat(),
                bottom,
                paint
            )

            var right = lm.getDecoratedRight(child).toFloat() + child.translationX.toFloat()
            if (isLast) {
                right = Math.max(right, parent.width.toFloat())
            }

            // Right border
            c.drawRect(
                child.right.toFloat() + child.translationX.toFloat(),
                top,
                right,
                bottom,
                paint
            )

            // Bottom border
            c.drawRect(
                lm.getDecoratedLeft(child).toFloat() + child.translationY.toFloat(),
                bottom,
                right,
                lm.getDecoratedBottom(child).toFloat() + child.translationY.toFloat(),
                paint
            )
        }
    }
}