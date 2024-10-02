package com.project.videoviewingapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.videoviewingapp.data.repository.VideoApi
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val videoApi: VideoApi
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(MainActivityViewModel::class.java) -> {
                MainActivityViewModel(videoApi) as T
            }
            modelClass.isAssignableFrom(VideoPlayerViewModel::class.java) -> {
                VideoPlayerViewModel() as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}