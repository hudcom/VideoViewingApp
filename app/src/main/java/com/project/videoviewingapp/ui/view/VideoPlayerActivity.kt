package com.project.videoviewingapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.project.videoviewingapp.VideoViewingApp
import com.project.videoviewingapp.data.model.VideoData
import com.project.videoviewingapp.databinding.ActivityVideoPlayerBinding
import com.project.videoviewingapp.ui.viewmodel.VideoPlayerViewModel
import com.project.videoviewingapp.ui.viewmodel.ViewModelFactory
import javax.inject.Inject

class VideoPlayerActivity @Inject constructor(): AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: ActivityVideoPlayerBinding
    private lateinit var viewModel: VideoPlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewBinding()
        initDagger()
        initViewModel()
        getIntentData()
        initLiveDataListener()
    }

    private fun initLiveDataListener() {
        viewModel.videoData.observe(this){
            displayInfo(it)
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
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this,viewModelFactory).get(VideoPlayerViewModel::class.java)
    }

    private fun initDagger() {
        (application as VideoViewingApp).appComponent.inject(this)
    }

    private fun initViewBinding() {
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}