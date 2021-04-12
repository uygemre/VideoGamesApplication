package com.base.videogames.ui.pages.detail.repository

import com.base.core.networking.DataFetchResult
import com.base.core.networking.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

// Created by Emre UYGUN on 4/10/21

class DetailActivityRepository(
    private val remote: DetailActivityRemoteData,
    private val scheduler: Scheduler,
    private val compositeDisposable: CompositeDisposable
) : DetailActivityContract.Repository {

    override fun <T> handleError(
        dataFetchResult: PublishSubject<DataFetchResult<T>>,
        error: Throwable
    ) {
        TODO("Not yet implemented")
    }
}