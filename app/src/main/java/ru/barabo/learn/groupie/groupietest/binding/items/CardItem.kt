package ru.barabo.learn.groupie.groupietest.binding.items


import androidx.annotation.ColorInt
import com.xwray.groupie.databinding.BindableItem
import ru.barabo.learn.groupie.groupietest.R
import ru.barabo.learn.groupie.groupietest.databinding.ItemCardBinding


val INSET_TYPE_KEY = "inset_type"
val FULL_BLEED = "full_bleed"
val INSET = "inset"


open class CardItem @JvmOverloads
constructor(@param:ColorInt @field:ColorInt val colorRes: Int,
            var text: CharSequence? = "") :
    BindableItem<ItemCardBinding>() {

    init {
        extras[INSET_TYPE_KEY] = INSET
    }

    override fun getLayout(): Int {
        return R.layout.item_card
    }

    override fun bind(viewBinding: ItemCardBinding, position: Int) {
        viewBinding.getRoot().setBackgroundColor(colorRes);
        viewBinding.text.setText(text)
    }
}