package com.project.videoviewingapp.di

import com.project.videoviewingapp.ui.view.VideoAdapter
import com.project.videoviewingapp.utils.ItemClickListener
import dagger.Module
import dagger.Provides

@Module
class AdapterModule {
    @Provides
    fun provideVideoAdapter(clickListener: ItemClickListener): VideoAdapter {
        return VideoAdapter(emptyList(), clickListener)
    }
}