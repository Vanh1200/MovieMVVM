package com.vanh1200.moviemvvm.ui.main

import com.vanh1200.moviemvvm.data.model.Movie

sealed class MainViewState {
    object ShowLoading: MainViewState()
    object HideLoading: MainViewState()
    data class GetMoviesSuccess(val movies: List<Movie>): MainViewState()
    data class GetMovieError(val errorMess: String): MainViewState()
}


