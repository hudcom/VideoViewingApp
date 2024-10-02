package com.project.videoviewingapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.videoviewingapp.data.model.VideoData
import com.project.videoviewingapp.utils.Logger
import javax.inject.Inject

class VideoPlayerViewModel @Inject constructor(): ViewModel() {
    private val _videoData = MutableLiveData<VideoData>()
    private lateinit var videoList: List<VideoData>
    val videoData: LiveData<VideoData>
        get() = _videoData

    fun setVideoData(video: VideoData){
        _videoData.value = video
    }

    fun setVideoList(list: List<VideoData>){
        videoList = list
    }

    fun getNextVideo(currentVideo: VideoData): VideoData? {
        val currentIndex = videoList.indexOf(currentVideo)
        Logger.d("Next: Current index: $currentIndex")
        return if (currentIndex < videoList.size - 1) videoList[currentIndex + 1] else null
    }

    fun getPreviousVideo(currentVideo: VideoData): VideoData? {
        val currentIndex = videoList.indexOf(currentVideo)
        Logger.d("Previous:Current index: $currentIndex")
        return if (currentIndex > 0) videoList[currentIndex - 1] else null
    }

    fun getCurrentVideo(): VideoData? = videoData.value

    fun getVideoUri(): String = videoData.value?.sources?.get(0) ?: ""
}