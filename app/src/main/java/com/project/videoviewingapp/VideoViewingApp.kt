package com.project.videoviewingapp

import android.app.Application
import com.project.videoviewingapp.di.AppComponent
import com.project.videoviewingapp.di.DaggerAppComponent

class VideoViewingApp: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDaggerComponent()
    }
    private fun initDaggerComponent(){
        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()
    }
}