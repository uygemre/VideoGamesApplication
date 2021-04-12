package com.base.videogames.ui.pages.gamescarddetail.repository

import com.base.data.database.dao.FavoriteGamesDAO
import com.base.data.database.model.FavoriteGamesDTO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// Created by Emre UYGUN on 4/10/21

class GamesCardDetailFragmentLocaleData(
    private var favoritesGamesDao: FavoriteGamesDAO
) : GamesCardDetailFragmentContract.Locale {

    override fun insertFav(favData: FavoriteGamesDTO) {
        GlobalScope.launch {
            favoritesGamesDao.insert(favData)
        }
    }

    override fun deleteFavoritesById(favoriteGamesId: Int) {
        GlobalScope.launch {
            favoritesGamesDao.deleteById(favoriteGamesId)
        }
    }
}