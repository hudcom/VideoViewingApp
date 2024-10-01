package com.project.videoviewingapp.utils

import android.util.Log

object Logger {
    private const val TAG = "Project"

    fun e(message:String){
        Log.d(TAG,message)
    }

    fun d(message: String){
        Log.d(TAG,message)
    }
}