package com.pendo.movie_browser.server.workers

import android.util.Log
import java.io.Serializable

data class MovieData(
    var overview: String = "description",
    var title: String = "title",
    var poster_path: String = "None",
    var id: String = ""
) : Serializable {
    private val regex by lazy { Regex("overview: (.*), title: (.*), poster_path: (.*), id: (\\d*)") }

    constructor(string: String) : this() {
        val match = regex.find(string)
        if (match != null) {
            val (overview, title, poster_path, id) = match.destructured
            this.overview = overview
            this.title = title
            this.poster_path = poster_path
            this.id = id
        }
    }

    override fun toString(): String {
        return "overview: ${overview.replace("...", ".")}, title: ${
            title.replace(
                "...",
                "."
            )
        }, poster_path: ${poster_path}, id: $id"
    }
}
