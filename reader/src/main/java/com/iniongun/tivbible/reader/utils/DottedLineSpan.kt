package com.iniongun.tivbible.reader.utils

import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Paint.FontMetricsInt
import android.graphics.Path
import android.text.style.ReplacementSpan

/**
 * Created by Isaac Iniongun on 30/04/2020.
 * For Tiv Bible project.
 */


internal class DottedLineSpan(
    _spannedText: String
) :
    ReplacementSpan() {
    private var p = Paint()
    private var mWidth = 0
    private val mSpan: String
    private var mSpanLength = 0f
    private val mLengthIsCached = false
    private var mOffsetY = 0f
    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: FontMetricsInt?
    ): Int {
        mWidth = paint.measureText(text, start, end).toInt()
        return mWidth
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
        canvas.drawText(text!!, start, end, x, y.toFloat(), paint)
        if (!mLengthIsCached) mSpanLength = paint.measureText(mSpan)
        val path = Path()
        path.moveTo(x, y + mOffsetY)
        path.lineTo(x + mSpanLength, y + mOffsetY)
        canvas.drawPath(path, p)
    }

    init {
        val mStrokeWidth = 2f
        val mDashPathEffect = 5f
        mOffsetY = 4f
        p = Paint()
        p.color = android.R.attr.textColorPrimary
        p.style = Paint.Style.STROKE
        p.pathEffect = DashPathEffect(floatArrayOf(mDashPathEffect, mDashPathEffect), 0f)
        p.strokeWidth = mStrokeWidth
        mSpan = _spannedText
        mSpanLength = _spannedText.length.toFloat()
    }
}