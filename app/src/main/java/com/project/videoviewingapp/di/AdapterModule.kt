package com.project.videoviewingapp.di

import com.project.videoviewingapp.ui.view.VideoAdapter
import dagger.Module
import dagger.Provides

@Module
class AdapterModule {
    @Provides
    fun provideVideoAdapter(): VideoAdapter {
        return VideoAdapter(emptyList())
    }
}