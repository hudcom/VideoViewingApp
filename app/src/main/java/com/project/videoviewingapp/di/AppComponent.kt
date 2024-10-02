package com.project.videoviewingapp.di

import com.project.videoviewingapp.VideoViewingApp
import com.project.videoviewingapp.ui.view.MainActivity
import com.project.videoviewingapp.ui.view.VideoPlayerActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, ViewModelModule::class, AdapterModule::class, DatabaseModule::class])
@Singleton
interface AppComponent {
    fun inject(mainActivity:MainActivity)
    fun inject(videoPlayerActivity: VideoPlayerActivity)

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: VideoViewingApp): Builder
        fun build(): AppComponent
    }
}