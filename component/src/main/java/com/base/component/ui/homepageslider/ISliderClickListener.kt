package com.base.component.ui.homepageslider

import com.base.core.ui.recyclerview.DisplayItem

interface ISliderClickListener {
    fun onClickListener(position: Int?, item: DisplayItem?)
}