package com.base.videogames.ioc.builder

import com.base.core.ioc.scopes.FragmentScope
import com.base.videogames.ui.pages.home.HomeFragment
import com.base.videogames.ui.pages.home.ioc.HomeFragmentModule
import com.base.videogames.ui.pages.favorites.FavoritesFragment
import com.base.videogames.ui.pages.favorites.ioc.FavoritesFragmentModule
import com.base.videogames.ui.pages.gamescarddetail.GamesCardDetailFragment
import com.base.videogames.ui.pages.gamescarddetail.ioc.GamesCardDetailFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun contributeHomeFragment(): HomeFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [FavoritesFragmentModule::class])
    abstract fun contributeFavoritesFragment(): FavoritesFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [GamesCardDetailFragmentModule::class])
    abstract fun contributeGamesCardDetailFragment(): GamesCardDetailFragment

}