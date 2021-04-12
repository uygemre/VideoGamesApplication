package com.base.component.utils.exoplayer


interface ExoAdListener {

    fun onAdPlay()

    fun onAdPause()

    fun onAdResume()

    fun onAdEnded()

    fun onAdError()

    fun onAdClicked()

    fun onAdTapped()
}