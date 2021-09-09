package com.pendo.movie_browser.server.workers

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.work.Worker
import androidx.work.WorkerParameters
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MovieServerWorker(context: Context, workerParams: WorkerParameters) : Worker(
    context,
    workerParams
) {
    private  val okHttpClient: OkHttpClient by lazy {
        val okHttpBuilder = OkHttpClient.Builder()
        return@lazy okHttpBuilder.build()
    }

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
        try{
            val response = retrofitCreate.getMovieData(inputData.getString("movieID")!!).execute()
            val responseBody = response.body() ?: return  Result.failure()
                // TODO : responseBody is a list of movieData- needs to be added to the recycler view
            }
        catch (e: Exception){

        }

        val call = okHttpClient.newCall(request)
        val execute = call.execute()
        return  Result.success()
    }
}