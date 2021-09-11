package com.pendo.movie_browser.server.workers

data class MovieFullData(
    var overview: String = "description",
    var title: String = "title",
    var release_date: String = "",
    var original_language: String = "en",
    var popularity: Float = 0.0f,
    var poster_path: String = "None",
    var vote_average: String,
    var id: String = ""
)
