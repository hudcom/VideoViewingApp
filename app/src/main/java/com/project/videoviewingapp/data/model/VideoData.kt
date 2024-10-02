package com.project.videoviewingapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoData(
    val title: String,
    val subtitle: String,
    val description: String,
    val thumb: String,
    val sources: List<String>
) : Parcelable
