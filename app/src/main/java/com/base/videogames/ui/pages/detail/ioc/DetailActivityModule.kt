package com.base.videogames.ui.pages.detail.ioc

import androidx.appcompat.app.AppCompatActivity
import com.base.core.ioc.scopes.ActivityScope
import com.base.core.networking.Scheduler
import com.base.data.request.VideoGamesApiInterface
import com.base.videogames.ioc.builder.FragmentBuilderModule
import com.base.videogames.ioc.keys.ActivityViewModelKey
import com.base.videogames.ioc.modules.database.videogames.VideoGamesService
import com.base.videogames.ui.base.activity.BaseActivityModule
import com.base.videogames.ui.base.viewmodel.BaseActivityViewModel
import com.base.videogames.ui.pages.detail.DetailActivity
import com.base.videogames.ui.pages.detail.repository.DetailActivityRemoteData
import com.base.videogames.ui.pages.detail.repository.DetailActivityRepository
import com.base.videogames.ui.pages.detail.viewmodel.DetailActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.reactivex.disposables.CompositeDisposable

// Created by Emre UYGUN on 4/10/21

@Module(includes = [BaseActivityModule::class, FragmentBuilderModule::class, VideoGamesService::class])
abstract class DetailActivityModule {

    @Binds
    @ActivityScope
    abstract fun bindActivity(activity: DetailActivity): AppCompatActivity

    @Binds
    @IntoMap
    @ActivityViewModelKey(DetailActivityViewModel::class)
    @ActivityScope
    abstract fun bindViewModel(activityViewModel: DetailActivityViewModel): BaseActivityViewModel

    @Module
    companion object {

        @Provides
        @ActivityScope
        @JvmStatic
        fun provideDetailActivityRemoteData(apiInterface: VideoGamesApiInterface) =
            DetailActivityRemoteData(apiInterface)

        @Provides
        @ActivityScope
        @JvmStatic
        fun provideDetailActivityRepository(
            remote: DetailActivityRemoteData,
            scheduler: Scheduler,
            compositeDisposable: CompositeDisposable
        ) = DetailActivityRepository(remote, scheduler, compositeDisposable)
    }
}