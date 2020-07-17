package com.vanh1200.moviemvvm.data.repository.main

import com.vanh1200.moviemvvm.data.local.dao.MovieDao
import com.vanh1200.moviemvvm.data.model.Movie
import com.vanh1200.moviemvvm.data.remote.api.MovieApi
import com.vanh1200.moviemvvm.data.remote.response.GetMovieListResponse

class MovieRepositoryImpl(private val movieApi: MovieApi, private val movieDao: MovieDao) :
    MovieRepository {
    override suspend fun getMovieFromRemote(): GetMovieListResponse {
        return movieApi.getMovies()
    }

    override suspend fun getMovieFromLocal(): List<Movie>? {
        return movieDao.getMovieList()
    }

    override suspend fun insertMoviesToLocal(movies: List<Movie>) {
        return movieDao.insert(movies)
    }
}