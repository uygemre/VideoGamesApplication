package com.base.component.ui.horizontalrecycler

import android.os.Parcelable
import com.base.component.constant.RecyclerviewAdapterConstant
import com.base.core.ui.recyclerview.DisplayItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HorizontalRecyclerDTO(
    var list: List<DisplayItem>,
    var isCircleLooping: Boolean = false
) : Parcelable, DisplayItem(RecyclerviewAdapterConstant.TYPES.TYPE_HORIZONTAL_RECYCLER)
