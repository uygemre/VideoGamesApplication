package com.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// Created by Emre UYGUN on 4/10/21

@Parcelize
data class GamesListResponse(
    val count: Int?,
    val next: String?,
    val results: List<Results>,
    val seo_title: String?,
    val seo_description: String?,
    val seo_keywords: String?,
    val seo_h1: String?,
    val description: String?
) : Parcelable

@Parcelize
data class Results(
    val id: Int?,
    val slug: String?,
    val name: String?,
    val released: String?,
    var background_image: String?,
    val rating: Double?,
    val rating_top: Int?
) : Parcelable