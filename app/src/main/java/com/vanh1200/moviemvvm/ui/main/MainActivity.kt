package com.vanh1200.moviemvvm.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.vanh1200.moviemvvm.R
import com.vanh1200.moviemvvm.utils.BaseUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getMovies()
    }

    private fun getMovies() {
        if (BaseUtils.isNetworkConnected(this)) {
            Log.d(TAG, "getMovies from remote ")
            mainViewModel.getMoviesFromRemote()
        } else {
            Log.d(TAG, "getMovies from local ")
            mainViewModel.getMoviesFromLocal()
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}