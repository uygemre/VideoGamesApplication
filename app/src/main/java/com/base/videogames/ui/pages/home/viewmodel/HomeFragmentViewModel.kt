package com.base.videogames.ui.pages.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.base.component.ui.homepageslider.HomePageSliderDTO
import com.base.component.ui.homepageslider.SliderItem
import com.base.component.ui.videogamescards.VideoGamesCardsDTO
import com.base.core.extensions.toLiveData
import com.base.core.networking.DataFetchResult
import com.base.core.ui.recyclerview.DisplayItem
import com.base.core.utils.TimeUtil
import com.base.data.response.GamesListResponse
import com.base.data.response.Results
import com.base.videogames.ui.base.viewmodel.BaseFragmentViewModel
import com.base.videogames.ui.pages.home.repository.HomeFragmentRepository
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

// Created by Emre UYGUN on 4/10/21

class HomeFragmentViewModel @Inject constructor(
    var repository: HomeFragmentRepository
) : BaseFragmentViewModel() {

    val homePageList = mutableListOf<DisplayItem>()
    val homePageLiveFilterList = MutableLiveData<List<Results?>>()
    val gamesListPublishSubject = PublishSubject.create<List<DisplayItem>>()

    private fun consumeGetGamesListSlider(response: GamesListResponse) {
        val list = arrayListOf<SliderItem>()
        response.results.take(3).map {
            list.add(
                SliderItem(
                    photoPath = it.background_image,
                    slug = it.slug
                )
            )
        }
        homePageList.add(HomePageSliderDTO(itemList = list))
        gamesListPublishSubject.onNext(homePageList)
    }

    private fun consumeGetGamesListCards(response: GamesListResponse) {
        response.results.drop(3).map {
            homePageList.add(
                VideoGamesCardsDTO(
                    id = it.id,
                    name = it.name,
                    background_image = it.background_image,
                    released = TimeUtil.dateDiff4(it.released ?: ""),
                    rating = it.rating,
                    slug = it.slug
                )
            )
        }

        gamesListPublishSubject.onNext(homePageList)
    }

    var gamesListDataResult: LiveData<DataFetchResult<GamesListResponse>> =
        Transformations.map(repository.gamesListDataResult.toLiveData(disposables)) {
            when (it) {
                is DataFetchResult.Progress -> {
                }
                is DataFetchResult.Failure -> {
                }
                is DataFetchResult.Success -> {
                    consumeGetGamesListSlider(it.data)
                    consumeGetGamesListCards(it.data)
                    homePageLiveFilterList.value = it.data.results
                }
            }
            it
        }

    fun resultSearch(results: List<Results?>): List<DisplayItem> {
        homePageList.clear()
        val list = arrayListOf<VideoGamesCardsDTO>()
        results.map {
            list.add(
                VideoGamesCardsDTO(
                    id = it?.id,
                    slug = it?.slug,
                    background_image = it?.background_image,
                    released = it?.released,
                    name = it?.name,
                    rating = it?.rating
                )
            )
        }

        homePageList.addAll(list)
        return homePageList
    }

    fun getGamesList() {
        repository.getGamesList()
    }
}