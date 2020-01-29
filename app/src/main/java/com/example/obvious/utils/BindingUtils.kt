package com.example.obvious.utils

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

/**
 * Created by SURYA N on 29/1/20.
 */


@BindingAdapter("app:loadImageUrl")
fun loadImage(view: AppCompatImageView, url: String) {
    val context = view.context
    Glide.with(context).load(url)
        .thumbnail(0.2f)
        .transition(DrawableTransitionOptions.withCrossFade(100))
        .apply(RequestOptions().override(400,400))
        .transform(CenterCrop(), RoundedCorners(10)).into(object : CustomTarget<Drawable>() {
            override fun onLoadCleared(placeholder: Drawable?) {
                Log.d("Glide","Imfo")
            }

            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                view.setImageDrawable(resource)
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                super.onLoadFailed(errorDrawable)
            }

        })
}