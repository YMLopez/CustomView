package com.lopez.customapplication.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.lopez.customapplication.utils.dp
import java.util.*


/**
 * @author Lopez
 * @date 2023/12/15
 * @time 9:22 PM
 */

private val COLORS = intArrayOf(
    Color.parseColor("#E91E63"),
    Color.parseColor("#673ab7"),
    Color.parseColor("#3f51b5"),
    Color.parseColor("#2196f3"),
    Color.parseColor("#009688"),
    Color.parseColor("#ff9800"),
    Color.parseColor("#ff5722"),
    Color.parseColor("#795548")
)

private val TEXT_SIZE = intArrayOf(16, 18, 20, 22, 24, 26, 28)
private val CORNER_RADIUS = 4.dp
private val X_PADDING = 16.dp.toInt()
private val Y_PADDING = 8.dp.toInt()

class ColoredTextView(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val random = Random()

    init {
        setTextColor(Color.WHITE)
        textSize = TEXT_SIZE[random.nextInt(7)].toFloat()
        paint.color = COLORS[random.nextInt(COLORS.size)]
        setPadding(X_PADDING, Y_PADDING, X_PADDING, Y_PADDING)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(0f, 0f, width.toFloat(), height.toFloat(), CORNER_RADIUS, CORNER_RADIUS, paint)
        super.onDraw(canvas)
    }

}