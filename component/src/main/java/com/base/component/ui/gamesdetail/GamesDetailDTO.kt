package com.base.component.ui.gamesdetail

import android.os.Parcelable
import com.base.component.constant.RecyclerviewAdapterConstant
import com.base.core.ui.recyclerview.DisplayItem
import io.reactivex.Single
import kotlinx.android.parcel.Parcelize

// Created by Emre UYGUN on 4/10/21

@Parcelize
data class GamesDetailDTO(
    var gamesId: Int?,
    var name: String?,
    var metacritic: Int?,
    var slug: String?,
    var rating: Double?,
    var released: String?,
    var background_image: String?
) : Parcelable,  DisplayItem(RecyclerviewAdapterConstant.TYPES.TYPE_VIDEO_GAMES_DETAIL)