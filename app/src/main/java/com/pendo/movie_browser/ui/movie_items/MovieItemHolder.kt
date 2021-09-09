package com.pendo.movie_browser.ui.movie_items

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.pendo.movie_browser.R

class MovieItemHolder(view: View) : RecyclerView.ViewHolder(view) {
    val bg : ConstraintLayout = view.findViewById(R.id.bg_movie_item)
    val movieTitle: TextView = view.findViewById(R.id.movie_name_text)
    val movieDescription: TextView = view.findViewById(R.id.movie_item_description)
    val movieImg: ImageView = view.findViewById(R.id.movie_item_img)
}
