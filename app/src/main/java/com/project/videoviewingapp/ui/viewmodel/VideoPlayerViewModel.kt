package com.project.videoviewingapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.videoviewingapp.data.model.VideoData
import javax.inject.Inject

class VideoPlayerViewModel @Inject constructor(): ViewModel() {

    private val _videoData = MutableLiveData<VideoData>()
    val videoData: LiveData<VideoData>
        get() = _videoData

    fun setVideoData(video: VideoData){
        _videoData.value = video
    }
}