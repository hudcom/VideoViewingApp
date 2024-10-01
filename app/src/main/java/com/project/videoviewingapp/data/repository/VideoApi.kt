package com.project.videoviewingapp.data.repository


import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface VideoApi {
    @GET("jsturgis/3b19447b304616f18657/raw/a8c1f60074542d28fa8da4fe58c3788610803a65/gistfile1.txt")
    suspend fun getVideos(): Response<ResponseBody>
}