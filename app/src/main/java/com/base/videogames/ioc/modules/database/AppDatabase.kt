package com.base.videogames.ioc.modules.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.base.data.database.dao.FavoriteGamesDAO
import com.base.data.database.model.FavoriteGamesDTO

@Database(entities = [FavoriteGamesDTO::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
	abstract fun favoriteGamesDao(): FavoriteGamesDAO
}
