package com.base.core.ioc.modules

import android.util.Log
import com.base.core.BuildConfig
import dagger.Module
import dagger.Provides
import timber.log.Timber
import javax.inject.Singleton


@Module
object TimberModule {
    @Provides
    @Singleton
    @JvmStatic
    fun provideTimberTree(): Timber.Tree =
        object : Timber.DebugTree() {
            override fun isLoggable(tag: String?, priority: Int) =
                BuildConfig.DEBUG || priority >= Log.INFO
        }
}