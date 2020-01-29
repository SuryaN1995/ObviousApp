package com.example.obvious.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.obvious.R
import com.example.obvious.ui.activity.ImageGridScreen
import kotlin.math.abs


/**
 * Created by SURYA N on 29/1/20.
 */

@BindingAdapter("bind:url", "bind:size")
fun loadImage(view: AppCompatImageView, url: String, size: Int) {
    val context = view.context
    val width = if(size == 500) size else getWidth(context).toInt()
    val height = if(size == 500) size else getHeight(context).toInt()
    Glide.with(context).load(url)
        .thumbnail(0.2f)
        .transition(DrawableTransitionOptions.withCrossFade(100))
        .transform(CenterCrop())
        .apply(RequestOptions().override(width, height))
        .placeholder(R.drawable.ic_launcher_foreground)
        .into(object : CustomTarget<Drawable>() {
            override fun onLoadCleared(placeholder: Drawable?) {
                view.setImageDrawable(placeholder)
            }

            override fun onLoadStarted(placeholder: Drawable?) {
                super.onLoadStarted(placeholder)
                view.setImageDrawable(placeholder)
            }

            override fun onResourceReady(
                resource: Drawable,
                transition: Transition<in Drawable>?
            ) {
                view.setImageDrawable(resource)
            }

        })
}

fun getPopUpWindow(
    context: Context,
    popupView: View,
    anchor: View,
    parentView: View,
    needMargin: Boolean,
    position: Int
): PopupWindow {
    val popupMenu = PopupWindow(popupView, 300.toPx, ViewGroup.LayoutParams.WRAP_CONTENT)
    popupMenu.setBackgroundDrawable(
        ColorDrawable(
            Color.TRANSPARENT
        )
    )
    popupMenu.isOutsideTouchable = true
    val values = IntArray(2)
    parentView.getLocationInWindow(values)
    val positionOfIcon = values[1]
    val displayHeight = context.resources.displayMetrics?.heightPixels ?: 0
    val height = (displayHeight) / 2
    val differenceHeight = abs((displayHeight / 2) - positionOfIcon)
    if (needMargin) {
        when {
            positionOfIcon > height -> {
                popupMenu.showAsDropDown(
                    anchor,
                    -(130.toPx),
                    -(position.toPx),
                    Gravity.BOTTOM
                )
                //popupMenu.showAtLocation(anchor, Gravity.TOP, parentView.right -(130.toPx), parentView.bottom)
            }
            else -> {
                popupMenu.showAsDropDown(
                    anchor,
                    -(130.toPx),
                    0,
                    Gravity.BOTTOM
                )
            }
        }
    } else {
        when {
            differenceHeight < 300 -> popupMenu.showAtLocation(anchor, Gravity.END, 50.toPx, 0)
            else -> popupMenu.showAsDropDown(anchor, 0, 0, Gravity.END)
        }
    }
    return popupMenu
}

val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

private fun getScreenDimension(context: Context): DisplayMetrics {
    val displayMetrics = DisplayMetrics()
    (context as ImageGridScreen).windowManager.defaultDisplay.getMetrics(displayMetrics)
    context.resources.displayMetrics.scaledDensity
    return displayMetrics
}

private fun getScreenDensity(context: Context): Float {
    return context.resources.displayMetrics.density
}


fun getWidth(context: Context): Float {
    val displayMetrics = getScreenDimension(context)
    return displayMetrics.widthPixels / getScreenDensity(context)
}

fun getHeight(context: Context): Float {
    val displayMetrics = getScreenDimension(context)
    return displayMetrics.heightPixels / getScreenDensity(context)
}