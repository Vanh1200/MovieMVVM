package com.vanh1200.moviemvvm.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.vanh1200.moviemvvm.R
import com.vanh1200.moviemvvm.data.model.Movie
import com.vanh1200.moviemvvm.databinding.ActivityMainBinding
import com.vanh1200.moviemvvm.utils.BaseUtils
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var movieAdapter: MovieAdapter
    private var tempMovies = mutableListOf<Movie>()
    private var isUseRx = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initViews()
        initAdapters()
        initListeners()
        getMovies()
        observeData()
    }

    private fun initViews() {
        binding.toolbar.title = resources.getString(R.string.app_name)
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.color_black))
    }

    private fun observeData() {
        mainViewModel.apply {
            mainViewState.observe(this@MainActivity, Observer {
                when (it) {
                    is MainViewState.GetMoviesSuccess -> {
                        if (it.movies.isNotEmpty()) {
                            tempMovies.clear()
                            tempMovies.addAll(it.movies)
                        }
                        movieAdapter.setData(it.movies)
                    }

                    is MainViewState.GetMovieError -> {
                        Toast.makeText(this@MainActivity, it.errorMess,
                            Toast.LENGTH_SHORT).show()
                    }

                    is MainViewState.ShowLoading -> {
                        binding.swipeRefresh.isRefreshing = true
                    }

                    is MainViewState.HideLoading -> {
                        binding.swipeRefresh.isRefreshing = false
                    }
                }
            })
        }
    }

    private fun initListeners() {
        binding.apply {
            swipeRefresh.setOnRefreshListener {
                getMovies()
            }
            fabCacheMovie.setOnClickListener {
                saveMoviesToLocal()
            }
        }

    }

    private fun saveMoviesToLocal() {
        Toast.makeText(this, "Saved movie to local: ${tempMovies.size}", Toast.LENGTH_SHORT).show()
        mainViewModel.insertMoviesToLocal(tempMovies)
    }

    private fun initAdapters() {
        movieAdapter = MovieAdapter()
        binding.recyclerMovie.adapter = movieAdapter
    }

    private fun getMovies() {
        if (BaseUtils.isNetworkConnected(this)) {
            Toast.makeText(this, "getMovies from internet", Toast.LENGTH_SHORT).show()
            if (isUseRx) {
                mainViewModel.getMoviesFromRemoteRx()
            } else {
                mainViewModel.getMoviesFromRemote()
            }
        } else {
            Toast.makeText(this, "getMovies from local", Toast.LENGTH_SHORT).show()
            mainViewModel.getMoviesFromLocal()
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}