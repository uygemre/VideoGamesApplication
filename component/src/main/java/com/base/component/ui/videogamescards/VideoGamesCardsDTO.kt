package com.base.component.ui.videogamescards

import android.os.Parcelable
import com.base.component.constant.RecyclerviewAdapterConstant
import com.base.core.ui.recyclerview.DisplayItem
import kotlinx.android.parcel.Parcelize

// Created by Emre UYGUN on 4/10/21

@Parcelize
data class VideoGamesCardsDTO(
    var id: Int?,
    var slug: String?,
    var background_image: String?,
    val name: String?,
    val released: String?,
    val rating: Double?
) : Parcelable, DisplayItem(RecyclerviewAdapterConstant.TYPES.TYPE_VIDEO_GAMES_CARDS)
