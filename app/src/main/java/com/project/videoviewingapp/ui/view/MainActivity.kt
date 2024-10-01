package com.project.videoviewingapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.project.videoviewingapp.R
import com.project.videoviewingapp.VideoViewingApp
import com.project.videoviewingapp.ui.viewmodel.MainActivityViewModel
import com.project.videoviewingapp.ui.viewmodel.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ініціалізація Dagger
        (application as VideoViewingApp).appComponent.inject(this)

        viewModel = ViewModelProvider(this,viewModelFactory).get(MainActivityViewModel::class.java)

        viewModel.videos.observe(this){
            Toast.makeText(this,"LiveData changed",Toast.LENGTH_LONG).show()
        }

        viewModel.fetchVideo()
    }
}