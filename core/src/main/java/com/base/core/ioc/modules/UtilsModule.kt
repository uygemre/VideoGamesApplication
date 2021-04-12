package com.base.core.ioc.modules

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.base.core.ioc.scopes.AppScope
import dagger.Module
import dagger.Provides

@Module
class UtilsModule {

    @Provides
    @AppScope
    internal fun provideConnectivityManager(context: Application): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}
