package com.neil.githubkotlin.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


/**
 * Created by benny on 7/4/17.
 */
fun ImageView.loadWithGlide(url: String, placeHolder:Int, requestOptions: RequestOptions = RequestOptions()){
    Glide.with(this.context)
            .load(url)
            .apply(requestOptions)
            .placeholder(placeHolder)
            .into(this)
}