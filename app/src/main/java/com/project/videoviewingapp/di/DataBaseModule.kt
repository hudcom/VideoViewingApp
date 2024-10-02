package com.project.videoviewingapp.di

import com.project.videoviewingapp.VideoViewingApp
import com.project.videoviewingapp.data.repository.AppDatabase
import com.project.videoviewingapp.data.repository.VideoDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(application: VideoViewingApp): AppDatabase {
        return AppDatabase.getDatabase(application)
    }

    @Provides
    @Singleton
    fun provideVideoDao(appDatabase: AppDatabase): VideoDao {
        return appDatabase.videoDao()
    }
}