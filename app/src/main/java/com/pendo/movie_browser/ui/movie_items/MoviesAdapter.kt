package com.pendo.movie_browser.ui.movie_items

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkManager
import com.bumptech.glide.Glide
import com.pendo.movie_browser.R
import com.pendo.movie_browser.view_models.MoviesViewModel



class MoviesAdapter : RecyclerView.Adapter<MovieItemHolder>() {
    private lateinit var context: Context

    private val _movies: MutableList<MovieItem> = ArrayList()
    private lateinit var moviesViewModel: MoviesViewModel

    fun setViewModel(vm: MoviesViewModel) {
        moviesViewModel = vm
    }

    fun setItems(items:List<MovieItem>) {
        _movies.clear()
        items.forEach { _movies.add(it) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemHolder {
        context = parent.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieItemHolder(view)
    }

    override fun onBindViewHolder(holder: MovieItemHolder, position: Int) {
        val movie = _movies[position]
        holder.movieTitle.text = movie.title
        holder.movieDescription.text = movie.description;
        Glide.with(context).load("https://image.tmdb.org/t/p/w200"+movie.imageRoot).into(holder.movieImg);
        holder.bg.setOnClickListener {
            // TODO add a workMananger and start a new worker to get all the movie full data
            Navigation.findNavController(it)
                .navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun getItemCount(): Int {
        return _movies.size
    }

}