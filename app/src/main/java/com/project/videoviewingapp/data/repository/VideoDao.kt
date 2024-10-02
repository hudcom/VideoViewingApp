package com.project.videoviewingapp.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.videoviewingapp.data.model.VideoData

@Dao
interface VideoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertVideos(videos: List<VideoData>)

    @Query("SELECT * FROM videos")
    suspend fun getAllVideos(): List<VideoData>
}