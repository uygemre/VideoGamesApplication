package com.base.videogames.ui.pages.favorites

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.base.component.VideoGamesRecyclerviewAdapter
import com.base.component.ui.videogamescards.VideoGamesCardsDTO
import com.base.core.extensions.addTo
import com.base.core.extensions.setup
import com.base.core.networking.DataFetchResult
import com.base.videogames.R
import com.base.videogames.ui.base.fragment.BaseViewModelFragment
import com.base.videogames.ui.common.navigation.NavigationHelper
import com.base.videogames.ui.pages.favorites.viewmodel.FavoritesFragmentViewModel
import kotlinx.android.synthetic.main.fragment_favorites.*
import javax.inject.Inject

// Created by Emre UYGUN on 4/11/21

class FavoritesFragment : BaseViewModelFragment<FavoritesFragmentViewModel>() {

    override val viewModelClass = FavoritesFragmentViewModel::class.java
    override val layoutViewRes = R.layout.fragment_favorites

    @Inject
    lateinit var adapter: VideoGamesRecyclerviewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_favorites.setup(adapter = adapter.getAdapter())

        adapter.getAdapter().itemClickListener = { _item, _position ->
            val navigationHelper = NavigationHelper(requireActivity())
            when (_item) {
                is VideoGamesCardsDTO -> navigationHelper.navigate(_item)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        bindObserver()
    }

    @SuppressLint("CheckResult")
    private fun bindObserver() {
        viewModel.getAllFavoritesData()

        viewModel.allFavoritesPublishSubject.subscribe {
            adapter.getAdapter().updateAllItems(it.toMutableList().reversed())
        }

        viewModel.getAllFavoritesPublishSubject.observe(this, Observer {
        })
    }
}