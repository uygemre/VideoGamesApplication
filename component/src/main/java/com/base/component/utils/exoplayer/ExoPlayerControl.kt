package com.base.component.utils.exoplayer

import android.os.Bundle

interface ExoPlayerControl {

    fun createPlayer(isToPrepare: Boolean)

    fun preparePlayer()

    fun releasePlayer()

    fun releaseAdsLoader()

    fun updateVideoUrls(urls: Array<String>)

    fun playerPause()

    fun playerPlay()

    fun playerNext()

    fun playerPrevious()

    fun seekTo(windowIndex: Int, positionMs: Long)

    fun seekToDefaultPosition()

    fun setExoPlayerEventsListener(pExoPlayerListenerListener: ExoPlayerListener)

    fun setExoAdListener(exoAdListener: ExoAdListener)

    fun setExoThumbListener(exoThumbListener: ExoThumbListener)

    fun onActivityStart()

    fun onActivityResume()

    fun onActivityPause()

    fun onActivityStop()

    fun onActivityDestroy()

    fun onSaveInstanceState(outState: Bundle)

    fun playerBlock()

    fun playerUnBlock()
}