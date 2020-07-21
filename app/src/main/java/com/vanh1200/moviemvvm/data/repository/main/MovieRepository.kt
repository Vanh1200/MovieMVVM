package com.vanh1200.moviemvvm.data.repository.main

import com.vanh1200.moviemvvm.data.model.Movie
import com.vanh1200.moviemvvm.data.remote.response.GetMovieListResponse
import io.reactivex.Single

interface MovieRepository {
    suspend fun getMovieFromRemote() : GetMovieListResponse

    suspend fun getMovieFromLocal() : List<Movie>?

    suspend fun insertMoviesToLocal(movies: List<Movie>)

    //for rx
    fun getMovieFromRemoteRx() : Single<GetMovieListResponse>
}