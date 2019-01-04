package ru.barabo.learn.groupie.groupietest.binding

import com.xwray.groupie.ExpandableGroup
import android.graphics.drawable.Animatable
import android.view.View
import ru.barabo.learn.groupie.groupietest.databinding.ItemHeaderBinding
import androidx.annotation.StringRes
import com.xwray.groupie.ExpandableItem
import ru.barabo.learn.groupie.groupietest.R
import ru.barabo.learn.groupie.groupietest.binding.items.HeaderItem


class ExpandableHeaderItem(@StringRes titleStringResId: Int, @StringRes subtitleResId: Int) :
    HeaderItem(titleStringResId, subtitleResId), ExpandableItem {

    private var expandableGroup: ExpandableGroup? = null

    override fun bind(viewBinding: ItemHeaderBinding, position: Int) {
        super.bind(viewBinding, position)

        // Initial icon state -- not animated.
        viewBinding.icon.visibility = View.VISIBLE
        viewBinding.icon.setImageResource(if (expandableGroup!!.isExpanded) R.drawable.collapse else R.drawable.expand)
        viewBinding.icon.setOnClickListener {
            expandableGroup!!.onToggleExpanded()
            bindIcon(viewBinding)
        }
    }

    private fun bindIcon(viewBinding: ItemHeaderBinding) {
        viewBinding.icon.visibility = View.VISIBLE
        viewBinding.icon.setImageResource(if (expandableGroup!!.isExpanded) R.drawable.collapse_animated else R.drawable.expand_animated)
        val drawable = viewBinding.icon.drawable as Animatable
        drawable.start()
    }

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        this.expandableGroup = onToggleListener
    }
}