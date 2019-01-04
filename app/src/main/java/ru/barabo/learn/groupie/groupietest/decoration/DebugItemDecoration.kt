package ru.barabo.learn.groupie.groupietest.decoration


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.core.view.ViewCompat.getTranslationY
import androidx.core.view.ViewCompat.getTranslationX
import androidx.recyclerview.widget.RecyclerView
import androidx.core.content.ContextCompat
import ru.barabo.learn.groupie.groupietest.Prefs
import ru.barabo.learn.groupie.groupietest.R


class DebugItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private var decoratedLeft: Int = 0
    private var decoratedTop: Int = 0
    private var decoratedRight: Int = 0
    private var decoratedBottom: Int = 0
    private var left: Int = 0
    private var top: Int = 0
    private var right: Int = 0
    private var bottom: Int = 0
    private val paint = Paint()
    private val prefs: Prefs
    private val leftColor: Int
    private val topColor: Int
    private val rightColor: Int
    private val bottomColor: Int

    init {
        prefs = Prefs[context]!!
        leftColor = ContextCompat.getColor(context, R.color.red_200)
        topColor = ContextCompat.getColor(context, R.color.pink_200)
        rightColor = ContextCompat.getColor(context, R.color.purple_200)
        bottomColor = ContextCompat.getColor(context, R.color.indigo_200)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (!(prefs.getShowBounds() || prefs.getShowOffsets())) return

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val lm = parent.layoutManager
            decoratedLeft = lm!!.getDecoratedLeft(child) + child.translationX.toInt()
            decoratedTop = lm.getDecoratedTop(child) + child.translationY.toInt()
            decoratedRight = lm.getDecoratedRight(child) + child.translationX.toInt()
            decoratedBottom = lm.getDecoratedBottom(child) + child.translationY.toInt()

            left = child.left + child.translationX.toInt()
            top = child.top + child.translationY.toInt()
            right = child.right + child.translationX.toInt()
            bottom = child.bottom + child.translationY.toInt()

            if (prefs.getShowBounds()) {
                paint.setColor(Color.RED)
                paint.setStyle(Paint.Style.STROKE)
                paint.setStrokeWidth(1F)
                c.drawRect(decoratedLeft.toFloat(), decoratedTop.toFloat(), decoratedRight.toFloat(), decoratedBottom.toFloat(), paint)
            }

            if (prefs.getShowOffsets()) {
                paint.setStyle(Paint.Style.FILL)

                paint.setColor(leftColor)
                c.drawRect(decoratedLeft.toFloat(), decoratedTop.toFloat(), left.toFloat(), decoratedBottom.toFloat(), paint)

                paint.setColor(topColor)
                c.drawRect(decoratedLeft.toFloat(), decoratedTop.toFloat(), decoratedRight.toFloat(), top.toFloat(), paint)

                paint.setColor(rightColor)
                c.drawRect(right.toFloat(), decoratedTop.toFloat(), decoratedRight.toFloat(), decoratedBottom.toFloat(), paint)

                paint.setColor(bottomColor)
                c.drawRect(decoratedLeft.toFloat(), bottom.toFloat(), decoratedRight.toFloat(), decoratedBottom.toFloat(), paint)
            }
        }
    }
}