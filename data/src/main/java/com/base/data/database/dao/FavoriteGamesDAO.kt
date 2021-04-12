package com.base.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.base.data.database.model.FavoriteGamesDTO
import io.reactivex.Single

// Created by Emre UYGUN on 4/10/21

@Dao
interface FavoriteGamesDAO : BaseDao<FavoriteGamesDTO> {

    @Query("DELETE FROM FavoriteGames WHERE gamesId = :gamesId")
    fun deleteById(gamesId: Int)

    @Query("SELECT * FROM FavoriteGames")
    fun getAllGamesCard(): Single<List<FavoriteGamesDTO>>

    @Query("DELETE FROM FavoriteGames")
    fun deleteAll()

}