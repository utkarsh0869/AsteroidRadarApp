package com.udacity.asteroidradar.main

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R

@BindingAdapter("pictureUrl")
fun bindUriToImage(imageView: ImageView, strUrl: String?) {
    Picasso.with(imageView.context)
        .load(strUrl)
        .placeholder(R.drawable.placeholder_picture_of_day)
        .error(R.drawable.placeholder_picture_of_day)
        .into(imageView)
}