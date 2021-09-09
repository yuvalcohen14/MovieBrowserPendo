package com.pendo.movie_browser.server.workers

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query

interface TMDbServer {
    @GET("/3/discover/movie?api_key=0548999184d4bddfc532bbe17525b66c")
    fun getMoviesData(): Call<List<MovieData>>

    @GET("/3/movie/")
    fun getMovieData(
        @Query("id") id: String,
        @Query("api_key") api: String = "api_key=0548999184d4bddfc532bbe17525b66c"
    ): Call<MovieFullData>
}