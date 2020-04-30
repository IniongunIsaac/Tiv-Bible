package com.iniongun.tivbible.reader.read.bindings

import android.graphics.Paint
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.iniongun.tivbible.entities.Verse
import com.iniongun.tivbible.reader.read.adapters.ChaptersAdapter
import com.iniongun.tivbible.reader.read.adapters.VersesAdapter


/**
 * Created by Isaac Iniongun on 18/04/2020.
 * For Tiv Bible project.
 */

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Verse>?) {
    items?.let {
        (listView.adapter as VersesAdapter).submitList(items)
    }
}

@BindingAdapter("app:items")
fun setItems(viewPager: ViewPager2, items: List<Verse>?) {
    items?.let {
        (viewPager.adapter as ChaptersAdapter).submitList(items)
    }
}

@BindingAdapter("app:formattedText")
fun setFormattedText(textView: AppCompatTextView, verse: Verse) {
    textView.text = with(verse) {
        buildSpannedString {
            append("\t\t")
            bold { append("$number.") }
            append(text)
        }
    }
}

@BindingAdapter("android:background")
fun setDottedLineBackground(textView: AppCompatTextView, enabled: Boolean) {
//    if (enabled) {
//        textView.background = ContextCompat.getDrawable(textView.context, R.drawable.dotted_underline)
//    } else {
//        textView.background = null
//    }

//    val spannableStringBuilder = SpannableStringBuilder(textView.text)
//    val intSpannableStringBuilderLength = spannableStringBuilder.length
//    val context = textView.context
//    val dashedUnderlineSpan = DashedUnderlineSpan(
//        textView, ContextCompat.getColor(context, R.color.colorAccent),
//        R.dimen.dus_stroke_thickness.toFloat(),
//        R.dimen.dus_dash_path.toFloat(),
//        R.dimen.dus_offset_y.toFloat(),
//        R.dimen.dus_spacing_extra.toFloat()
//    )
//
//    spannableStringBuilder.setSpan(
//        dashedUnderlineSpan, 0,
//        intSpannableStringBuilderLength, Spanned.SPAN_INCLUSIVE_INCLUSIVE
//    )
//
//    textView.text = spannableStringBuilder

//
//    if (enabled) {
//        textView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
//        spannableStringBuilder.setSpan(
//            dashedUnderlineSpan, 0,
//            intSpannableStringBuilderLength, Spanned.SPAN_INCLUSIVE_INCLUSIVE
//        )
//    } else {
//        spannableStringBuilder.removeSpan(dashedUnderlineSpan)
//    }
//
//    textView.text = spannableStringBuilder

//    val string = textView.text.toString()
//    val text = SpannableString(string)
////    val dottedLineSpan = DottedLineSpan(string)
//    val underlineDotSpan = UnderlineDotSpan(textView.context)
//

    if (enabled) {
        textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
//        text.setSpan(underlineDotSpan, string.indexOf(string.first()), string.indexOf(string.last()), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        textView.text = text
    } else {
        textView.paintFlags = textView.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
//        text.removeSpan(underlineDotSpan)
//        textView.text = text
    }
}