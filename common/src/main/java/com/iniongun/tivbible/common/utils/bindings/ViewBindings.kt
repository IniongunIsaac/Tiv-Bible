package com.iniongun.tivbible.common.utils.bindings

import android.graphics.Typeface
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

/**
 * Created by Isaac Iniongun on 26/04/2020.
 * For Tiv Bible project.
 */

@BindingAdapter("app:fontTypeface")
fun setFontStyle(textView: AppCompatTextView, fontName: String) {
    textView.typeface = Typeface.createFromAsset(textView.context.assets, "font/$fontName")
}

@BindingAdapter("app:fontTypeface")
fun setFontStyle(button: MaterialButton, fontName: String) {
    button.typeface = Typeface.createFromAsset(button.context.assets, "font/$fontName")
}

@BindingAdapter("app:fontTypeface")
fun setFontStyle(button: TextInputLayout, fontName: String) {
    button.typeface = Typeface.createFromAsset(button.context.assets, "font/$fontName")
}