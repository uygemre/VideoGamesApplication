package com.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// Created by Emre UYGUN on 4/10/21

@Parcelize
data class GamesDetailResponse(
    var id: Int?,
    var slug: String?,
    var name: String?,
    var name_original: String?,
    var description: String?,
    var metacritic: Int?,
    var released: String?,
    var background_image: String?,
    var rating: Double?
) : Parcelable