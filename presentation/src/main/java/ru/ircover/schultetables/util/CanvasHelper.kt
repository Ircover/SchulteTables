package ru.ircover.schultetables.util

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

fun Canvas.drawTextAtCenter(text: String, centerX: Float, centerY: Float, paint: Paint) {
    val textBounds = Rect()
    paint.getTextBounds(text, 0, text.length, textBounds)
    val left = centerX - textBounds.width() / 2f - 1
    val top = centerY + textBounds.height() / 2f
    drawText(text, left, top, paint)
}