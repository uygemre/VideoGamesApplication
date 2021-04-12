package com.base.videogames.ioc.modules.database.videogames

import com.base.core.ioc.scopes.ActivityScope
import com.base.data.request.VideoGamesApiInterface
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

// Created by Emre UYGUN on 4/10/21

@Module
abstract class VideoGamesService {

    @Module
    companion object {

        @Provides
        @ActivityScope
        @JvmStatic
        fun provideGamesInterface(retrofit: Retrofit): VideoGamesApiInterface =
            retrofit.create(VideoGamesApiInterface::class.java)

    }
}