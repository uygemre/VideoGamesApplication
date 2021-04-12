package com.base.videogames.ui.pages.gamescarddetail.repository

import com.base.core.extensions.*
import com.base.core.networking.DataFetchResult
import com.base.core.networking.Scheduler
import com.base.data.database.model.FavoriteGamesDTO
import com.base.data.response.GamesDetailResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

// Created by Emre UYGUN on 4/10/21

class GamesCardDetailFragmentRepository(
    private val remote: GamesCardDetailFragmentRemoteData,
    private val locale: GamesCardDetailFragmentLocaleData,
    private val scheduler: Scheduler,
    private val compositeDisposable: CompositeDisposable
) : GamesCardDetailFragmentContract.Repository {

    override fun <T> handleError(result: PublishSubject<DataFetchResult<T>>, error: Throwable) {
        result.failed(error)
        Timber.e(error.localizedMessage)
    }

    override val gamesDetailDataResult =
        PublishSubject.create<DataFetchResult<GamesDetailResponse>>()

    override fun getGamesDetail(game_pk: String?) {
        gamesDetailDataResult.loading(true)
        remote.getGamesDetail(game_pk)
            .performOnBackOutOnMain(scheduler)
            .subscribe(
                {
                    gamesDetailDataResult.success(it)
                },
                { error ->
                    handleError(gamesDetailDataResult, error)
                }
            ).addTo(compositeDisposable)
    }

    override fun insertFav(favoriteGamesDTO: FavoriteGamesDTO) {
        return locale.insertFav(favoriteGamesDTO)
    }

    override fun deleteFavoritesById(favoriteGamesId: Int) {
        return locale.deleteFavoritesById(favoriteGamesId)
    }
}