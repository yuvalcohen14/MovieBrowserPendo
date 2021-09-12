package com.pendo.movie_browser.server.workers

import com.pendo.movie_browser.MoviesApp

data class ResultCall(
    var results: List<MovieData> = mutableListOf()
) {
    constructor(strArray: Array<String>) : this() {
        for (str in strArray) {
            results += MoviesApp.instance.gson.fromJson(str, MovieData::class.java)
        }
    }

    fun toStringArray(): Array<String> {
        var arr = arrayOf<String>()
        for (movie in results) {
            arr += movie.toString()
        }
        return arr
    }
}
