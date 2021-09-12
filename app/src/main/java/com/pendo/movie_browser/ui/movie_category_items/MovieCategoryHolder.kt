package com.pendo.movie_browser.ui.movie_category_items

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.pendo.movie_browser.R

class MovieCategoryHolder(view: View) : RecyclerView.ViewHolder(view) {
    val bg : ConstraintLayout = view.findViewById(R.id.bg_movie_item)
    val movieCategory: TextView = view.findViewById(R.id.movie_category_text)
    val movieRecycler: RecyclerView = view.findViewById(R.id.movies_recycler)
}
