package com.pendo.movie_browser

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import com.google.gson.Gson
import com.pendo.movie_browser.model.LoadingStage
import com.pendo.movie_browser.server.workers.*
import java.util.*

class MoviesApp : Application() {


    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        val instance: MoviesApp = MoviesApp()

    }

    val gson by lazy { Gson() }
    private val moviesWorkManager = WorkManager.getInstance(this)
    val appName: String by lazy { return@lazy "Movie Browser (Pendo)" }
    val imgStartURL by lazy { return@lazy "https://image.tmdb.org/t/p/original" }
    var currentFragment : String = "launcher"
    var currentMoviePage = 1

    val currentTitle: MutableLiveData<String> = MutableLiveData(appName)
    val currentMovieFullData: MutableLiveData<MovieFullData> = MutableLiveData()

    val moviesListStage: MutableLiveData<LoadingStage> = MutableLiveData(LoadingStage.FAILURE)
    val fullMovieStage: MutableLiveData<LoadingStage> = MutableLiveData(LoadingStage.FAILURE)

    private var moviesList: List<MovieData> = listOf()
    var recentlyWatched = mapOf<String, MovieData>()


    fun getMovies(): List<MovieData> {
        return moviesList
    }

    fun setLoading(stage: LoadingStage, list: Boolean = true): ListenableWorker.Result {
        if (list) {
            moviesListStage.value = stage
        } else fullMovieStage.value = stage
        if (stage == LoadingStage.FAILURE) return ListenableWorker.Result.failure()
        return if (stage == LoadingStage.SUCCESS) ListenableWorker.Result.success()
        else ListenableWorker.Result.retry()
    }

    fun loadFullMovie(movie_id: String) {
        setLoading(LoadingStage.LOADING, false)
        try {
            moviesListStage.value = LoadingStage.LOADING
            val fullMovieServerWorkerRequest =
                OneTimeWorkRequest.Builder(MovieServerWorker::class.java)
                    .setConstraints(
                        Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
                    ).setInputData(Data.Builder().putString("movie_id", movie_id).build()).build()
            moviesWorkManager.enqueue(fullMovieServerWorkerRequest)
            val workInfoByIdLiveData =
                moviesWorkManager.getWorkInfoByIdLiveData(fullMovieServerWorkerRequest.id)
            workInfoByIdLiveData.observeForever {
                when (it.state) {
                    WorkInfo.State.SUCCEEDED -> {
                        setCurrentMovie(it.outputData.getString("fullMovie")!!)
                    }
                    WorkInfo.State.FAILED -> {
                        setLoading(LoadingStage.FAILURE, false)
                    }
                    else -> {
                        Log.d("yuval", "the work info stage is ${it.state}")
                    }
                }
            }
        } catch (e: Exception) {
            setLoading(LoadingStage.FAILURE, false)
        }

    }

    private fun setCurrentMovie(gson_str: String) {
        currentMovieFullData.value = gson.fromJson(gson_str, MovieFullData::class.java)
        setLoading(LoadingStage.SUCCESS, false)

    }

    fun reloadMoviesList() {
        try {
            moviesListStage.value = LoadingStage.LOADING
            val moviesListServerWorkerRequest =
                OneTimeWorkRequest.Builder(MoviesListServerWorker::class.java)
                    .setConstraints(
                        Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
                    ).build()
            moviesWorkManager.enqueue(moviesListServerWorkerRequest)
            val workInfoByIdLiveData =
                moviesWorkManager.getWorkInfoByIdLiveData(moviesListServerWorkerRequest.id)
            workInfoByIdLiveData.observeForever {
                when (it.state) {
                    WorkInfo.State.SUCCEEDED -> {
                        setMovies(ResultCall(it.outputData.getStringArray("moviesList")!!).results)
                    }
                    WorkInfo.State.FAILED -> {
                        setLoading(LoadingStage.FAILURE)
                    }
                    else -> {
                    }
                }
            }
        } catch (e: Exception) {
            moviesListStage.value = LoadingStage.FAILURE
            moviesList = listOf()
        }
    }

    private fun setMovies(results: List<MovieData>) {
        moviesList = results
        moviesListStage.value = LoadingStage.SUCCESS
    }

    fun getRecently(): List<MovieData> {
        return recentlyWatched.values.toList()
    }
}