package com.pendo.movie_browser.server.workers

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query
import retrofit2.http.Url

interface TMDbServer {
    @GET("/3/discover/movie?api_key=0548999184d4bddfc532bbe17525b66c")
    fun getMoviesData(@Query("page") page: Int): Call<ResultCall>

    @GET
    fun getFullMovieData(@Url url: String): Call<MovieFullData>
}