package com.base.videogames.ui.pages.favorites.repository

import com.base.data.database.dao.FavoriteGamesDAO
import com.base.data.database.model.FavoriteGamesDTO
import io.reactivex.Single

// Created by Emre UYGUN on 4/10/21

class FavoritesFragmentLocaleData(
    private var favoritesDAO: FavoriteGamesDAO
) : FavoritesFragmentContract.Locale {

    override fun getAllFavorites(): Single<List<FavoriteGamesDTO>> {
        return favoritesDAO.getAllGamesCard()
    }
}