package com.base.videogames.ui.pages.favorites.ioc

import androidx.fragment.app.Fragment
import com.base.core.ioc.scopes.FragmentScope
import com.base.core.networking.Scheduler
import com.base.data.database.dao.FavoriteGamesDAO
import com.base.data.request.VideoGamesApiInterface
import com.base.videogames.ioc.keys.FragmentViewModelKey
import com.base.videogames.ui.base.fragment.BaseViewModelFragmentModule
import com.base.videogames.ui.base.viewmodel.BaseFragmentViewModel
import com.base.videogames.ui.pages.favorites.FavoritesFragment
import com.base.videogames.ui.pages.favorites.repository.FavoritesFragmentLocaleData
import com.base.videogames.ui.pages.favorites.repository.FavoritesFragmentRemoteData
import com.base.videogames.ui.pages.favorites.repository.FavoritesFragmentRepository
import com.base.videogames.ui.pages.favorites.viewmodel.FavoritesFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.reactivex.disposables.CompositeDisposable

// Created by Emre UYGUN on 4/10/21

@Module(includes = [BaseViewModelFragmentModule::class])
abstract class FavoritesFragmentModule {

    @Binds
    @FragmentScope
    abstract fun bindFragment(fragment: FavoritesFragment): Fragment

    @Binds
    @IntoMap
    @FragmentViewModelKey(FavoritesFragmentViewModel::class)
    @FragmentScope
    abstract fun bindViewModel(viewModel: FavoritesFragmentViewModel): BaseFragmentViewModel

    @Module
    companion object {

        @Provides
        @FragmentScope
        @JvmStatic
        fun provideFavoritesRemoteData(apiInterface: VideoGamesApiInterface) =
            FavoritesFragmentRemoteData(apiInterface)

        @Provides
        @FragmentScope
        @JvmStatic
        fun provideFavoritesLocaleData(favoriteGamesDAO: FavoriteGamesDAO) =
            FavoritesFragmentLocaleData(favoriteGamesDAO)

        @Provides
        @FragmentScope
        @JvmStatic
        fun provideFavoritesRepository(
            remote: FavoritesFragmentRemoteData,
            locale: FavoritesFragmentLocaleData,
            scheduler: Scheduler,
            compositeDisposable: CompositeDisposable
        ) = FavoritesFragmentRepository(remote, locale, scheduler, compositeDisposable)
    }
}