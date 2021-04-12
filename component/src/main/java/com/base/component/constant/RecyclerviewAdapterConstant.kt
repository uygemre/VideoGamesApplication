package com.base.component.constant

import com.base.component.ui.gamesdetail.GamesDetailViewHolder
import com.base.component.ui.gamesdetailparagraph.GamesDetailParagraphViewHolder
import com.base.component.ui.homepageslider.HomePageSliderViewHolder
import com.base.component.ui.horizontalrecycler.HorizontalRecyclerViewHolder
import com.base.component.ui.videogamescards.VideoGamesCardsViewHolder

// Created by Emre UYGUN on 4/10/21

class RecyclerviewAdapterConstant {

    object TYPES {
        const val TYPE_NONE = 0
        const val TYPE_HORIZONTAL_RECYCLER = 1
        const val TYPE_HOME_PAGE_SLIDER = 2
        const val TYPE_SLIDER_ITEM = 3
        const val TYPE_VIDEO_GAMES_CARDS = 4
        const val TYPE_VIDEO_GAMES_DETAIL = 5
        const val TYPE_VIDEO_GAMES_DETAIL_PARAGRAPH = 6
    }

    var binderFactoryMap = mutableMapOf(
        TYPES.TYPE_HORIZONTAL_RECYCLER to HorizontalRecyclerViewHolder.BinderFactory(),
        TYPES.TYPE_HOME_PAGE_SLIDER to HomePageSliderViewHolder.BinderFactory(),
        TYPES.TYPE_VIDEO_GAMES_CARDS to VideoGamesCardsViewHolder.BinderFactory(),
        TYPES.TYPE_VIDEO_GAMES_DETAIL to GamesDetailViewHolder.BinderFactory(),
        TYPES.TYPE_VIDEO_GAMES_DETAIL_PARAGRAPH to GamesDetailParagraphViewHolder.BinderFactory()
    )

    var holderFactoryMap = mutableMapOf(
        TYPES.TYPE_HORIZONTAL_RECYCLER to HorizontalRecyclerViewHolder.HolderFactory(),
        TYPES.TYPE_HOME_PAGE_SLIDER to HomePageSliderViewHolder.HolderFactory(),
        TYPES.TYPE_VIDEO_GAMES_CARDS to VideoGamesCardsViewHolder.HolderFactory(),
        TYPES.TYPE_VIDEO_GAMES_DETAIL to GamesDetailViewHolder.HolderFactory(),
        TYPES.TYPE_VIDEO_GAMES_DETAIL_PARAGRAPH to GamesDetailParagraphViewHolder.HolderFactory()
    )
}
