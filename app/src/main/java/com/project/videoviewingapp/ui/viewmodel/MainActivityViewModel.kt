package com.project.videoviewingapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.project.videoviewingapp.data.model.MediaResponse
import com.project.videoviewingapp.data.model.VideoData
import com.project.videoviewingapp.data.repository.VideoApi
import com.project.videoviewingapp.utils.Logger
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val videoApi: VideoApi
): ViewModel() {

    private val _videos = MutableLiveData<List<VideoData>>()
    val videos: LiveData<List<VideoData>>
        get() = _videos

    private val _navigateToVideoPlayer = MutableLiveData<VideoData>()
    val navigateToVideoPlayer: LiveData<VideoData>
        get() = _navigateToVideoPlayer

    fun onItemClicked(video: VideoData){
        _navigateToVideoPlayer.value = video
    }

    fun fetchVideo(){
        viewModelScope.launch {
            try {
                val response = videoApi.getVideos()
                if(response.isSuccessful){
                    _videos.value = convertToJson(response).categories.flatMap { it.videos }
                    Logger.d("Successfully retrieving video from API")
                } else {
                    Logger.e("Error: ${response.code()} ${response.message()}")
                }
            } catch(e: Exception){
                Logger.e("Coroutine can`t get video from API")
                Logger.e("Error: $e")
            }
        }
    }

    private fun convertToJson(response: Response<ResponseBody>): MediaResponse {
        return Gson().fromJson(
            clearString(response.body()?.string() ?: ""),
            MediaResponse::class.java)
    }

    private fun clearString(str: String): String {
        return str
            .replace("var mediaJSON =", "")
            .replace(";", "")
            .trim()
    }
}