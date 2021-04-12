package com.base.component.ui.homepageslider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.base.component.R
import com.base.core.ui.recyclerview.DisplayItem
import com.base.core.ui.recyclerview.ViewHolder
import com.base.core.ui.recyclerview.ViewHolderBinder
import com.base.core.ui.recyclerview.ViewHolderFactory
import javax.inject.Inject

class HomePageSliderViewHolder(var view: View) : ViewHolder<HomePageSliderDTO>(view) {

    private var viewPager: LoopingViewPager = view.findViewById(R.id.viewpager)

    override fun bind(item: HomePageSliderDTO) {
        val adapter =
            HomePageSliderViewPagerAdapter(view.context, item.itemList, false, clickListener)
        viewPager.adapter = adapter
    }

    private var clickListener = object :
        ISliderClickListener {
        override fun onClickListener(position: Int?, item: DisplayItem?) {
            item?.let { _item ->
                position?.let { _position ->
                    itemClickListener?.invoke(
                        _item,
                        _position
                    )
                }
            }
        }
    }

    class HolderFactory @Inject constructor() : ViewHolderFactory {
        override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
            HomePageSliderViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_home_page_slider,
                    parent,
                    false
                )
            )
    }

    class BinderFactory @Inject constructor() : ViewHolderBinder {
        override fun bind(holder: RecyclerView.ViewHolder, item: DisplayItem) {
            (holder as HomePageSliderViewHolder).bind(item as HomePageSliderDTO)
        }
    }
}
