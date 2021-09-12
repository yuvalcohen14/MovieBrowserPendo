package com.pendo.movie_browser.ui.movie_category_items

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pendo.movie_browser.MoviesApp
import com.pendo.movie_browser.R
import com.pendo.movie_browser.ui.movie_items.MovieItemHolder
import com.pendo.movie_browser.ui.movie_items.MoviesAdapter


class MovieCategoryAdapter : RecyclerView.Adapter<MovieCategoryHolder>() {
    private lateinit var context: Context
    private val moviesApp by lazy { MoviesApp.instance }

    private val _movies_category: MutableList<MoviesCategory> = ArrayList()

    fun setItems(items: List<MoviesCategory>) {
        _movies_category.clear()
        items.forEach { _movies_category.add(it) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCategoryHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movies_recycler, parent, false)
        return MovieCategoryHolder(view)
    }


    override fun onBindViewHolder(holder: MovieCategoryHolder, position: Int) {

        val category = _movies_category[position]
        holder.movieCategory.text = category.title

        val recyclerView = holder.movieRecycler
        val adapter by lazy { return@lazy MoviesAdapter() }

        adapter.setItems(category.movies, category.showRank)
        recyclerView.layoutManager =
            GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = adapter
    }

    override fun getItemCount(): Int {
        return _movies_category.size
    }

}