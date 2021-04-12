package com.base.videogames.ui.pages.favorites.repository

import com.base.core.networking.DataFetchResult
import com.base.data.database.model.FavoriteGamesDTO
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

// Created by Emre UYGUN on 4/10/21

interface FavoritesFragmentContract {

    interface Repository {
        val getAllFavoritesPublishSubject: PublishSubject<DataFetchResult<List<FavoriteGamesDTO>>>
        fun getAllFavorites()
        fun <T> handleError(dataFetchResult: PublishSubject<DataFetchResult<T>>, error: Throwable)
    }

    interface Remote {
    }

    interface Locale {
        fun getAllFavorites(): Single<List<FavoriteGamesDTO>>
    }
}