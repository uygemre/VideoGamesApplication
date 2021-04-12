package com.base.videogames.ui.pages.favorites.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.base.component.ui.videogamescards.VideoGamesCardsDTO
import com.base.core.extensions.toLiveData
import com.base.core.networking.DataFetchResult
import com.base.core.ui.recyclerview.DisplayItem
import com.base.data.database.model.FavoriteGamesDTO
import com.base.videogames.ui.base.viewmodel.BaseFragmentViewModel
import com.base.videogames.ui.pages.favorites.repository.FavoritesFragmentRepository
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

// Created by Emre UYGUN on 4/10/21

class FavoritesFragmentViewModel @Inject constructor(
    private val repository: FavoritesFragmentRepository
) : BaseFragmentViewModel() {

    val allFavoritesPublishSubject = PublishSubject.create<MutableList<DisplayItem>>()

    val getAllFavoritesPublishSubject: LiveData<DataFetchResult<List<FavoriteGamesDTO>>> =
        Transformations.map(repository.getAllFavoritesPublishSubject.toLiveData(disposables)) { _data ->
            when (_data) {
                is DataFetchResult.Progress -> {
                }
                is DataFetchResult.Failure -> {
                }
                is DataFetchResult.Success -> {
                    getMyListData(_data.data)
                }
            }
            _data
        }

    private fun getMyListData(data: List<FavoriteGamesDTO>){
        val myList = mutableListOf<DisplayItem>()
        data.map { _localData ->
            myList.add(
                VideoGamesCardsDTO(
                    background_image = _localData.background_image,
                    name = _localData.name,
                    rating = _localData.rating,
                    released = _localData.released,
                    id = _localData.id,
                    slug = _localData.slug
                )
            )
        }

        allFavoritesPublishSubject.onNext(myList)
    }

    fun getAllFavoritesData() {
        repository.getAllFavorites()
    }
}