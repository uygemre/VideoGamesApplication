package com.base.videogames.ui.pages.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.base.component.VideoGamesRecyclerviewAdapter
import com.base.component.ui.homepageslider.SliderItem
import com.base.component.ui.videogamescards.VideoGamesCardsDTO
import com.base.core.extensions.afterTextChanged
import com.base.core.extensions.gone
import com.base.core.extensions.setup
import com.base.core.extensions.visibile
import com.base.core.networking.DataFetchResult
import com.base.videogames.R
import com.base.videogames.ui.base.fragment.BaseViewModelFragment
import com.base.videogames.ui.common.navigation.NavigationHelper
import com.base.videogames.ui.pages.home.viewmodel.HomeFragmentViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

// Created by Emre UYGUN on 4/10/21

class HomeFragment : BaseViewModelFragment<HomeFragmentViewModel>() {

    override val viewModelClass = HomeFragmentViewModel::class.java
    override val layoutViewRes = R.layout.fragment_home

    @Inject
    lateinit var adapter: VideoGamesRecyclerviewAdapter

    @SuppressLint("DefaultLocale")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        home_page_recyclerview.setup(adapter = adapter.getAdapter())
        bindObserver()

        val navigationHelper = NavigationHelper(requireActivity())
        adapter.getAdapter().itemClickListener = { item, position ->
            when (item) {
                is VideoGamesCardsDTO -> {
                    navigationHelper.navigate(item)
                }
                is SliderItem -> {
                    navigationHelper.navigate(item)
                }
            }
        }

        et_search.afterTextChanged {
            if (et_search.text.length > 2) {
                viewModel.homePageLiveFilterList.value.let {
                    val filter = it?.filter {
                        it?.name?.toLowerCase()!!.contains(et_search.text.toString().toLowerCase())
                    }
                    if (filter.isNullOrEmpty()) {
                        tv_no_data_found.visibile()
                        home_page_recyclerview.gone()
                    } else {
                        tv_no_data_found.gone()
                        home_page_recyclerview.visibile()
                        adapter.getAdapter().updateAllItems(viewModel.resultSearch(filter))
                    }
                }
            } else {
                viewModel.homePageList.clear()
                tv_no_data_found.gone()
                home_page_recyclerview.visibile()
                viewModel.getGamesList()
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun bindObserver() {
        viewModel.getGamesList()
        viewModel.gamesListPublishSubject.subscribe {
            adapter.getAdapter().updateAllItems(it)
        }

        viewModel.gamesListDataResult.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataFetchResult.Progress -> {
                    progress_bar.visibile()
                }
                is DataFetchResult.Failure -> {
                }
                is DataFetchResult.Success -> {
                    progress_bar.gone()
                }
            }
        })
    }
}