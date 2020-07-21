package com.vanh1200.moviemvvm.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanh1200.moviemvvm.data.model.Movie
import com.vanh1200.moviemvvm.data.remote.const.ApiParams
import com.vanh1200.moviemvvm.data.repository.main.MovieRepository
import kotlinx.coroutines.launch

class MainViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    val liveMovies = MutableLiveData<List<Movie>>()

    fun getMoviesFromRemote() {
        viewModelScope.launch() {
            try {
                val hashMap = HashMap<String, String>()
                hashMap[ApiParams.PAGE] = "1"
                hashMap[ApiParams.SORT_BY] = ApiParams.POPULARITY_DESC
                liveMovies.value = movieRepository.getMovieFromRemote().results
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getMoviesFromLocal() {
        viewModelScope.launch {
            val movies = movieRepository.getMovieFromLocal()
            movies?.let { liveMovies.value = it }
        }
    }

    fun insertMoviesToLocal(movies: List<Movie>) {
        viewModelScope.launch {
            movieRepository.insertMoviesToLocal(movies)
        }
    }

}