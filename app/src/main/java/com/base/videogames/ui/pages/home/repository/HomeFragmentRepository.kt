package com.base.videogames.ui.pages.home.repository

import com.base.core.extensions.*
import com.base.core.ioc.scopes.FragmentScope
import com.base.core.networking.DataFetchResult
import com.base.core.networking.Scheduler
import com.base.data.response.GamesListResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

// Created by Emre UYGUN on 4/10/21

@FragmentScope
class HomeFragmentRepository(
    private val remote: HomeFragmentRemoteData,
    private val scheduler: Scheduler,
    private val compositeDisposable: CompositeDisposable
) : HomeFragmentContract.Repository {

    override fun <T> handleError(result: PublishSubject<DataFetchResult<T>>, error: Throwable) {
        result.failed(error)
        Timber.e(error.localizedMessage)
    }

    override val gamesListDataResult = PublishSubject.create<DataFetchResult<GamesListResponse>>()

    override fun getGamesList() {
        gamesListDataResult.loading(true)
        remote.getGamesList()
            .performOnBackOutOnMain(scheduler)
            .subscribe(
                {
                    gamesListDataResult.success(it)
                },
                { error ->
                    handleError(gamesListDataResult, error)
                }
            ).addTo(compositeDisposable)
    }
}