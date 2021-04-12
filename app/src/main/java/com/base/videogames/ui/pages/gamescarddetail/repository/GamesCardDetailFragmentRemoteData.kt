package com.base.videogames.ui.pages.gamescarddetail.repository

import com.base.data.request.VideoGamesApiInterface
import com.base.data.response.GamesDetailResponse
import io.reactivex.Single

// Created by Emre UYGUN on 4/10/21

class GamesCardDetailFragmentRemoteData(
    private val apiInterface: VideoGamesApiInterface
) : GamesCardDetailFragmentContract.Remote {

    override fun getGamesDetail(game_pk: String?): Single<GamesDetailResponse> =
        apiInterface.getGamesDetail(game_pk)

}