package com.pendo.movie_browser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.pendo.movie_browser.MoviesApp
import com.pendo.movie_browser.R
import com.pendo.movie_browser.server.workers.MovieData

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MovieFragment : Fragment() {
    private val moviesApp: MoviesApp by lazy { MoviesApp.instance }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieImg = view.findViewById<ImageView>(R.id.movie_img)
        val movieDescription = view.findViewById<TextView>(R.id.movie_description_text)
        val movieRate = view.findViewById<TextView>(R.id.movie_rate_text)
        val movieDate = view.findViewById<TextView>(R.id.release_date_text)
        val movieExtra = view.findViewById<TextView>(R.id.extra_text)


        val curMovie = moviesApp.currentMovieFullData.value!!
        moviesApp.currentFragment= "fullMovie"

        moviesApp.recentlyWatched += Pair(curMovie.id,MovieData(curMovie.overview,curMovie.title,curMovie.poster_path,curMovie.id))

        Glide.with(requireContext())
            .load(moviesApp.imgStartURL + curMovie.poster_path)
            .into(movieImg)

        MoviesApp.instance.currentTitle.value = curMovie.title

        movieDate.text = "release Date: ${curMovie.release_date}"
        movieDescription.text = curMovie.overview
        movieRate.text = "Rate: ${curMovie.vote_average}"
        movieExtra.text = "Popularity: ${curMovie.popularity} \n\n Original Language: ${curMovie.original_language}"
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}