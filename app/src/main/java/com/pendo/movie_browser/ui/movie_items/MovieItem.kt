package com.pendo.movie_browser.ui.movie_items

data class MovieItem(
    val title: String,
    val imageRoot: String,
    val description: String,
    val releaseDate: String,
    val movieRate : String
) : Comparable<MovieItem> {
    override fun compareTo(other: MovieItem): Int {
        return movieRate.toFloat().compareTo(other.movieRate.toFloat())
    }
}
