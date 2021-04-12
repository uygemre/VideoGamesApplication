package com.base.core.ioc.modules

import android.content.Context
import com.base.core.constants.AppConstants
import com.base.core.constants.AppConstants.MAX_MEMORY_CACHE
import com.base.core.constants.AppConstants.NETWORK_TIMEOUT
import com.base.core.helpers.LocalPrefManager
import com.base.core.ioc.qualifiers.ApplicationContext
import com.base.core.networking.Scheduler
import com.demiroren.core.networking.AppScheduler
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun scheduler(): Scheduler {
        return AppScheduler()
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory
    ): Retrofit {
        return Retrofit.Builder().baseUrl(AppConstants.API_URL)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .client(client)
            .build()
    }

    @Inject
    lateinit var localPrefManager: LocalPrefManager

    @Provides
    @Singleton
    fun providesInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()


            if (chain.request().url().uri().toString().contains("api/Soccer/GetMatchDetail")) {
                val credentials = Credentials.basic("admin!", "password!admin")
                request.addHeader("Authorization", credentials)
            }

            if (chain.request().url().toString().contains("datasources/authors/")) {
                var response = chain.proceed(request.build())
                var rawJson = response.body()!!.string()
                rawJson = rawJson.replace("\"author_articles\": {}", "\"author_articles\": []")
                response.newBuilder()
                    .body(ResponseBody.create(response.body()!!.contentType(), rawJson)).build()
            } else {
                chain.proceed(request.build())
            }
        }
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        interceptor: Interceptor,
        cache: Cache
    ): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(interceptor)
            .addInterceptor(logInterceptor)
            .connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
        return client.build()
    }

    @Provides
    @Singleton
    fun providesOkhttpCache(@ApplicationContext context: Context): Cache {
        return Cache(context.cacheDir, MAX_MEMORY_CACHE)
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun providesRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }
}
