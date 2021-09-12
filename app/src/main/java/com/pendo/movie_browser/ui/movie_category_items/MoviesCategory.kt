package com.pendo.movie_browser.ui.movie_category_items

import com.pendo.movie_browser.server.workers.MovieData

data class MoviesCategory(val title: String, var movies: List<MovieData>, val showRank: Boolean)
