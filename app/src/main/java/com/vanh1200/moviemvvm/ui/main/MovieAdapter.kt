package com.vanh1200.moviemvvm.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.vanh1200.moviemvvm.R
import com.vanh1200.moviemvvm.base.setImage
import com.vanh1200.moviemvvm.data.model.Movie
import com.vanh1200.moviemvvm.databinding.ItemHorizontalMovieBinding

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private var movies: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemHorizontalMovieBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_horizontal_movie,
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(movies[position])
    }

    fun setData(movies: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemHorizontalMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(movie: Movie) {
            binding.textTitle.text = movie.title
            movie.getFullBackdropPath()?.let { binding.imagePoster.setImage(it) }
            binding.textOverview.text = movie.overview
            binding.textReleaseDate.text = movie.release_date
            binding.ratingBar.rating = movie.vote_average?.toFloat()!!
        }
    }

}