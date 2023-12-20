package com.lopez.customapplication.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.lopez.customapplication.utils.dp
import kotlin.math.min


/**
 * @author Lopez
 * @date 2023/12/14
 * @time 7:10 PM
 */

class SquareImageView(context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = min(measuredWidth, measuredHeight)   //  测量过程应该使用这个，因为这是最新的
        setMeasuredDimension(size, size)
    }

//    override fun layout(l: Int, t: Int, r: Int, b: Int) {
//        val width = r -l
//        val height = b -t
//        val size = min(width, height)
//        super.layout(l, t, (l + size), (t + size))  // 注意这里是l,t不是r,b
//    }


}