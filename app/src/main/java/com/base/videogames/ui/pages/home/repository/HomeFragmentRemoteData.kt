package com.base.videogames.ui.pages.home.repository

import com.base.data.request.VideoGamesApiInterface
import com.base.data.response.GamesListResponse
import io.reactivex.Single

// Created by Emre UYGUN on 4/10/21

class HomeFragmentRemoteData(
    private val apiInterface: VideoGamesApiInterface
) : HomeFragmentContract.Remote {

    override fun getGamesList(): Single<GamesListResponse> = apiInterface.getGamesList()

}