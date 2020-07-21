package com.vanh1200.moviemvvm.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanh1200.moviemvvm.data.model.Movie
import com.vanh1200.moviemvvm.data.repository.main.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class MainViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    val liveMovies = MutableLiveData<List<Movie>>()
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun getMoviesFromRemote() {
        viewModelScope.launch() {
            try {
                liveMovies.value = movieRepository.getMovieFromRemote().results
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getMoviesFromRemoteRx() {
        val disposable = movieRepository
            .getMovieFromRemoteRx()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response -> liveMovies.value = response.results },
                { e -> e.printStackTrace() })

        compositeDisposable.add(disposable)
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

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

}