package ru.barabo.learn.groupie.groupietest.binding.items

import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.xwray.groupie.databinding.BindableItem
import ru.barabo.learn.groupie.groupietest.R
import ru.barabo.learn.groupie.groupietest.databinding.ItemHeaderBinding


open class HeaderItem @JvmOverloads constructor(
    @param:StringRes @field:StringRes private val titleStringResId: Int,
    @param:StringRes @field:StringRes private val subtitleResId: Int = 0,
    @param:DrawableRes @field:DrawableRes private val iconResId: Int = 0,
    private val onIconClickListener: View.OnClickListener? = null
) : BindableItem<ItemHeaderBinding>() {

    override fun getLayout(): Int {
        return R.layout.item_header
    }

    override fun bind(viewBinding: ItemHeaderBinding, position: Int) {
        viewBinding.title.setText(titleStringResId)
        if (subtitleResId > 0) {
            viewBinding.subtitle.setText(subtitleResId)
        }
        viewBinding.subtitle.visibility = if (subtitleResId > 0) View.VISIBLE else View.GONE

        if (iconResId > 0) {
            viewBinding.icon.setImageResource(iconResId)
            viewBinding.icon.setOnClickListener(onIconClickListener)
        }
        viewBinding.icon.visibility = if (iconResId > 0) View.VISIBLE else View.GONE
    }
}