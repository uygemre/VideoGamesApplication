package com.base.videogames.ui.pages.gamescarddetail.ioc

import androidx.fragment.app.Fragment
import com.base.core.ioc.scopes.FragmentScope
import com.base.core.networking.Scheduler
import com.base.data.database.dao.FavoriteGamesDAO
import com.base.data.request.VideoGamesApiInterface
import com.base.videogames.ioc.keys.FragmentViewModelKey
import com.base.videogames.ui.base.fragment.BaseViewModelFragmentModule
import com.base.videogames.ui.base.viewmodel.BaseFragmentViewModel
import com.base.videogames.ui.pages.gamescarddetail.GamesCardDetailFragment
import com.base.videogames.ui.pages.gamescarddetail.repository.GamesCardDetailFragmentLocaleData
import com.base.videogames.ui.pages.gamescarddetail.repository.GamesCardDetailFragmentRemoteData
import com.base.videogames.ui.pages.gamescarddetail.repository.GamesCardDetailFragmentRepository
import com.base.videogames.ui.pages.gamescarddetail.viewmodel.GamesCardDetailFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.reactivex.disposables.CompositeDisposable

// Created by Emre UYGUN on 4/10/21

@Module(includes = [BaseViewModelFragmentModule::class])
abstract class GamesCardDetailFragmentModule {

    @Binds
    @FragmentScope
    abstract fun bindFragment(fragment: GamesCardDetailFragment): Fragment

    @Binds
    @IntoMap
    @FragmentViewModelKey(GamesCardDetailFragmentViewModel::class)
    @FragmentScope
    abstract fun bindViewModel(viewModel: GamesCardDetailFragmentViewModel): BaseFragmentViewModel

    @Module
    companion object {

        @Provides
        @FragmentScope
        @JvmStatic
        fun gamesCardDetailFragmentRemoteData(apiInterface: VideoGamesApiInterface) =
            GamesCardDetailFragmentRemoteData(apiInterface)

        @Provides
        @FragmentScope
        @JvmStatic
        fun gamesCardDetailFragmentLocaleData(favoriteGamesDao: FavoriteGamesDAO) =
            GamesCardDetailFragmentLocaleData(favoriteGamesDao)

        @Provides
        @FragmentScope
        @JvmStatic
        fun gamesCardDetailFragmentRepository(
            remote: GamesCardDetailFragmentRemoteData,
            locale: GamesCardDetailFragmentLocaleData,
            scheduler: Scheduler,
            compositeDisposable: CompositeDisposable
        ) = GamesCardDetailFragmentRepository(remote, locale, scheduler, compositeDisposable)
    }
}