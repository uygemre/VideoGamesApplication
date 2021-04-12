@file:Suppress("unused")

package com.base.videogames.ui.pages.main.ioc

import androidx.appcompat.app.AppCompatActivity
import com.base.core.ioc.scopes.ActivityScope
import com.base.core.networking.Scheduler
import com.base.videogames.ioc.builder.FragmentBuilderModule
import com.base.videogames.ioc.keys.ActivityViewModelKey
import com.base.videogames.ui.pages.main.MainActivity
import com.base.videogames.ui.pages.main.viewmodel.MainActivityViewModel
import com.base.videogames.ui.base.activity.BaseActivityModule
import com.base.videogames.ui.base.viewmodel.BaseActivityViewModel
import com.base.data.request.VideoGamesApiInterface
import com.base.videogames.ioc.modules.database.videogames.VideoGamesService
import com.base.videogames.ui.pages.main.repository.MainActivityRemoteData
import com.base.videogames.ui.pages.main.repository.MainActivityRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.reactivex.disposables.CompositeDisposable

// Created by Emre UYGUN on 4/10/21

@Module(includes = [BaseActivityModule::class, FragmentBuilderModule::class, VideoGamesService::class])
abstract class MainActivityModule {

    @Binds
    @ActivityScope
    abstract fun bindActivity(activity: MainActivity): AppCompatActivity

    @Binds
    @IntoMap
    @ActivityViewModelKey(MainActivityViewModel::class)
    @ActivityScope
    abstract fun bindViewModel(activityViewModel: MainActivityViewModel): BaseActivityViewModel

    @Module
    companion object {

        @Provides
        @ActivityScope
        @JvmStatic
        fun provideMainActivityRemoteData(apiInterface: VideoGamesApiInterface) =
            MainActivityRemoteData(apiInterface)

        @Provides
        @ActivityScope
        @JvmStatic
        fun provideMainActivityRepository(
            remote: MainActivityRemoteData,
            scheduler: Scheduler,
            compositeDisposable: CompositeDisposable
        ) = MainActivityRepository(remote, scheduler, compositeDisposable)
    }
}
