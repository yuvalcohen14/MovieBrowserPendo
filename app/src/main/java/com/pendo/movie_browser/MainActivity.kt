package com.pendo.movie_browser

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.pendo.movie_browser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val app = MoviesApp.instance
    override fun onCreate(savedInstanceState: Bundle?) {
        this.window?.decorView?.layoutDirection = View.LAYOUT_DIRECTION_LTR;
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolBar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        app.currentTitle.observeForever {
            toolBar.title = it
        }

    }

    override fun onBackPressed() {

        if (app.currentFragment == "fullMovie") {
            Navigation.findNavController(findViewById(R.id.nav_host_fragment))
                .navigate(R.id.action_SecondFragment_to_FirstFragment)
        } else if (app.currentMoviePage > 1) {
            app.currentMoviePage -= 1
            app.reloadMoviesList()
            Navigation.findNavController(findViewById(R.id.nav_host_fragment))
                .navigate(R.id.action_FirstFragment_to_launcherFragment)
        } else {
            super.onBackPressed()
        }
    }
}