package com.pendo.movie_browser.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pendo.movie_browser.MoviesApp
import com.pendo.movie_browser.R
import com.pendo.movie_browser.databinding.FragmentMainRecyclerBinding
import com.pendo.movie_browser.ui.movie_items.MoviesAdapter

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainRecyclerFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieApp: MoviesApp = MoviesApp.instance

        // find all views
        val recyclerView = view.findViewById<RecyclerView>(R.id.movie_recycler)
        val adapter by lazy { return@lazy MoviesAdapter()}

        movieApp.currentTitle.value = movieApp.appName
        movieApp.currentFragment= "recycler"
        adapter.setItems(MoviesApp.instance.getMovies())
        recyclerView.layoutManager =
            GridLayoutManager(requireActivity(), 2, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}