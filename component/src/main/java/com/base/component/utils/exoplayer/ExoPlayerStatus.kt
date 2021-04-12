package com.base.component.utils.exoplayer

interface ExoPlayerStatus {

    val isPlayerVideoMuted: Boolean

    val currentWindowIndex: Int

    val currentPosition: Long

    val duration: Long

    val isPlayerCreated: Boolean

    val isPlayerPrepared: Boolean

    val isPlayingAd: Boolean

}