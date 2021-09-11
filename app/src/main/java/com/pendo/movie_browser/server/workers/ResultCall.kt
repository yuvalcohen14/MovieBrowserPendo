package com.pendo.movie_browser.server.workers

data class ResultCall(
    var results: List<MovieData> = mutableListOf()
) {
    constructor(strArray: Array<String>) : this() {
        for (str in strArray) {
            val md = MovieData(str)
            if (md.id.isNotEmpty()) {
                results += MovieData(str)
            }
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
