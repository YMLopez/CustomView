package com.lopez.customapplication.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.lopez.customapplication.utils.dp


/**
 * @author Lopez
 * @date 2023/12/14
 * @time 9:52 PM
 */

private val RADIUS = 100.dp
private val PADDING = 100.dp

class CircleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = ((PADDING + RADIUS) * 2).toInt()

        /*val specWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val specWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        val width = when(specWidthMode){
            MeasureSpec.EXACTLY -> specWidthSize
            MeasureSpec.AT_MOST -> if(size > specWidthSize) specWidthSize else size
            else -> size // MeasureSpec.UNSPECIFIED
        }*/

        val width = resolveSize(size, widthMeasureSpec)
        val height = resolveSize(size, heightMeasureSpec)
        //val height = resolveSizeAndState(size, heightMeasureSpec, 0)  //  没什么用，跟resolveSize方法一样
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawCircle(PADDING + RADIUS, PADDING + RADIUS, RADIUS, paint)
    }

}