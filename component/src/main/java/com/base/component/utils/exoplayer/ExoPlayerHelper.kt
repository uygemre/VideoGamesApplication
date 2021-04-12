package com.base.component.utils.exoplayer
/*
import com.google.ads.interactivemedia.v3.api.AdEvent
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.BehindLiveWindowException
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.source.TrackGroupArray
import android.os.Bundle
import android.annotation.SuppressLint
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import android.view.MotionEvent
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSinkFactory
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import android.view.Gravity
import android.widget.FrameLayout
import com.google.android.exoplayer2.source.ads.AdsMediaSource
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.source.MergingMediaSource
import com.google.android.exoplayer2.source.SingleSampleMediaSource
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import android.widget.ProgressBar
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import com.demiroren.component.R
import com.demiroren.component.utils.exoplayer.*
import com.google.ads.interactivemedia.v3.api.player.VideoAdPlayer
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.util.Util
import com.google.android.exoplayer2.upstream.*
import java.io.File

/**
 * Created by Hamdullah KALAY on 17.06.2020
 */

class ExoPlayerHelper private constructor(
    private val mContext: Context?,
    private val mExoPlayerView: PlayerView?
) : View.OnClickListener,
    View.OnTouchListener, ExoPlayerControl, ExoPlayerStatus, Player.EventListener,
    VideoAdPlayer.VideoAdPlayerCallback, AdsMediaSource.MediaSourceFactory,
    AdEvent.AdEventListener {


    private var mPlayer: SimpleExoPlayer? = null
    private var mImaAdsLoader: ImaAdsLoader? = null
    private var mDataSourceFactory: DataSource.Factory? = null
    private var mLoadControl: DefaultLoadControl? = null
    private var mMediaSource: MediaSource? = null

    private var mExoAdListener: ExoAdListener? = null
    private var mExoPlayerListener: ExoPlayerListener? = null
    private var mExoThumbListener: ExoThumbListener? = null

    private var mProgressBar: ProgressBar? = null
    private var mBtnMute: ImageView? = null
    private var mBtnFullScreen: ImageView? = null
    private var mThumbImage: ImageView? = null

    private var mVideosUris: Array<Uri?>? = null
    private var mAdResponse: String? = null
    private var mSubTitlesUrls: ArrayList<String>? = null
    private var mTagUrl: String? = null
    private var mResumePosition = C.TIME_UNSET
    private var mResumeWindow = C.INDEX_UNSET
    private var mTempCurrentVolume: Float = 0.toFloat()
    override var isPlayerVideoMuted: Boolean = false

    private var isAdMuted: Boolean = false
    private var isRepeatModeOn: Boolean = false
    private var isAutoPlayOn: Boolean = false
    private var isResumePlayWhenReady: Boolean = false
    private var isAdWasShown: Boolean = false
    override var isPlayerPrepared: Boolean = false

    private var isToPrepareOnResume = true
    private var isThumbImageViewEnabled: Boolean = false
    private var isLiveStreamSupportEnabled: Boolean = false
    private val mBottomProgress: LinearLayoutCompat

    private val nextWindowIndex: Int
        get() =
            mPlayer!!.currentTimeline.getNextWindowIndex(
                mPlayer!!.currentWindowIndex,
                mPlayer!!.repeatMode,
                false
            )

    private val previousWindowIndex: Int
        get() =
            mPlayer!!.currentTimeline.getPreviousWindowIndex(
                mPlayer!!.currentWindowIndex,
                mPlayer!!.repeatMode,
                false
            )

    // It looks like the issue was solved and no need for this Runnable
    private val checkFreeze = Runnable {
        if (mPlayer != null && mPlayer!!.playbackState == Player.STATE_BUFFERING && mPlayer!!.playWhenReady) {
            Log.e("zaq", "Player.STATE_BUFFERING stuck issue")
            mPlayer!!.seekTo(if (mPlayer!!.contentPosition > 500) mPlayer!!.contentPosition - 500 else 0)
        }
    }

    /**
     * ExoPlayerStatus interface methods
     */
    override val isPlayerCreated: Boolean
        get() = mPlayer != null

    override val currentWindowIndex: Int
        get() = if (mPlayer != null) {
            mPlayer!!.currentWindowIndex
        } else {
            0
        }

    override val currentPosition: Long
        get() = if (mPlayer != null) {
            mPlayer!!.currentPosition
        } else {
            0
        }

    override val duration: Long
        get() = if (mPlayer != null) {
            mPlayer!!.duration
        } else {
            0
        }

    override val isPlayingAd: Boolean
        get() = mPlayer != null && mPlayer!!.isPlayingAd


    init {
        if (mContext == null) {
            throw IllegalArgumentException("ExoPlayerHelper constructor - Context can't be null")
        }

        if (mContext !is Activity) {
            throw IllegalArgumentException("ExoPlayerHelper constructor - Context must be an instance of Activity")
        }

        if (mExoPlayerView == null) {
            throw IllegalArgumentException("ExoPlayerHelper constructor - SimpleExoPlayerView can't be null")
        }

        mBottomProgress = mExoPlayerView.findViewById(R.id.bottom_progress)

        setVideoClickable()
        setControllerListener()
        init()
    }

    private fun addProgressBar(color: Int) {
        val frameLayout = mExoPlayerView!!.overlayFrameLayout
        mProgressBar = frameLayout?.findViewById(R.id.progressBar)
        if (mProgressBar != null) {
            return
        }
        mProgressBar = ProgressBar(mContext, null, android.R.attr.progressBarStyleLarge)
        mProgressBar!!.id = R.id.progressBar
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.CENTER
        mProgressBar!!.layoutParams = params
        mProgressBar!!.isIndeterminate = true
        mProgressBar!!.indeterminateDrawable.setColorFilter(
            if (color == 0) Color.RED else color,
            android.graphics.PorterDuff.Mode.SRC_IN
        )
        mProgressBar!!.visibility = View.GONE
        frameLayout?.addView(mProgressBar)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setVideoClickable() {
        mExoPlayerView!!.setOnTouchListener(this)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setControllerListener() {
        mExoPlayerView!!.findViewById<View>(R.id.exo_play).setOnTouchListener(this)
        mExoPlayerView.findViewById<View>(R.id.exo_pause).setOnTouchListener(this)

        mBtnMute = mExoPlayerView.findViewById(R.id.btnMute)
        mBtnFullScreen = mExoPlayerView.findViewById(R.id.btnFullScreen)
        mBtnMute!!.setOnTouchListener(this)
        mBtnFullScreen!!.setOnTouchListener(this)
    }


    private fun init() {
        // Measures bandwidth during playback. Can be null if not required.
        val bandwidthMeter = DefaultBandwidthMeter()

        // Produces DataSource instances through which media data is loaded.
        mDataSourceFactory = DefaultDataSourceFactory(
            mContext,
            Util.getUserAgent(mContext, mContext!!.getString(R.string.app_name)), bandwidthMeter
        )


        // LoadControl that controls when the MediaSource buffers more media, and how much media is buffered.
        // LoadControl is injected when the player is created.
        //removed deprecated DefaultLoadControl creation method
        val builder = DefaultLoadControl.Builder()
        builder.setAllocator(DefaultAllocator(true, 2 * 1024 * 1024))
        builder.setBufferDurationsMs(5000, 5000, 5000, 5000)
        builder.setPrioritizeTimeOverSizeThresholds(true)
        mLoadControl = builder.createDefaultLoadControl()
    }

    // Player creation and release
    private fun setVideoUrls(urls: Array<String>) {
        mVideosUris = arrayOfNulls(urls.size)
        for (i in urls.indices) {
            mVideosUris!![i] = Uri.parse(urls[i])
        }
    }

    private fun setAdResponse(response: String) {
        mAdResponse = response
    }

    private fun setSubtitlesUrls(list: ArrayList<String>) {
        mSubTitlesUrls = list
    }

    private fun createMediaSource() {
        // A MediaSource defines the media to be played, loads the media, and from which the loaded media can be read.
        // A MediaSource is injected via ExoPlayer.prepare at the start of playback.
        if (mVideosUris != null) {

            val mediaSources = arrayOfNulls<MediaSource>(mVideosUris!!.size)
            for (i in mVideosUris!!.indices) {
                mediaSources[i] = buildMediaSource(mVideosUris!![i]!!)

                if (mSubTitlesUrls != null && (i < mSubTitlesUrls!!.size) and (mSubTitlesUrls!![i] != null)) {
                    mediaSources[i] =
                        addSubTitlesToMediaSource(mediaSources[i]!!, mSubTitlesUrls!![i])
                }
            }

            mMediaSource =
                if (mediaSources.size == 1) mediaSources[0] else ConcatenatingMediaSource(*mediaSources)

            addAdsToMediaSource()

        }


    }

    private fun addSubTitlesToMediaSource(
        mediaSource: MediaSource,
        subTitlesUrl: String
    ): MediaSource {
        val textFormat = Format.createTextSampleFormat(
            null, MimeTypes.APPLICATION_SUBRIP,
            null, Format.NO_VALUE, Format.NO_VALUE, "en", Format.NO_VALUE, null
        )
        val uri = Uri.parse(subTitlesUrl)
        val subtitleSource = SingleSampleMediaSource.Factory(mDataSourceFactory!!)
            .createMediaSource(uri, textFormat, C.TIME_UNSET)
        return MergingMediaSource(mediaSource, subtitleSource)
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val type = Util.inferContentType(uri)
        return when (type) {
            C.TYPE_SS -> SsMediaSource.Factory(mDataSourceFactory).createMediaSource(uri)
            C.TYPE_DASH -> DashMediaSource.Factory(mDataSourceFactory).createMediaSource(uri)
            C.TYPE_HLS -> HlsMediaSource.Factory(mDataSourceFactory).createMediaSource(uri)
            C.TYPE_OTHER -> ExtractorMediaSource.Factory(mDataSourceFactory).createMediaSource(uri)
            else -> {
                throw IllegalStateException("Unsupported type: $type")
            }
        }
    }

    private fun addAdsToMediaSource() {
        if (mMediaSource == null) {
            throw IllegalStateException("setVideoUrls must be invoked before setTagUrl (mMediaSource is null)")
        }
        if (!isAdWasShown) {
            if (mTagUrl != null) {
                if (mImaAdsLoader == null) {
                    mImaAdsLoader = ImaAdsLoader.Builder(mContext!!)
                        .setAdEventListener(this)
                        .buildForAdTag(Uri.parse(mTagUrl))
                    //mImaAdsLoader = new ImaAdsLoader(mContext, Uri.parse(mTagUrl));
                    mImaAdsLoader!!.addCallback(this)
                }
            } else if (mAdResponse != null) {
                if (mImaAdsLoader == null) {
                    mImaAdsLoader = ImaAdsLoader.Builder(mContext!!)
                        .setAdEventListener(this)
                        .buildForAdsResponse(mAdResponse)
                    //mImaAdsLoader = new ImaAdsLoader(mContext, Uri.parse(mTagUrl));
                    mImaAdsLoader!!.addCallback(this)
                }
            }
        } else {
            return
        }

        mMediaSource = AdsMediaSource(
            mMediaSource,
            this,
            mImaAdsLoader!!,
            mExoPlayerView!!.overlayFrameLayout
        )
    }

    private fun setProgressVisible(visible: Boolean) {
        if (mProgressBar != null) {
            mProgressBar!!.visibility = if (visible) View.VISIBLE else View.GONE
        }
    }

    private fun addThumbImageView() {
        if (mThumbImage != null) {
            return
        }
        val frameLayout =
            mExoPlayerView!!.findViewById<AspectRatioFrameLayout>(R.id.exo_content_frame)
        mThumbImage = ImageView(mContext)
        mThumbImage!!.id = R.id.thumbImg
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        params.gravity = Gravity.CENTER
        mThumbImage!!.layoutParams = params
        mThumbImage!!.setBackgroundColor(Color.BLACK)
        frameLayout.addView(mThumbImage)

        if (mExoThumbListener != null) {
            mExoThumbListener!!.onThumbImageViewReady(mThumbImage!!)
        }
    }

    private fun removeThumbImageView() {
        if (mThumbImage != null) {
            val frameLayout =
                mExoPlayerView!!.findViewById<AspectRatioFrameLayout>(R.id.exo_content_frame)
            frameLayout.removeView(mThumbImage)
            mThumbImage = null
        }
    }

    private fun setUiControllersVisibility(visibility: Boolean) {
        mExoPlayerView!!.useController = visibility
        if (!visibility) {
            val frameLayout =
                mExoPlayerView.findViewById<AspectRatioFrameLayout>(R.id.exo_content_frame)
            frameLayout.setOnClickListener(this)
        }
    }

    @SuppressLint("RtlHardcoded")
    private fun addMuteButton(isAdMuted: Boolean, isVideoMuted: Boolean) {
        this.isPlayerVideoMuted = isVideoMuted
        this.isAdMuted = isAdMuted
        mBtnMute!!.setImageResource(if (this.isPlayerVideoMuted) R.drawable.ic_action_mute else R.drawable.ic_action_volume_up)

        /*     FrameLayout frameLayout = mExoPlayerView.getOverlayFrameLayout();
        mBtnMute = new ImageView(mContext);
        mBtnMute.setId(R.id.muteBtn);
        mBtnMute.setImageResource(this.isVideoMuted ? R.drawable.ic_action_mute : R.drawable.ic_action_volume_up);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.LEFT | Gravity.BOTTOM;
        //params.bottomMargin = Math.round(3 * mExoPlayerView.getContext().getResources().getDisplayMetrics().density);
        mBtnMute.setLayoutParams(params);

        mBtnMute.setOnClickListener(this);

        frameLayout.addView(mBtnMute);*/
    }

    private fun updateMutedStatus() {
        val isMuted =
            mPlayer!!.isPlayingAd && isAdMuted || !mPlayer!!.isPlayingAd && isPlayerVideoMuted
        if (isMuted) {
            mPlayer!!.volume = 0f
        } else {
            mPlayer!!.volume = mTempCurrentVolume
        }
        if (mBtnMute != null) {
            mBtnMute!!.setImageResource(if (isMuted) R.drawable.ic_action_mute else R.drawable.ic_action_volume_up)
        }
    }

    private fun enableCache(maxCacheSizeMb: Int) {
        val evictor = LeastRecentlyUsedCacheEvictor((maxCacheSizeMb * 1024 * 1024).toLong())
        val file = File(mContext!!.cacheDir, "media")
        Log.d("ZAQ", "enableCache (" + maxCacheSizeMb + " MB), file: " + file.getAbsolutePath())
        val simpleCache = SimpleCache(file, evictor)
        mDataSourceFactory = CacheDataSourceFactory(
            simpleCache,
            mDataSourceFactory,
            FileDataSourceFactory(),
            CacheDataSinkFactory(simpleCache, (2 * 1024 * 1024).toLong()),
            CacheDataSource.FLAG_BLOCK_ON_CACHE or CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR,
            object : CacheDataSource.EventListener {
                override fun onCacheIgnored(reason: Int) {
                    Log.d("ZAQ", "onCacheIgnored")
                }

                override fun onCachedBytesRead(cacheSizeBytes: Long, cachedBytesRead: Long) {
                    Log.d(
                        "ZAQ",
                        "onCachedBytesRead , cacheSizeBytes: $cacheSizeBytes   cachedBytesRead: $cachedBytesRead"
                    )
                }
            })
    }

    override fun onClick(v: View) {
        // On video tap
        if (mExoPlayerListener != null && v.getId() === R.id.exo_content_frame) {
            mExoPlayerListener!!.onVideoTapped()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        if (motionEvent.action == MotionEvent.ACTION_UP && mExoPlayerListener != null) {
            if (view.getId() === mExoPlayerView!!.id) {
                mExoPlayerListener!!.onVideoTapped()
            }
            if (view.getId() === R.id.exo_play) {
                if (mExoPlayerListener!!.onPlayBtnTap()) {
                    return true
                }
            }
            if (view.id === R.id.exo_pause) {
                if (mExoPlayerListener!!.onPauseBtnTap()) {
                    return true
                }
            }

            if (view.getId() === R.id.btnFullScreen) {
                if (mExoPlayerListener != null) {
                    mExoPlayerListener!!.onFullScreenBtnTap()
                }
                return true
            }
            if (view.getId() === R.id.btnMute) {
                if (mPlayer!!.isPlayingAd) {
                    isAdMuted = !isAdMuted
                    isPlayerVideoMuted = isAdMuted
                } else {
                    isPlayerVideoMuted = !isPlayerVideoMuted
                    isAdMuted = isPlayerVideoMuted
                }
                (view as ImageView).setImageResource(if (isPlayerVideoMuted) R.drawable.ic_action_mute else R.drawable.ic_action_volume_up)
                updateMutedStatus()
                if (mExoPlayerListener != null) {
                    mExoPlayerListener!!.onMuteStateChanged(isPlayerVideoMuted)
                }
                return true
            }
        }

        // Player block
        return view.getId() === mExoPlayerView!!.overlayFrameLayout?.id

    }

    // Resume position saving
    private fun addSavedInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            isAdWasShown = savedInstanceState.getBoolean(PARAM_IS_AD_WAS_SHOWN, false)
            isResumePlayWhenReady = savedInstanceState.getBoolean(PARAM_AUTO_PLAY, true)
            mResumeWindow = savedInstanceState.getInt(PARAM_WINDOW, C.INDEX_UNSET)
            mResumePosition = savedInstanceState.getLong(PARAM_POSITION, C.TIME_UNSET)
        }
    }

    private fun updateResumePosition() {
        isResumePlayWhenReady = mPlayer!!.playWhenReady
        mResumeWindow = mPlayer!!.currentWindowIndex
        mResumePosition = Math.max(0, mPlayer!!.contentPosition)
    }

    private fun clearResumePosition() {
        mResumeWindow = C.INDEX_UNSET
        mResumePosition = C.TIME_UNSET
    }

    // Player events, internal handle
    private fun onPlayerBuffering() {
        if (mPlayer!!.playWhenReady) {
            setProgressVisible(true)
        }
    }

    private fun onPlayerPlaying() {
        setProgressVisible(false)
        removeThumbImageView()
        updateMutedStatus()
    }

    private fun onPlayerLoadingChanged() {
        liveStreamCheck()
    }

    private fun liveStreamCheck() {
        if (isLiveStreamSupportEnabled) {
            val isLiveStream =
                mPlayer!!.isCurrentWindowDynamic || !mPlayer!!.isCurrentWindowSeekable
            /*            Log.e("ZAQ", "isCurrentWindowDynamic: " + mPlayer.isCurrentWindowDynamic());
            Log.e("ZAQ", "isCurrentWindowSeekable: " + mPlayer.isCurrentWindowDynamic());
            Log.e("ZAQ", "isLiveStream: " + isLiveStream);*/
            mBottomProgress.visibility = if (isLiveStream) View.GONE else View.VISIBLE
        }
    }

    private fun onPlayerPaused() {
        setProgressVisible(false)
    }

    private fun onAdEnded() {
        updateMutedStatus()
        isAdWasShown = true
    }

    class Builder(context: Context, exoPlayerView: PlayerView) {

        private val mExoPlayerHelper: ExoPlayerHelper =
            ExoPlayerHelper(
                context,
                exoPlayerView
            )

        fun addMuteButton(isAdMuted: Boolean, isVideoMuted: Boolean): Builder {
            mExoPlayerHelper.addMuteButton(isAdMuted, isVideoMuted)
            return this
        }

        fun setUiControllersVisibility(visibility: Boolean): Builder {
            mExoPlayerHelper.setUiControllersVisibility(visibility)
            return this
        }

        fun setVideoUrls(urls: Array<String>): Builder {
            mExoPlayerHelper.setVideoUrls(urls)
            return this
        }

        fun setAdResponse(response: String): Builder {
            mExoPlayerHelper.setAdResponse(response)
            return this
        }

        fun setSubTitlesUrls(list: ArrayList<String>): Builder {
            mExoPlayerHelper.setSubtitlesUrls(list)
            return this
        }

        fun setTagUrl(tagUrl: String): Builder {
            mExoPlayerHelper.mTagUrl = tagUrl
            return this
        }

        fun setRepeatModeOn(isOn: Boolean): Builder {
            mExoPlayerHelper.isRepeatModeOn = isOn
            return this
        }

        fun setAutoPlayOn(isAutoPlayOn: Boolean): Builder {
            mExoPlayerHelper.isAutoPlayOn = isAutoPlayOn
            return this
        }

        fun setExoPlayerEventsListener(exoPlayerListener: ExoPlayerListener): Builder {
            mExoPlayerHelper.setExoPlayerEventsListener(exoPlayerListener)
            return this
        }

        fun setExoAdEventsListener(exoAdListener: ExoAdListener): Builder {
            mExoPlayerHelper.setExoAdListener(exoAdListener)
            return this
        }

        fun addSavedInstanceState(savedInstanceState: Bundle): Builder {
            mExoPlayerHelper.addSavedInstanceState(savedInstanceState)
            return this
        }

        fun setThumbImageViewEnabled(exoThumbListener: ExoThumbListener): Builder {
            mExoPlayerHelper.setExoThumbListener(exoThumbListener)
            return this
        }

        fun enableCache(maxCacheSizeMb: Int): Builder {
            mExoPlayerHelper.enableCache(maxCacheSizeMb)
            return this
        }

        /**
         * If you have a list of videos set isToPrepareOnResume to be false
         * to prevent auto prepare on activity onResume/onCreate
         */
        fun setToPrepareOnResume(toPrepareOnResume: Boolean): Builder {
            mExoPlayerHelper.isToPrepareOnResume = toPrepareOnResume
            return this
        }

        fun enableLiveStreamSupport(): Builder {
            mExoPlayerHelper.isLiveStreamSupportEnabled = true
            return this
        }


        fun addProgressBarWithColor(colorAccent: Int): Builder {
            mExoPlayerHelper.addProgressBar(colorAccent)
            return this
        }

        fun setFullScreenBtnVisible(): Builder {
            mExoPlayerHelper.mBtnFullScreen!!.setVisibility(View.VISIBLE)
            return this
        }

        fun setMuteBtnVisible(): Builder {
            mExoPlayerHelper.mBtnMute!!.setVisibility(View.VISIBLE)
            return this
        }

        /**
         * Probably you will feel a need to use that method when you need to show pre-roll ad
         * and you not interested in auto play. That method allows to separate player creation
         * from calling prepare()
         * Note: To play ad/content you ned to call preparePlayer()
         *
         * @return ExoPlayerHelper instance
         */
        fun create(): ExoPlayerHelper {
            mExoPlayerHelper.createPlayer(false)
            return mExoPlayerHelper
        }

        /**
         * Note: If you added tagUrl ad would start playing automatic even if you had set setAutoPlayOn(false)
         *
         * @return ExoPlayerHelper instance
         */
        fun createAndPrepare(): ExoPlayerHelper {
            mExoPlayerHelper.createPlayer(true)
            return mExoPlayerHelper
        }
    }


    /**
     * ExoPlayerControl interface methods
     */
    override fun setExoThumbListener(exoThumbListener: ExoThumbListener) {
        isThumbImageViewEnabled = true
        mExoThumbListener = exoThumbListener
    }

    override fun setExoPlayerEventsListener(pExoPlayerListenerListener: ExoPlayerListener) {
        mExoPlayerListener = pExoPlayerListenerListener
    }

    override fun setExoAdListener(exoAdListener: ExoAdListener) {
        mExoAdListener = exoAdListener
    }

    override fun createPlayer(isToPrepare: Boolean) {
        if (mExoPlayerListener != null) {
            mExoPlayerListener!!.createExoPlayerCalled(isToPrepare)
        }
        if (mPlayer != null) {
            return
        }

        if (isThumbImageViewEnabled) {
            addThumbImageView()
        }

        mPlayer = ExoPlayerFactory.newSimpleInstance(
            mContext,
            DefaultRenderersFactory(mContext),
            DefaultTrackSelector(),
            mLoadControl
        )

        mExoPlayerView!!.player = mPlayer
        mExoPlayerView.controllerShowTimeoutMs = 1500
        mExoPlayerView.controllerHideOnTouch = false

        mTempCurrentVolume = mPlayer!!.volume

        mPlayer!!.repeatMode =
            if (isRepeatModeOn) Player.REPEAT_MODE_ALL else Player.REPEAT_MODE_OFF
        mPlayer!!.playWhenReady = isAutoPlayOn
        mPlayer!!.addListener(this)

        createMediaSource()

        if (isToPrepare) {
            preparePlayer()
        }
    }

    override fun preparePlayer() {
        if (mPlayer == null || isPlayerPrepared) {
            return
        }
        isPlayerPrepared = true

        mPlayer!!.prepare(mMediaSource)

        if (mResumeWindow != C.INDEX_UNSET && !mPlayer!!.isPlayingAd) {
            mPlayer!!.playWhenReady = isResumePlayWhenReady
            mPlayer!!.seekTo(mResumeWindow, mResumePosition + 100)
            if (mExoPlayerListener != null) {
                mExoPlayerListener!!.onVideoResumeDataLoaded(
                    mResumeWindow,
                    mResumePosition,
                    isResumePlayWhenReady
                )
            }
            //mExoPlayerView.postDelayed(checkFreeze, 1000);
        }
    }

    override fun releasePlayer() {
        isPlayerPrepared = false

        if (mExoPlayerListener != null) {
            mExoPlayerListener!!.releaseExoPlayerCalled()
        }
        if (mPlayer != null) {
            updateResumePosition()
            removeThumbImageView()
            mPlayer!!.release()
            mPlayer = null
        }
    }

    override fun seekToDefaultPosition() {
        if (mPlayer != null) {
            mPlayer!!.seekToDefaultPosition()
        }
    }

    override fun releaseAdsLoader() {
        if (mImaAdsLoader != null) {
            mImaAdsLoader!!.release()
            mImaAdsLoader = null
            mExoPlayerView!!.overlayFrameLayout?.removeAllViews()
        }
    }

    override fun updateVideoUrls(urls: Array<String>) {
        if (!isPlayerPrepared) {
            setVideoUrls(urls)
            createMediaSource()
        } else {
            throw IllegalStateException("Can't update url's when player is prepared")
        }
    }

    override fun playerPause() {
        if (mPlayer != null) {
            mPlayer!!.playWhenReady = false
        }
    }

    override fun playerPlay() {
        if (mPlayer != null) {
            mPlayer!!.playWhenReady = true
        }
    }

    override fun playerNext() {
        if (mPlayer != null) {
            seekTo(nextWindowIndex, 0)
        }
    }

    override fun playerPrevious() {
        if (mPlayer != null) {
            seekTo(previousWindowIndex, 0)
        }
    }

    override fun seekTo(windowIndex: Int, positionMs: Long) {
        if (mPlayer != null) {
            mPlayer!!.seekTo(windowIndex, positionMs)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun playerBlock() {
        mExoPlayerView?.overlayFrameLayout?.setOnTouchListener(this)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun playerUnBlock() {
        mExoPlayerView?.overlayFrameLayout?.setOnTouchListener(null)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(PARAM_IS_AD_WAS_SHOWN, !mPlayer!!.isPlayingAd)
        outState.putBoolean(PARAM_AUTO_PLAY, mPlayer!!.playWhenReady)
        outState.putInt(PARAM_WINDOW, mPlayer!!.currentWindowIndex)
        outState.putLong(PARAM_POSITION, mPlayer!!.contentPosition)
    }

    override fun onActivityStart() {
        if (Util.SDK_INT > 23) {
            createPlayer(isToPrepareOnResume)
        }
    }

    override fun onActivityResume() {
        if (Util.SDK_INT <= 23 || mPlayer == null) {
            createPlayer(isToPrepareOnResume)
        }
    }

    override fun onActivityPause() {
        releasePlayer()
    }

    override fun onActivityStop() {
        releasePlayer()
    }

    override fun onActivityDestroy() {
        releaseAdsLoader()
    }

    /**
     * ExoPlayer Player.EventListener methods
     */
    override fun onTracksChanged(
        trackGroups: TrackGroupArray?,
        trackSelections: TrackSelectionArray?
    ) {
        if (mExoPlayerListener != null) {
            mExoPlayerListener!!.onTracksChanged(
                mPlayer!!.currentWindowIndex,
                nextWindowIndex,
                mPlayer!!.playbackState == Player.STATE_READY
            )
        }
    }

    override fun onLoadingChanged(isLoading: Boolean) {
        onPlayerLoadingChanged()
        if (mExoPlayerListener != null) {
            mExoPlayerListener!!.onLoadingStatusChanged(
                isLoading,
                mPlayer!!.bufferedPosition,
                mPlayer!!.bufferedPercentage
            )
        }
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        if (mExoPlayerListener == null || mPlayer == null) {
            return
        }
        when (playbackState) {
            Player.STATE_READY -> if (playWhenReady) {
                onPlayerPlaying()
                mExoPlayerListener!!.onPlayerPlaying(mPlayer!!.currentWindowIndex)
            } else {
                onPlayerPaused()
                mExoPlayerListener!!.onPlayerPaused(mPlayer!!.currentWindowIndex)
            }
            Player.STATE_BUFFERING -> {
                onPlayerBuffering()
                mExoPlayerListener!!.onPlayerBuffering(mPlayer!!.currentWindowIndex)
            }
            Player.STATE_ENDED -> mExoPlayerListener!!.onPlayerStateEnded(mPlayer!!.currentWindowIndex)
            Player.STATE_IDLE -> mExoPlayerListener!!.onPlayerStateIdle(mPlayer!!.currentWindowIndex)
            else -> Log.e("ExoPlayerHelper-zaq", "onPlayerStateChanged unknown: $playbackState")
        }
    }

    override fun onPlayerError(e: ExoPlaybackException?) {
        var errorString: String? = null

        when (e!!.type) {
            ExoPlaybackException.TYPE_SOURCE -> {
                //https://github.com/google/ExoPlayer/issues/2702
                val ioException = e.sourceException
                Log.e("ExoPlayerHelper", ioException.localizedMessage)
                errorString = ioException.localizedMessage
            }
            ExoPlaybackException.TYPE_RENDERER -> {
                val exception = e.rendererException
                Log.e("ExoPlayerHelper", exception.message)
            }
            ExoPlaybackException.TYPE_UNEXPECTED -> {
                val runtimeException = e.unexpectedException
                Log.e(
                    "ExoPlayerHelper",
                    if (runtimeException.message == null) "Message is null" else runtimeException.message
                )
                if (runtimeException.message == null) {
                    runtimeException.printStackTrace()
                }
                errorString = runtimeException.message
            }
        }


        if (e.type == ExoPlaybackException.TYPE_RENDERER) {
            val cause = e.rendererException
            if (cause is MediaCodecRenderer.DecoderInitializationException) {
                // Special case for decoder initialization failures.
                if (cause.decoderName == null) {
                    if (cause.cause is MediaCodecUtil.DecoderQueryException) {
                        errorString = mContext!!.getString(R.string.error_querying_decoders)
                    } else if (cause.secureDecoderRequired) {
                        errorString = mContext!!.getString(
                            R.string.error_no_secure_decoder,
                            cause.mimeType
                        )
                    } else {
                        errorString = mContext!!.getString(
                            R.string.error_no_decoder,
                            cause.mimeType
                        )
                    }
                } else {
                    errorString = mContext!!.getString(
                        R.string.error_instantiating_decoder,
                        cause.decoderName
                    )
                }
            }
        }
        if (errorString != null) {
            Log.e("ExoPlayerHelper", "errorString: $errorString")
        }

        if (isBehindLiveWindow(
                e
            )
        ) {
            createPlayer(true)
            Log.e("ExoPlayerHelper", "isBehindLiveWindow is true")
        }


        if (mExoPlayerListener != null) {
            errorString?.let {
                mExoPlayerListener!!.onPlayerError(it)
            }
        }
    }

    override fun onRepeatModeChanged(repeatMode: Int) {

    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {

    }

    override fun onPositionDiscontinuity(reason: Int) {

    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {

    }

    override fun onSeekProcessed() {

    }

    override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {

    }

    /**
     * ImaAdsLoader.VideoAdPlayerCallback
     */

    override fun onPlay() {
        if (mExoAdListener != null) {
            mExoAdListener!!.onAdPlay()
        }
    }

    override fun onVolumeChanged(pI: Int) {

    }

    override fun onLoaded() {

    }

    override fun onPause() {
        if (mExoAdListener != null) {
            mExoAdListener!!.onAdPause()
        }
        mExoPlayerView?.removeCallbacks(checkFreeze)
    }

    override fun onResume() {
        if (mExoAdListener != null) {
            mExoAdListener!!.onAdResume()
        }
    }

    override fun onEnded() {
        onAdEnded()
        if (mExoAdListener != null) {
            mExoAdListener!!.onAdEnded()
        }
    }

    override fun onError() {
        if (mExoAdListener != null) {
            mExoAdListener!!.onAdError()
        }
    }


    /**
     * AdsMediaSource.MediaSourceFactory
     */
    override fun createMediaSource(uri: Uri): MediaSource {
        return buildMediaSource(uri)
    }

    override fun getSupportedTypes(): IntArray {
        return intArrayOf(C.TYPE_DASH, C.TYPE_HLS, C.TYPE_OTHER)
    }

    /**
     * AdEvent.AdEventListener
     */
    override fun onAdEvent(adEvent: AdEvent) {
        if (mExoAdListener == null) {
            return
        }
        when (adEvent.type) {
            AdEvent.AdEventType.TAPPED -> mExoAdListener!!.onAdTapped()
            AdEvent.AdEventType.CLICKED -> mExoAdListener!!.onAdClicked()
        }
    }

    companion object {

        val PARAM_AUTO_PLAY = "PARAM_AUTO_PLAY"
        val PARAM_WINDOW = "PARAM_WINDOW"
        val PARAM_POSITION = "PARAM_POSITION"
        val PARAM_IS_AD_WAS_SHOWN = "PARAM_IS_AD_WAS_SHOWN"

        private fun isBehindLiveWindow(e: ExoPlaybackException): Boolean {
            if (e.type != ExoPlaybackException.TYPE_SOURCE) {
                return false
            }
            var cause: Throwable? = e.sourceException
            while (cause != null) {
                if (cause is BehindLiveWindowException) {
                    return true
                }
                cause = cause.cause
            }
            return false
        }
    }

}


 */