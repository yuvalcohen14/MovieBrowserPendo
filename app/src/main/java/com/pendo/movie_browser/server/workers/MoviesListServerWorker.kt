package com.pendo.movie_browser.server.workers

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.pendo.movie_browser.MoviesApp
import com.pendo.movie_browser.model.LoadingStage
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MoviesListServerWorker(context: Context, workerParams: WorkerParameters) : Worker(
    context,
    workerParams
) {
    private val okHttpClient: OkHttpClient by lazy {
        val okHttpBuilder = OkHttpClient.Builder()
        return@lazy okHttpBuilder.build()
    }
    private val app: MoviesApp by lazy {
        MoviesApp.instance
    }

    private val retroFit: Retrofit by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.themoviedb.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun doWork(): Result {

        val retrofitCreate = retroFit.create(TMDbServer::class.java)
        try {

            val response = retrofitCreate.getMoviesData(app.currentMoviePage).execute()
            val responseBody = response.body() ?: return Result.failure();
            return Result.success(
                Data.Builder()
                    .putStringArray("moviesList", responseBody.toStringArray())
                    .build())
        } catch (e: Exception) {
            Log.d("yuval exception", e.toString())

            return Result.retry()
        }
    }
}