package com.pendo.movie_browser.server.workers

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.pendo.movie_browser.MoviesApp
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MovieServerWorker(context: Context, workerParams: WorkerParameters) : Worker(
    context,
    workerParams
) {
    private val okHttpClient: OkHttpClient by lazy {
        val okHttpBuilder = OkHttpClient.Builder()
        return@lazy okHttpBuilder.build()
    }
    private val app: MoviesApp by lazy { MoviesApp.instance }
    private val gson by lazy { Gson() }

    private val retroFit: Retrofit by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.themoviedb.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun doWork(): Result {
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/discover/movie?api_key=0548999184d4bddfc532bbe17525b66c")
            .get()
            .build()
        val retrofitCreate = retroFit.create(TMDbServer::class.java)
        try {
            val response = retrofitCreate.getFullMovieData(
                "https://api.themoviedb.org/3/movie/${
                    inputData.getString("movie_id")
                }?api_key=0548999184d4bddfc532bbe17525b66c"
            ).execute()
            val responseBody = response.body() ?: return Result.failure()
            return Result.success(
                Data.Builder().putString("fullMovie", gson.toJson(responseBody)).build()
            )
        } catch (e: Exception) {
            return Result.retry()
        }
    }
}