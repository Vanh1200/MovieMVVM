package com.vanh1200.moviemvvm.data.repository.main

import com.vanh1200.moviemvvm.data.model.Movie
import com.vanh1200.moviemvvm.data.remote.response.GetMovieListResponse

interface MovieRepository {
    suspend fun getMovieFromRemote() : GetMovieListResponse

    suspend fun getMovieFromLocal() : List<Movie>?

    suspend fun insertMoviesToLocal(movies: List<Movie>)
}