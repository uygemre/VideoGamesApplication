package com.base.videogames.ui.pages.home.ioc

import androidx.fragment.app.Fragment
import com.base.core.ioc.scopes.FragmentScope
import com.base.core.networking.Scheduler
import com.base.data.request.VideoGamesApiInterface
import com.base.videogames.ioc.keys.FragmentViewModelKey
import com.base.videogames.ui.base.fragment.BaseViewModelFragmentModule
import com.base.videogames.ui.base.viewmodel.BaseFragmentViewModel
import com.base.videogames.ui.pages.home.HomeFragment
import com.base.videogames.ui.pages.home.repository.HomeFragmentRemoteData
import com.base.videogames.ui.pages.home.repository.HomeFragmentRepository
import com.base.videogames.ui.pages.home.viewmodel.HomeFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.reactivex.disposables.CompositeDisposable

// Created by Emre UYGUN on 4/10/21

@Module(includes = [BaseViewModelFragmentModule::class])
abstract class HomeFragmentModule {

    @Binds
    @FragmentScope
    abstract fun bindFragment(fragment: HomeFragment): Fragment

    @Binds
    @IntoMap
    @FragmentViewModelKey(HomeFragmentViewModel::class)
    @FragmentScope
    abstract fun bindViewModel(viewModel: HomeFragmentViewModel): BaseFragmentViewModel

    @Module
    companion object {

        @Provides
        @FragmentScope
        @JvmStatic
        fun provideHomeRemoteData(apiInterface: VideoGamesApiInterface) =
            HomeFragmentRemoteData(apiInterface)

        @Provides
        @FragmentScope
        @JvmStatic
        fun provideHomeRepository(
            remote: HomeFragmentRemoteData,
            scheduler: Scheduler,
            compositeDisposable: CompositeDisposable
        ) = HomeFragmentRepository(remote, scheduler, compositeDisposable)
    }
}