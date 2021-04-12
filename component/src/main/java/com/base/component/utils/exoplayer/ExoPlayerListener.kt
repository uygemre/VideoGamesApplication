package com.base.component.utils.exoplayer

interface ExoPlayerListener {

    fun onLoadingStatusChanged(isLoading: Boolean, bufferedPosition: Long, bufferedPercentage: Int)

    fun onPlayerPlaying(currentWindowIndex: Int)

    fun onPlayerPaused(currentWindowIndex: Int)

    fun onPlayerBuffering(currentWindowIndex: Int)

    fun onPlayerStateEnded(currentWindowIndex: Int)

    fun onPlayerStateIdle(currentWindowIndex: Int)

    fun onPlayerError(errorString: String)

    fun createExoPlayerCalled(isToPrepare: Boolean)

    fun releaseExoPlayerCalled()

    fun onVideoResumeDataLoaded(window: Int, position: Long, isResumeWhenReady: Boolean)

    fun onTracksChanged(
        currentWindowIndex: Int,
        nextWindowIndex: Int,
        isPlayBackStateReady: Boolean
    )

    fun onMuteStateChanged(isMuted: Boolean)

    fun onVideoTapped()

    /**
     * @return - true to handle the tap
     */
    fun onPlayBtnTap(): Boolean

    /**
     * @return - true to handle the tap
     */
    fun onPauseBtnTap(): Boolean

    fun onFullScreenBtnTap()
}