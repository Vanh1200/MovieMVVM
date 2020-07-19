package com.vanh1200.moviemvvm.base

import android.app.Activity
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.setImage(url: String) {
    Glide.with(this).load(url).into(this)
}