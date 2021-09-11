package com.pendo.movie_browser.ui.movie_items

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pendo.movie_browser.MoviesApp
import com.pendo.movie_browser.R
import com.pendo.movie_browser.server.workers.MovieData
import com.pendo.movie_browser.view_models.MoviesViewModel


class MoviesAdapter : RecyclerView.Adapter<MovieItemHolder>() {
    private lateinit var context: Context
    private val moviesApp by lazy { MoviesApp.instance }

    private val _movies: MutableList<MovieData> = ArrayList()
    private lateinit var moviesViewModel: MoviesViewModel

    fun setViewModel(vm: MoviesViewModel) {
        moviesViewModel = vm
    }

    fun setItems(items: List<MovieData>) {
        _movies.clear()
        items.forEach { _movies.add(it) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieItemHolder(view)
    }


    override fun onBindViewHolder(holder: MovieItemHolder, position: Int) {
        if (position == _movies.size) {
            holder.movieTitle.text = ""
            holder.movieDescription.text = "<< Previous Page"
            holder.movieImg.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_baseline_navigate_before_24
                )
            )
            if (moviesApp.currentMoviePage > 1) {
                holder.movieImg.visibility = View.VISIBLE
                holder.movieImg.setColorFilter(
                    ContextCompat.getColor(context, R.color.teal),
                    android.graphics.PorterDuff.Mode.SRC_IN
                );
                holder.movieDescription.visibility = View.VISIBLE
                holder.movieDescription.gravity = Gravity.CENTER_HORIZONTAL
                holder.bg.setOnClickListener {
                    moviesApp.currentMoviePage -= 1
                    moviesApp.reloadMoviesList()
                    Navigation.findNavController(it)
                        .navigate(R.id.action_FirstFragment_to_launcherFragment)
                }
            } else {
                holder.movieImg.visibility = View.GONE
                holder.movieDescription.visibility = View.GONE
            }
        } else if (position == _movies.size + 1) {
            holder.movieTitle.text = ""
            holder.movieDescription.text = " Next Page >>"
            holder.movieImg.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_baseline_navigate_next_24
                )
            )
            if (moviesApp.currentMoviePage < 500) {
                holder.movieImg.visibility = View.VISIBLE
                holder.movieDescription.visibility = View.VISIBLE
                holder.movieDescription.gravity = Gravity.CENTER_HORIZONTAL
                holder.movieImg.setColorFilter(
                    ContextCompat.getColor(context, R.color.teal),
                    android.graphics.PorterDuff.Mode.SRC_IN
                );
                holder.bg.setOnClickListener {
                    moviesApp.currentMoviePage += 1
                    moviesApp.reloadMoviesList()
                    Navigation.findNavController(it)
                        .navigate(R.id.action_FirstFragment_to_launcherFragment)
                }
            } else {
                holder.movieImg.visibility = View.GONE
                holder.movieDescription.visibility = View.GONE
            }
        } else {
            val movie = _movies[position]
            holder.movieTitle.text = movie.title
            if (movie.overview.length > 100) {
                (movie.overview.substring(0, 97) + "...").also { holder.movieDescription.text = it }
            } else {
                holder.movieDescription.text = movie.overview
            }
            holder.movieImg.clearColorFilter()
            Glide.with(context).load(moviesApp.imgStartURL + movie.poster_path)
                .into(holder.movieImg)
            holder.bg.setOnClickListener {
                moviesApp.loadFullMovie(movie.id)
                Navigation.findNavController (it)
                    .navigate(R.id.action_FirstFragment_to_launcherFragment)
            }
        }
    }

    override fun getItemCount(): Int {
        return _movies.size + 2
    }

}