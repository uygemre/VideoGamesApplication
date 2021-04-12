package com.base.videogames.ui.pages.gamescarddetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.base.component.ui.gamesdetail.GamesDetailDTO
import com.base.component.ui.gamesdetailparagraph.GamesDetailParagraphDTO
import com.base.core.extensions.toLiveData
import com.base.core.networking.DataFetchResult
import com.base.core.ui.recyclerview.DisplayItem
import com.base.core.utils.TimeUtil
import com.base.data.database.model.FavoriteGamesDTO
import com.base.data.response.GamesDetailResponse
import com.base.videogames.ui.base.viewmodel.BaseFragmentViewModel
import com.base.videogames.ui.pages.gamescarddetail.repository.GamesCardDetailFragmentRepository
import io.reactivex.subjects.PublishSubject
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

// Created by Emre UYGUN on 4/10/21

class GamesCardDetailFragmentViewModel @Inject constructor(
    private val repository: GamesCardDetailFragmentRepository
) : BaseFragmentViewModel() {

    private var detailList = mutableListOf<DisplayItem>()
    var gamesDetailPublishSubject = PublishSubject.create<List<DisplayItem>>()

    val gamesDetailDataResult: LiveData<DataFetchResult<GamesDetailResponse>> =
        Transformations.map(repository.gamesDetailDataResult.toLiveData(disposables)) { _data ->
            when (_data) {
                is DataFetchResult.Success -> {
                    consumeGetGamesDetail(_data.data)
                }
                is DataFetchResult.Progress -> {
                }
                is DataFetchResult.Failure -> {
                }
            }
            _data
        }

    private fun consumeGetGamesDetail(response: GamesDetailResponse) {
        detailList.add(
            GamesDetailDTO(
                gamesId = response.id,
                name = response.name,
                background_image = response.background_image,
                released = TimeUtil.dateDiff4(response.released ?: ""),
                metacritic = response.metacritic,
                slug = response.slug,
                rating = response.rating
            )
        )

        gamesDetailPublishSubject.onNext(detailList)
    }

    fun parseHtmlData(_stringJson: String?) {
        _stringJson?.let { _stringJson ->

            val json = JSONObject(_stringJson)
            if (!json.isNull("Result")) {
                val result = json.getJSONArray("Result")

                result.let { _result ->
                    for (i in 0 until _result.length()) {
                        val obj = _result.getJSONObject(i)
                        if (!obj.isNull("type")) {

                            val type = obj.getString("type")

                            var value = ""

                            if (!obj.isNull("value")) {
                                value = obj.getString("value")
                            }

                            Timber.d(type)
                            Timber.d(value)
                            Timber.d("----------------")

                            when (type) {
                                "paragraph" -> {
                                    detailList.add(
                                        GamesDetailParagraphDTO(
                                            description = value
                                        )
                                    )
                                }
                                else -> {

                                }
                            }
                        }
                    }
                }
            }
        }

        gamesDetailPublishSubject.onNext(detailList)
    }

    fun getGamesDetail(game_pk: String?) {
        repository.getGamesDetail(game_pk)
    }

    fun insertFav(dataFav: FavoriteGamesDTO) {
        repository.insertFav(dataFav)
    }

    fun deleteFavoritesById(favoriteGamesId: Int) {
        repository.deleteFavoritesById(favoriteGamesId)
    }
}


