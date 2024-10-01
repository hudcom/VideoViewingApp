package com.project.videoviewingapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.videoviewingapp.VideoViewingApp
import com.project.videoviewingapp.databinding.ActivityMainBinding
import com.project.videoviewingapp.ui.viewmodel.MainActivityViewModel
import com.project.videoviewingapp.ui.viewmodel.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var videoAdapter: VideoAdapter

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        initDagger()
        initViewModel()
        initRecyclerView()

        if(savedInstanceState == null){
            viewModel.fetchVideo()
        }
    }

    // Налаштування RecyclerView
    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = videoAdapter
        }
    }

    // Ініціалізація ViewModel
    private fun initViewModel() {
        viewModel = ViewModelProvider(this,viewModelFactory).get(MainActivityViewModel::class.java)
        initLiveDataListener()
    }

    private fun initLiveDataListener() {
        viewModel.videos.observe(this){
            videoAdapter.updateVideoList(it)
        }
    }

    // Ініціалізація Dagger
    private fun initDagger() {
        (application as VideoViewingApp).appComponent.inject(this)
    }

    // Ініціалізація View Binding
    private fun initViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}