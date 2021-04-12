package com.base.component.ui.gamesdetailparagraph

import android.os.Parcelable
import com.base.component.constant.RecyclerviewAdapterConstant
import com.base.core.ui.recyclerview.DisplayItem
import kotlinx.android.parcel.Parcelize

// Created by Emre UYGUN on 4/10/21

@Parcelize
data class GamesDetailParagraphDTO(
    var description: String?
) : Parcelable, DisplayItem(RecyclerviewAdapterConstant.TYPES.TYPE_VIDEO_GAMES_DETAIL_PARAGRAPH)