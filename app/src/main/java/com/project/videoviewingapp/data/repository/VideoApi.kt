package com.project.videoviewingapp.data.repository

import com.project.videoviewingapp.data.model.VideoData
import retrofit2.http.GET

interface VideoApi {
    @GET("3b19447b304616f18657/raw/a8c1f60074542d28fa8da4fe58c3788610803a65/gistfile1.txt")
    suspend fun getVideos(): List<VideoData>
}