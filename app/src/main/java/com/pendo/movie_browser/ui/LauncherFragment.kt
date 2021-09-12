package com.pendo.movie_browser.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.pendo.movie_browser.MoviesApp
import com.pendo.movie_browser.R
import com.pendo.movie_browser.model.LoadingStage

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class LauncherFragment : Fragment() {
    private lateinit var app: MoviesApp
    private lateinit var list_observer: Observer<LoadingStage>
    private lateinit var full_movie_observer: Observer<LoadingStage>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("yuval", "start launcher")

        return inflater.inflate(R.layout.fragment_launcher, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        app = MoviesApp.instance
        list_observer = Observer<LoadingStage> {
            when (it) {
                LoadingStage.SUCCESS -> {
                    app.currentTitle.value = app.appName
                    Navigation.findNavController(view)
                        .navigate(R.id.action_launcherFragment_to_FirstFragment)
                }
                LoadingStage.FAILURE -> {
                    app.reloadMoviesList()
                }
                else -> {
                }
            }
        }
        full_movie_observer = Observer<LoadingStage> {
            when (it) {
                LoadingStage.SUCCESS -> {
                    Log.d("yuval", app.moviesListStage.value.toString())
                    Navigation.findNavController(view)
                        .navigate(R.id.action_launcherFragment_to_SecondFragment)

                }
                LoadingStage.FAILURE -> {
                }
                else -> {
                    Log.d("yuval", app.moviesListStage.value.toString())
                }
            }
        }
        if (app.fullMovieStage.value == LoadingStage.LOADING) {
            app.fullMovieStage.observe(viewLifecycleOwner, full_movie_observer)
        } else {
            app.moviesListStage.observe(viewLifecycleOwner, list_observer)
        }
        if (app.moviesListStage.value == LoadingStage.SUCCESS && app.fullMovieStage.value == LoadingStage.FAILURE){
            Navigation.findNavController(view)
                .navigate(R.id.action_launcherFragment_to_FirstFragment)
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        app.fullMovieStage.removeObserver(full_movie_observer)
        app.moviesListStage.removeObserver(list_observer)


    }
}