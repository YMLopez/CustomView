package com.lopez.customapplication

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.lopez.customapplication.utils.DpUtil


/**
 * @author Lopez
 * @date 2022/12/6
 * @time 2:25 PM
 */

class DragMaskView : View {

    private var paint1: Paint = Paint()
    private lateinit var rect: Rect

    init {
        paint1.color = Color.BLACK
        paint1.isAntiAlias = true
        paint1.strokeWidth = DpUtil.dip2px(this.context, 5f).toFloat()
        paint1.style = Paint.Style.STROKE
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rect = Rect(
            (w / 2 + 0.5f).toInt() - DpUtil.dip2px(this.context, 50f),
            (h / 2 + 0.5f).toInt() - DpUtil.dip2px(this.context, 50f),
            (w / 2 + 0.5f).toInt() + DpUtil.dip2px(this.context, 50f),
            (h / 2 + 0.5f).toInt() + DpUtil.dip2px(this.context, 50f)
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawRect(rect, paint1)
    }

    //@SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                //点击的时候使得所选View具备选中形态
                //按压的时候需要判断坐标，判断你当前点击的区域就是你的小方框
                val region = Region(rect)
                if (region.contains(event.x.toInt(), event.y.toInt())) {
                    paint1.color = Color.RED
                }
            }
            MotionEvent.ACTION_MOVE -> {
                //移动的时候，目标View也要随之移动

            }
            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                paint1.color = Color.BLACK
            }
        }
        invalidate()
        return true
    }

}