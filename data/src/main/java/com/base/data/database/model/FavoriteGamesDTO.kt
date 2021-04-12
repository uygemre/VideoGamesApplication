package com.base.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Created by Emre UYGUN on 4/10/21

@Entity(tableName = "FavoriteGames")
data class FavoriteGamesDTO(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var gamesId: Int?,
    var slug: String?,
    var background_image: String?,
    val name: String?,
    val released: String?,
    val rating: Double?
)