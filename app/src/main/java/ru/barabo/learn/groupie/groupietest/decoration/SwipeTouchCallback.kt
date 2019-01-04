package ru.barabo.learn.groupie.groupietest.decoration

import android.graphics.Canvas
import android.graphics.Paint

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.annotation.ColorInt
import com.xwray.groupie.TouchCallback


abstract class SwipeTouchCallback(@param:ColorInt @field:ColorInt private val backgroundColor: Int) : TouchCallback() {

    private val paint: Paint

    init {
        paint = Paint()
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (ItemTouchHelper.ACTION_STATE_SWIPE == actionState) {
            val child = viewHolder.itemView

            //val lm = recyclerView.layoutManager

            // Fade out the item
            child.alpha = 1 - Math.abs(dX) / child.width.toFloat()
            //
            //            // Draw gray "behind" the item, if it's being moved horizontally (swiped)
            //            paint.setColor(backgroundColor);
            //            if (dX > 0) {
            //                c.drawRect(
            //                        lm.getDecoratedLeft(child),
            //                        lm.getDecoratedTop(child),
            //                        lm.getDecoratedLeft(child) + dX,
            //                        lm.getDecoratedBottom(child), paint);
            //            } else if (dX < 0) {
            //                c.drawRect(
            //                        lm.getDecoratedRight(child) + dX,
            //                        lm.getDecoratedTop(child),
            //                        lm.getDecoratedRight(child),
            //                        lm.getDecoratedBottom(child), paint);
            //            }
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}