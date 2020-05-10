package com.iniongun.tivbible.reader.more.bindings

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

/**
 * Created by Isaac Iniongun on 09/05/2020.
 * For Tiv Bible project.
 */

@BindingAdapter("app:image")
fun setImage(imageView: AppCompatImageView, @DrawableRes imageRes: Int) {
    imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, imageRes))
}

@SuppressLint("NewApi")
@BindingAdapter("app:highlightColor")
fun setHighlightColor(view: View, @ColorRes color: Int) {
    val gradientDrawable = GradientDrawable()
    gradientDrawable.setColor(ContextCompat.getColor(view.context, color))
    //topLeft, topLeft, topRight, topRight, bottomRight, bottomRight, bottomLeft, bottomLeft
    gradientDrawable.cornerRadii = floatArrayOf(8f, 8f, 0f, 0f, 0f, 0f, 8f, 8f)
    view.background = gradientDrawable
}