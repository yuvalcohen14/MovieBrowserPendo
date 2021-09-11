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
        Log.d("yuval", "call start do worke")

        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/discover/movie?api_key=0548999184d4bddfc532bbe17525b66c")
            .get()
            .build()
        val retrofitCreate = retroFit.create(TMDbServer::class.java)
        try {
            Log.d("app.currentMoviePage", app.currentMoviePage.toString())

            val response = retrofitCreate.getMoviesData(app.currentMoviePage).execute()
            val responseBody = response.body() ?: return Result.failure();
//            app.setMovies(responseBody.results)
            return Result.success(
                Data.Builder()
                    .putStringArray("moviesList", responseBody.toStringArray())
                    .build())
            // TODO : responseBody is a list of movieData- needs to be added to the recycler view
        } catch (e: Exception) {
            return Result.retry()
        }

//        val call = okHttpClient.newCall(request)
//        val execute = call.execute()
//        return Result.success()
    }
}