package com.base.component.ui.homepageslider

import android.os.Parcelable
import com.base.component.constant.RecyclerviewAdapterConstant
import com.base.core.ui.recyclerview.DisplayItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SliderItem(
    var newsType: String? = "games",
    var placeName: String? = "",
    var photoPath: String? = "",
    var desc: String? = "",
    var slug: String? =  ""
) : Parcelable, DisplayItem(RecyclerviewAdapterConstant.TYPES.TYPE_SLIDER_ITEM)

