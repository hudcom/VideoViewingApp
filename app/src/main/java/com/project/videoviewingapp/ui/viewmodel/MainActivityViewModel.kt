package com.project.videoviewingapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.videoviewingapp.data.model.VideoData
import com.project.videoviewingapp.data.repository.VideoApi
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val videoApi: VideoApi
): ViewModel() {

    private val _videos = MutableLiveData<List<VideoData>>()
    val videos: LiveData<List<VideoData>>
        get() = _videos


    fun fetchVideo(){
        // TODO
    }
}