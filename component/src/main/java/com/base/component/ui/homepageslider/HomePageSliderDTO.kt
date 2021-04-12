package com.base.component.ui.homepageslider

import android.os.Parcelable
import com.base.component.constant.RecyclerviewAdapterConstant.TYPES.TYPE_HOME_PAGE_SLIDER
import com.base.core.ui.recyclerview.DisplayItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HomePageSliderDTO(
    var itemList: ArrayList<SliderItem>
) : Parcelable, DisplayItem(TYPE_HOME_PAGE_SLIDER)