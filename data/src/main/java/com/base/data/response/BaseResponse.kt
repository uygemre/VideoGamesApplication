package com.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class BaseResponse(
    var id: String?
) : Parcelable

