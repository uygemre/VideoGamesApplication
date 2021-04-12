package com.base.videogames.ui.pages.home.repository

import com.base.core.networking.DataFetchResult
import com.base.data.response.GamesListResponse
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

// Created by Emre UYGUN on 4/10/21

interface HomeFragmentContract {

    interface Repository {
        fun <T> handleError(dataFetchResult: PublishSubject<DataFetchResult<T>>, error: Throwable)
        val gamesListDataResult: PublishSubject<DataFetchResult<GamesListResponse>>
        fun getGamesList()
    }
    interface Remote{
        fun getGamesList(): Single<GamesListResponse>
    }
}