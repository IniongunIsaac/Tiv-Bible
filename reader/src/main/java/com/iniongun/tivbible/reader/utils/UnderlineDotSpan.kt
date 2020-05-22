package com.iniongun.tivbible.reader.utils

import android.R
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextUtils
import android.text.style.ReplacementSpan


/**
 * Created by Isaac Iniongun on 30/04/2020.
 * For Tiv Bible project.
 */

class UnderlineDotSpan(context: Context) : ReplacementSpan() {
    private val mSize: Float
    private val mDotColor: Int
    private val mTextColor: Int
    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        return Math.round(paint.measureText(text, start, end)).toInt()
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        if (TextUtils.isEmpty(text)) {
            return
        }
        val textSize: Float = paint.measureText(text, start, end)
        paint.setColor(mDotColor)
        canvas.drawCircle(
            x + textSize / 2,  // text center X
            bottom + mSize,  // dot center Y
            mSize / 2,  // radius
            paint
        )
        paint.setColor(mTextColor)
        canvas.drawText(text!!, start, end, x.toFloat(), y.toFloat(), paint)
    }

    init {
        val ta: TypedArray = context.theme.obtainStyledAttributes(
            intArrayOf(
                R.attr.colorAccent,
                R.attr.textColorPrimary
            )
        )
        mDotColor = R.attr.textColorPrimary
        mTextColor = R.attr.textColorPrimary
        ta.recycle()
        mSize = 2f
    }
}