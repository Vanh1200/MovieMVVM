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

    val mainViewState = MutableLiveData<MainViewState>()
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun getMoviesFromRemote() {
        viewModelScope.launch() {
            try {
                mainViewState.value = MainViewState.ShowLoading
                mainViewState.value = movieRepository.getMovieFromRemote().results?.let {
                    MainViewState.GetMoviesSuccess(it)
                }
            } catch (e: Exception) {
                mainViewState.value = MainViewState.GetMovieError(e.toString())
                e.printStackTrace()
            } finally {
                mainViewState.value = MainViewState.HideLoading
            }
        }
    }

    fun getMoviesFromRemoteRx() {
        val disposable = movieRepository
            .getMovieFromRemoteRx()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .filter { it.results != null && it.results!!.size > 0 }
            .map {
                for (item in it.results!!) {
                    item.title = item.title?.toUpperCase()
                }
                it
            }
            .doOnSubscribe { mainViewState.value = MainViewState.ShowLoading }
            .doAfterTerminate { mainViewState.value = MainViewState.HideLoading }
            .subscribe(
                {
                    mainViewState.value = it.results?.let { MainViewState.GetMoviesSuccess(it) }
                },
                { mainViewState.value = MainViewState.GetMovieError(it.toString()) })

        compositeDisposable.add(disposable)
    }

    fun getMoviesFromLocal() {
        viewModelScope.launch {
            val movies = movieRepository.getMovieFromLocal()
            movies?.let { mainViewState.value = MainViewState.GetMoviesSuccess(it) }
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