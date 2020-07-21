package com.vanh1200.moviemvvm.data.remote.api

import com.vanh1200.moviemvvm.data.remote.response.GetMovieListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MovieApi {
    @GET("3/discover/movie")
    suspend fun getMovies(@QueryMap hashMap: HashMap<String, String> = HashMap()):
            GetMovieListResponse

    @GET("3/discover/movie")
    fun getMoviesRx(@QueryMap hashMap: HashMap<String, String> = HashMap()):
            Single<GetMovieListResponse>
}