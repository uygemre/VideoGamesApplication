@file:Suppress("unused")

package com.base.videogames.ui.base.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.base.core.ioc.qualifiers.FragmentContext
import com.base.core.ioc.scopes.FragmentScope
import com.base.videogames.ioc.factory.FragmentViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class BaseViewModelFragmentModule {

    @Binds
    @FragmentScope
    @FragmentContext
    abstract fun bindViewModelFactory(viewModelFactory: FragmentViewModelFactory): ViewModelProvider.Factory

    @Module
    companion object {
        @Provides
        @FragmentScope
        @FragmentContext
        @JvmStatic
        fun provideViewModelProvider(
            fragment: Fragment,
            @FragmentContext viewModelFactory: ViewModelProvider.Factory
        ): ViewModelProvider =
            ViewModelProviders.of(fragment, viewModelFactory)
    }
}
