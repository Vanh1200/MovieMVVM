package com.vanh1200.moviemvvm.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.gson.Gson
import com.vanh1200.moviemvvm.data.local.db.AppDatabase
import com.vanh1200.moviemvvm.data.repository.main.MovieRepository
import com.vanh1200.moviemvvm.data.repository.main.MovieRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    // database
    single { createAppDatabase(get()) }
    single { createMovieDao(get()) }


    single { Gson() }

    // repository
    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
}

fun createSharedPrefs(context: Context): SharedPreferences =
    context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

fun createAppDatabase(context: Context) =
    Room.databaseBuilder(context, AppDatabase::class.java, "MovieDB").build()

fun createMovieDao(appDatabase: AppDatabase) = appDatabase.movieDao()
