package com.project.videoviewingapp.di

import com.project.videoviewingapp.data.repository.VideoApi
import com.project.videoviewingapp.ui.viewmodel.ViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {
    @Provides
    fun provideViewModelFactory(videoApi: VideoApi): ViewModelFactory {
        return ViewModelFactory(videoApi)
    }
}