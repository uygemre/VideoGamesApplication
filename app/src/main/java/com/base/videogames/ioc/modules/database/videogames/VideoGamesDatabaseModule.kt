package com.base.videogames.ioc.modules.database.videogames

import android.content.Context
import androidx.room.Room
import com.base.data.database.dao.FavoriteGamesDAO
import com.base.videogames.application.Application
import com.base.videogames.ioc.modules.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

// Created by Emre UYGUN on 4/10/21

@Module
internal class VideoGamesDatabaseModule {

    @Singleton
    @Provides
    fun provideDb(app: Context): AppDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            AppDatabase::class.java, "videoGames.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideFavoriteGamesDao(db: AppDatabase): FavoriteGamesDAO {
        return db.favoriteGamesDao()
    }

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }
}