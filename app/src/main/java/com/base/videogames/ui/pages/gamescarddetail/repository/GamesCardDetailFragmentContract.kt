package com.base.videogames.ui.pages.gamescarddetail.repository

import com.base.core.networking.DataFetchResult
import com.base.data.database.model.FavoriteGamesDTO
import com.base.data.response.GamesDetailResponse
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

// Created by Emre UYGUN on 4/10/21

interface GamesCardDetailFragmentContract {

    interface Repository {
        fun <T> handleError(dataFetchResult: PublishSubject<DataFetchResult<T>>, error: Throwable)
        val gamesDetailDataResult: PublishSubject<DataFetchResult<GamesDetailResponse>>
        fun getGamesDetail(game_pk: String?)
        fun insertFav(favoriteGamesDTO: FavoriteGamesDTO)
        fun deleteFavoritesById(favoriteGamesId: Int)
    }

    interface Remote {
        fun getGamesDetail(game_pk: String?): Single<GamesDetailResponse>
    }

    interface Locale {
        fun insertFav(favData: FavoriteGamesDTO)
        fun deleteFavoritesById(favoriteGamesId: Int)
    }
}