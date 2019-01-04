package ru.barabo.learn.groupie.groupietest.binding.items

import android.graphics.drawable.Animatable
import android.view.View
import androidx.annotation.ColorInt
import com.xwray.groupie.databinding.BindableItem
import ru.barabo.learn.groupie.groupietest.R
import ru.barabo.learn.groupie.groupietest.databinding.ItemHeartCardBinding


class HeartCardItem(
    @param:ColorInt @field:ColorInt private val colorRes: Int, id: Long,
    private val onFavoriteListener: OnFavoriteListener
) : BindableItem<ItemHeartCardBinding>(id) {
    private var checked = false
    private var inProgress = false

    init {
        extras[INSET_TYPE_KEY] = INSET
    }

    override fun getLayout(): Int {
        return R.layout.item_heart_card
    }

    override fun bind(binding: ItemHeartCardBinding, position: Int) {
        //binding.getRoot().setBackgroundColor(colorRes);
        bindHeart(binding)
        binding.text.text = (id + 1).toString()

        binding.favorite.setOnClickListener {
            inProgress = true
            animateProgress(binding)

            onFavoriteListener.onFavorite(this@HeartCardItem, !checked)
        }
    }

    private fun bindHeart(binding: ItemHeartCardBinding) {
        if (inProgress) {
            animateProgress(binding)
        } else {
            binding.favorite.setImageResource(R.drawable.favorite_state_list)
        }
        binding.favorite.setChecked(checked)
    }

    private fun animateProgress(binding: ItemHeartCardBinding) {
        binding.favorite.setImageResource(R.drawable.avd_favorite_progress)
        (binding.favorite.getDrawable() as Animatable).start()
    }

    fun setFavorite(favorite: Boolean) {
        inProgress = false
        checked = favorite
    }

    override fun bind(binding: ItemHeartCardBinding, position: Int, payloads: List<Any>?) {
        if (payloads!!.contains(FAVORITE)) {
            bindHeart(binding)
        } else {
            bind(binding, position)
        }
    }

    override fun isClickable(): Boolean {
        return false
    }

    interface OnFavoriteListener {
        fun onFavorite(item: HeartCardItem, favorite: Boolean)
    }

    companion object {

        val FAVORITE = "FAVORITE"
    }
}