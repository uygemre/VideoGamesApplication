package com.base.videogames.application
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import com.evernote.android.state.StateSaver
import dagger.android.support.DaggerApplication
import timber.log.Timber
import javax.inject.Inject

open class Application : DaggerApplication() {

    @Inject
    protected lateinit var timberTree: Timber.Tree

    var mHandler: Handler

    init {
        instance = this
        mHandler = Handler()
    }

    private val applicationInjector =
        DaggerApplicationComponent
            .builder()
            .application(this)
            .build()

    companion object {
        private var instance: Application? = null

        var isNewsSwipeSelected = false

        fun getAppContext(): Context {
            return instance!!.applicationContext
        }

        fun getInstance(): Application {
            return instance!!
        }

        fun getStrWithID(stringResourceID: Int): String {
            try {
                return instance!!.resources.getString(stringResourceID)
            } catch (ignored: Exception) {
            }

            return ""
        }

        fun getDimenWithID(dimenResourceID: Int): Int {
            try {
                return instance!!.resources.getDimensionPixelSize(dimenResourceID)
            } catch (e: Exception) {
            }

            return 10
        }
    }


    override fun onCreate() {
        super.onCreate()
        Timber.plant(timberTree)

        registerActivityLifecycleCallbacks(AppLifecycleCallbacks())

        StateSaver.setEnabledForAllActivitiesAndSupportFragments(this, true)
    }

    override fun applicationInjector() = applicationInjector


    class AppLifecycleCallbacks : android.app.Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

        }

        override fun onActivityStarted(activity: Activity) {
        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {

        }
    }
}

