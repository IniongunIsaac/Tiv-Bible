package com.iniongun.tivbible.reader.more.bindings

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