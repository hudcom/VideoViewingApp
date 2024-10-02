package com.project.videoviewingapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.videoviewingapp.data.repository.VideoApi
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val videoApi: VideoApi
): ViewModelProvider.Factory {
    // Словник для зберігання екземплярів ViewModel
    private val viewModels = mutableMapOf<Class<*>, ViewModel>()

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Перевірте, чи вже є екземпляр у словнику
        @Suppress("UNCHECKED_CAST")
        return viewModels[modelClass] as? T ?: createNewViewModel(modelClass)
    }

    private fun <T : ViewModel> createNewViewModel(modelClass: Class<T>): T {
        val viewModel = when {
            modelClass.isAssignableFrom(MainActivityViewModel::class.java) -> {
                MainActivityViewModel(videoApi)
            }
            modelClass.isAssignableFrom(VideoPlayerViewModel::class.java) -> {
                VideoPlayerViewModel()
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }

        // Зберігайте новий екземпляр у словнику
        viewModels[modelClass] = viewModel
        return viewModel as T
    }
}