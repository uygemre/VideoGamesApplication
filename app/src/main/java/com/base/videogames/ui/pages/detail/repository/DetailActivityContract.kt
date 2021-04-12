package com.base.videogames.ui.pages.detail.repository

import com.base.core.networking.DataFetchResult
import io.reactivex.subjects.PublishSubject

// Created by Emre UYGUN on 4/10/21

interface DetailActivityContract {

    interface Repository {
        fun <T> handleError(dataFetchResult: PublishSubject<DataFetchResult<T>>, error: Throwable)
    }

    interface Remote {

    }
}