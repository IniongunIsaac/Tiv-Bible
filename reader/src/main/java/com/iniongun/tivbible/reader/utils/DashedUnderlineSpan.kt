package com.iniongun.tivbible.reader.utils

import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.text.Layout
import android.text.style.LineBackgroundSpan
import android.text.style.LineHeightSpan
import android.widget.TextView


/**
 * Created by Isaac Iniongun on 30/04/2020.
 * For Tiv Bible project.
 */

class DashedUnderlineSpan(
    textView: TextView, color: Int, thickness: Float, dashPath: Float,
    offsetY: Float, spacingExtra: Float
) :
    LineBackgroundSpan, LineHeightSpan {
    private val paint: Paint
    private val textView: TextView
    private val offsetY: Float
    private val spacingExtra: Int
    override fun chooseHeight(
        text: CharSequence?, start: Int, end: Int, spanstartv: Int, v: Int,
        fm: Paint.FontMetricsInt
    ) {
        fm.ascent -= spacingExtra
        fm.top -= spacingExtra
        fm.descent += spacingExtra
        fm.bottom += spacingExtra
    }

    override fun drawBackground(
        canvas: Canvas, p: Paint?, left: Int, right: Int, top: Int, baseline: Int,
        bottom: Int, text: CharSequence?, start: Int, end: Int, lnum: Int
    ) {
        val lineNum = textView.lineCount
        for (i in 0 until lineNum) {
            val layout: Layout = textView.layout
            canvas.drawLine(
                layout.getLineLeft(i), layout.getLineBottom(i) - spacingExtra + offsetY,
                layout.getLineRight(i), layout.getLineBottom(i) - spacingExtra + offsetY,
                paint
            )
        }
    }

    init {
        paint = Paint()
        paint.setColor(color)
        paint.setStyle(Paint.Style.STROKE)
        paint.setPathEffect(DashPathEffect(floatArrayOf(dashPath, dashPath), 0f))
        paint.setStrokeWidth(thickness)
        this.textView = textView
        this.offsetY = offsetY
        this.spacingExtra = spacingExtra.toInt()
    }
}