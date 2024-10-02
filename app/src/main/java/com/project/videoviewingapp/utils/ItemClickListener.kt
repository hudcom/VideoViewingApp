package com.project.videoviewingapp.utils

import com.project.videoviewingapp.data.model.VideoData
import com.project.videoviewingapp.ui.viewmodel.MainActivityViewModel
import javax.inject.Inject

class ItemClickListener @Inject constructor (): OnItemClickListener {
    private lateinit var viewModel: MainActivityViewModel
    override fun onClickListener(video: VideoData) {
        viewModel.onItemClicked(video)
    }

    fun setViewModel(viewModel: MainActivityViewModel) {
        this.viewModel = viewModel
    }
}