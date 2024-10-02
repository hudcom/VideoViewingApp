package com.project.videoviewingapp.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.MediaItem
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.ExoPlayer
import com.project.videoviewingapp.R
import com.project.videoviewingapp.VideoViewingApp
import com.project.videoviewingapp.data.model.VideoData
import com.project.videoviewingapp.databinding.ActivityVideoPlayerBinding
import com.project.videoviewingapp.databinding.CustomPlayerControlsBinding
import com.project.videoviewingapp.ui.viewmodel.MainActivityViewModel
import com.project.videoviewingapp.ui.viewmodel.VideoPlayerViewModel
import com.project.videoviewingapp.ui.viewmodel.ViewModelFactory
import com.project.videoviewingapp.utils.Logger
import javax.inject.Inject

class VideoPlayerActivity @Inject constructor(): AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: ActivityVideoPlayerBinding
    private lateinit var controlBinding: CustomPlayerControlsBinding
    private lateinit var viewModel: VideoPlayerViewModel
    private lateinit var mainViewModel: MainActivityViewModel
    private var player: ExoPlayer? = null

    private lateinit var progressBar: SeekBar
    private lateinit var playPauseButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var fullscreenButton: ImageButton

    private var isPlaying = false
    private var isControlPanelVisible = false
    private var shouldUpdateData = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewBinding()
        initDagger()
        initViewModel()
    }

    /*
    * Initialize methods
    * */
    private fun initializePlayer() {
        player = ExoPlayer.Builder(this).build().also { exoPlayer ->
            binding.playerView.player = exoPlayer
            val mediaItem = MediaItem.fromUri(viewModel.getVideoUri())
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
            isPlaying = true
        }
        startUpdatingSeekBar()
        initControlPanel()
    }
    private fun initControlPanel() {
        progressBar = controlBinding.progressBar
        playPauseButton = controlBinding.playPauseButton
        prevButton = controlBinding.prevButton
        nextButton = controlBinding.nextButton
        fullscreenButton = controlBinding.fullscreenButton

        setOnClickListeners()
    }
    private fun initLiveDataListener() {
        viewModel.videoData.observe(this){
            if (shouldUpdateData)
                displayInfo(it)
        }

        mainViewModel.videos.observe(this){
            viewModel.setVideoList(it)
        }
    }
    private fun displayInfo(video:VideoData) {
        binding.videoTitle.text = video.title
        binding.videoResource.text = video.subtitle
        binding.videoDescription.text = video.description
    }
    private fun getIntentData() {
        val video = intent.getParcelableExtra<VideoData>("videoData")
        if (video != null){
            viewModel.setVideoData(video)
            displayInfo(video)
        }
    }
    private fun initViewModel() {
        viewModel = ViewModelProvider(this,viewModelFactory).get(VideoPlayerViewModel::class.java)
        mainViewModel = ViewModelProvider(this,viewModelFactory).get(MainActivityViewModel::class.java)
    }
    private fun initDagger() {
        (application as VideoViewingApp).appComponent.inject(this)
    }
    private fun initViewBinding() {
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        controlBinding = CustomPlayerControlsBinding.inflate(layoutInflater)
        controlBinding = CustomPlayerControlsBinding.bind(binding.customControls.controlPanel)
    }

    private fun setOnClickListeners() {
        setOnVideoClickListener()
        setOnPlayPauseBtnClickListener()
        setOnSeekBarChangeListener()
        setOnPreviousBtnClickListener(viewModel.getCurrentVideo())
        setOnNextBtnClickListener(viewModel.getCurrentVideo())
    }

    /*
    * SeekBar methods
    * */
    private val handler = Handler(Looper.getMainLooper())
    private val updateSeekBarRunnable = object : Runnable {
        override fun run() {
            player?.let { exoPlayer ->
                // Оновлюємо прогрес
                val currentPosition = exoPlayer.currentPosition
                val duration = exoPlayer.duration

                // Встановлюємо прогрес у SeekBar
                if (duration > 0) {
                    val progress = (currentPosition * 100 / duration).toInt()
                    progressBar.progress = progress
                }
            }
            handler.postDelayed(this, 1000)
        }
    }
    private fun startUpdatingSeekBar() {
        handler.post(updateSeekBarRunnable)
    }
    private fun stopUpdatingSeekBar() {
        handler.removeCallbacks(updateSeekBarRunnable)
    }
    private fun setOnSeekBarChangeListener() {
        progressBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val duration = player?.duration ?: 0
                    // Обчислюємо нову позицію
                    val newPosition = (duration * progress) / 100
                    player?.seekTo(newPosition)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Можна зупинити оновлення SeekBar, поки користувач його переміщує
                stopUpdatingSeekBar()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Повертаємося до оновлення SeekBar після переміщення
                startUpdatingSeekBar()
            }
        })
    }

    /*
    * Control panel methods
    * */
    private fun setOnVideoClickListener() {
        binding.playerView.setOnClickListener {
            if (isControlPanelVisible) {
                hideControlPanel()
            } else {
                showControlPanel()
            }
        }
    }
    private fun showControlPanel() {
        controlBinding.controlPanel.visibility = View.VISIBLE
        isControlPanelVisible = true
        controlBinding.controlPanel.animate().alpha(1f).setDuration(300).start()
    }
    private fun hideControlPanel() {
        controlBinding.controlPanel.visibility = View.GONE
        isControlPanelVisible = false
        controlBinding.controlPanel.animate().alpha(0f).setDuration(300).start()
    }
    private fun setOnPlayPauseBtnClickListener() {
        playPauseButton.setOnClickListener {
            Logger.d("Click Listener isPlaying: $isPlaying")
            if (isPlaying){
                pauseVideo()
            } else
                playVideo()
        }
    }
    private fun playVideo() {
        Logger.d("Pressed Play")
        player?.play()
        playPauseButton.setImageResource(R.drawable.ic_pause_button)
        isPlaying = true
    }
    private fun pauseVideo() {
        Logger.d("Pressed Pause")
        player?.pause()
        playPauseButton.setImageResource(R.drawable.ic_play_button)
        isPlaying = false
    }

    /*
    * Prev/next button methods
    * */
    private fun setOnNextBtnClickListener(video: VideoData?) {
        nextButton.setOnClickListener {
            if(video != null){
                val nextVideo = viewModel.getNextVideo(video)
                if (nextVideo != null) {
                    navigateToVideoPlayer(nextVideo)
                }
            }
        }
    }
    private fun setOnPreviousBtnClickListener(video: VideoData?) {
        prevButton.setOnClickListener {
            if (video != null){
                val prevVideo = viewModel.getPreviousVideo(video)
                if (prevVideo != null) {
                    navigateToVideoPlayer(prevVideo)
                }
            }
        }
    }
    private fun navigateToVideoPlayer(videoData: VideoData) {
        val intent = Intent(this, VideoPlayerActivity::class.java).apply {
            putExtra("videoData", videoData)
        }
        startActivity(intent)
        finish()
    }

    /*
    * Lifecycle methods
    * */
    @androidx.media3.common.util.UnstableApi
    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            player?.playWhenReady = true
        }
    }
    @androidx.media3.common.util.UnstableApi
    override fun onResume() {
        super.onResume()
        shouldUpdateData = false
        isPlaying = true
        getIntentData()
        initLiveDataListener()
        initializePlayer()

        // Start player after start activity
        if (Util.SDK_INT <= 23 || player == null) {
            player?.playWhenReady = true
        }

        startUpdatingSeekBar()

    }
    @androidx.media3.common.util.UnstableApi
    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            player?.release()
        }
        isPlaying = false
        stopUpdatingSeekBar()
    }
    @androidx.media3.common.util.UnstableApi
    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            player?.release()
        }
        shouldUpdateData = true
    }
}