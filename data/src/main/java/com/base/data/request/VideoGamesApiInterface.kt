package com.base.data.request

import com.base.core.constants.AppConstants
import com.base.data.response.GamesDetailResponse
import com.base.data.response.GamesListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

// Created by Emre UYGUN on 4/10/21

interface VideoGamesApiInterface {

    @GET("https://rawg-video-games-database.p.rapidapi.com/games")
    fun getGamesList(
        @Header("x-rapidapi-key") api_key: String = AppConstants.API_KEY,
        @Header("x-rapidapi-host") api_host: String = AppConstants.API_HOST
    ): Single<GamesListResponse>

    @GET("https://rawg-video-games-database.p.rapidapi.com/games/{game_pk}")
    fun getGamesDetail(
        @Path("game_pk") game_pk: String?,
        @Header("x-rapidapi-key") api_key: String = AppConstants.API_KEY,
        @Header("x-rapidapi-host") api_host: String = AppConstants.API_HOST
    ): Single<GamesDetailResponse>

}