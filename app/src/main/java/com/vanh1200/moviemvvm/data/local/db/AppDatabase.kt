package com.vanh1200.moviemvvm.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vanh1200.moviemvvm.data.local.dao.MovieDao
import com.vanh1200.moviemvvm.data.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}