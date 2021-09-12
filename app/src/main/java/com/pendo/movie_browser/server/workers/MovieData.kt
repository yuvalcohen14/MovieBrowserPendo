package com.pendo.movie_browser.server.workers

import android.util.Log
import com.google.gson.Gson
import com.pendo.movie_browser.MoviesApp
import java.io.Serializable

data class MovieData(
    var overview: String = "description",
    var title: String = "title",
    var poster_path: String = "None",
    var id: String = ""
) : Serializable {

    override fun toString(): String {
        return MoviesApp.instance.gson.toJson(this)
    }
}
