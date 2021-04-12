package com.base.videogames.ioc.builder

import com.base.core.ioc.scopes.ActivityScope
import com.base.videogames.ui.pages.detail.DetailActivity
import com.base.videogames.ui.pages.detail.ioc.DetailActivityModule
import com.base.videogames.ui.pages.main.MainActivity
import com.base.videogames.ui.pages.main.ioc.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    internal abstract fun bindMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [DetailActivityModule::class])
    internal abstract fun bindDetailActivity(): DetailActivity

}