package com.base.core.ioc.modules

import android.accounts.AccountManager
import android.content.Context
import android.content.SharedPreferences
import com.base.core.constants.AppConstants.PREF_NAME
import com.base.core.helpers.LocalPrefManager
import com.base.core.ioc.qualifiers.ApplicationContext
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
object SystemServiceModule {

    @Provides
    @Singleton
    @JvmStatic
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    @JvmStatic
    fun provideLocalPrefManager(sharedPreferences: SharedPreferences): LocalPrefManager =
        LocalPrefManager(sharedPreferences)

    @Provides
    @Singleton
    @JvmStatic
    fun provideAccountManager(@ApplicationContext context: Context): AccountManager =
        AccountManager.get(context)

    @Provides
    @Singleton
    @JvmStatic
    fun compositeDisposable(): CompositeDisposable = CompositeDisposable()
}