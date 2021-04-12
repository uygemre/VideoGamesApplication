package com.base.videogames.ui.pages.main.repository

import com.base.core.extensions.failed
import com.base.core.networking.DataFetchResult
import com.base.core.networking.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

// Created by Emre UYGUN on 4/10/21

class MainActivityRepository(
    private val remote: MainActivityRemoteData,
    private val scheduler: Scheduler,
    private val compositeDisposable: CompositeDisposable
) : MainActivityContract.Repository {

    override fun <T> handleError(
        dataFetchResult: PublishSubject<DataFetchResult<T>>,
        error: Throwable
    ) {
        dataFetchResult.failed(error)
        Timber.e(error.localizedMessage)
    }
}
