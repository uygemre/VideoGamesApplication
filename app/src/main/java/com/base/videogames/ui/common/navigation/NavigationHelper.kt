package com.base.videogames.ui.common.navigation

import android.app.Activity
import android.os.Bundle
import com.base.component.ui.homepageslider.SliderItem
import com.base.component.ui.videogamescards.VideoGamesCardsDTO
import com.base.core.extensions.openActivity
import com.base.core.ui.recyclerview.DisplayItem
import com.base.videogames.ui.common.enums.IntentBundleKeyEnum
import com.base.videogames.ui.pages.detail.DetailActivity

// Created by Emre UYGUN on 4/10/21

class NavigationHelper(var activity: Activity) {

    fun navigate(item: DisplayItem) {
        val bundle = Bundle()
        when (item) {
            is VideoGamesCardsDTO -> {
                bundle.putString(
                    IntentBundleKeyEnum.DETAIL_KEY.toString(),
                    IntentBundleKeyEnum.DETAIL_GAMES_CARD.toString()
                )
                bundle.putString("slug", item.slug)
            }
            is SliderItem -> {
                bundle.putString(
                    IntentBundleKeyEnum.DETAIL_KEY.toString(),
                    IntentBundleKeyEnum.DETAIL_SLIDER.toString()
                )
                bundle.putString("slug", item.slug)
            }
        }

        activity.openActivity(DetailActivity::class.java, bundle)
    }
}