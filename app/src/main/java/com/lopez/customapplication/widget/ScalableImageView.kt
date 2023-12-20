package com.lopez.customapplication.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller
import androidx.core.animation.doOnEnd
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import com.lopez.customapplication.utils.dp
import com.lopez.customapplication.utils.getAvatar
import java.lang.Float.max
import java.lang.Float.min


/**
 * @author Lopez
 * @date 2023/12/19
 * @time 10:46 PM
 */

private val IMAGE_SIZE = 300.dp.toInt()
private const val EXTRA_SCALE_FACTOR = 1.5f   //  放缩系数

class ScalableImageView(context: Context, attrs: AttributeSet?) : View(context, attrs),
    GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, Runnable {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(resources, IMAGE_SIZE)
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f
    private var offsetX = 0f
    private var offsetY = 0f
    private var smallScale = 0f
    private var bigScale = 0f
    private var isBig = false
    private val gestureDetector = GestureDetectorCompat(context, this)
    private var scaleFraction = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val scaleAnimator: ObjectAnimator by lazy {
        ObjectAnimator.ofFloat(this, "scaleFraction", 0f, 1f).apply {
            doOnEnd {
                if (!isBig){
                    offsetX = 0f
                    offsetY = 0f
                }
            }
        }
    }
    private val overScroller = OverScroller(context)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        originalOffsetX = (width - IMAGE_SIZE) / 2f
        originalOffsetY = (height - IMAGE_SIZE) / 2f
        //图像的宽高比与视图的宽高比
        if (bitmap.width / bitmap.height.toFloat() > width / height.toFloat()) {
            smallScale = width / bitmap.width.toFloat()
            bigScale = height / bitmap.height.toFloat() * EXTRA_SCALE_FACTOR
        } else {
            smallScale = height / bitmap.height.toFloat()
            bigScale = width / bitmap.width.toFloat() * EXTRA_SCALE_FACTOR
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction)
        // the scale can be smallScale or bigScale
        //val scale = if (isBig) bigScale else smallScale
        val scale = smallScale + (bigScale - smallScale) * scaleFraction
        canvas.scale(scale, scale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    //每次 ACTION_DOWN 事件出现的时候都会被调用，在这里返回 true可以保证必然消费掉事件
    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    //用户按下 100ms 不松手后会被调用，用于标记「可以显示按下状态了」
    override fun onShowPress(e: MotionEvent?) {
    }

    //单击事件
    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    //其实就是MOVE事件
    override fun onScroll(
        downEvent: MotionEvent?,
        currentEvent: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        // 偏移是按下时的位置 - 当前事件的位置，所以这里负负得正，就是相加
        if (isBig) {
            //限制可滑动的边界
            offsetX -= distanceX
            offsetX = min(offsetX, (bitmap.width * bigScale - width) / 2)
            offsetX = max(offsetX, -(bitmap.width * bigScale - width) / 2)

            offsetY -= distanceY
            offsetY = min(offsetY, (bitmap.height * bigScale - height) / 2)
            offsetY = max(offsetY, -(bitmap.height * bigScale - height) / 2)
            invalidate()
        }
        return false
    }

    //长按事件
    override fun onLongPress(e: MotionEvent?) {
        // 用户⻓按（按下 500ms 不松手）后会被调用
        // 这个 500ms 在 GestureDetectorCompat 中变成了 600ms
    }

    //惯性滑动/快滑事件，velocity是速率
    override fun onFling(
        downEvent: MotionEvent?,
        currentEvent: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        if (isBig) {
            overScroller.fling(
                offsetX.toInt(),
                offsetY.toInt(),
                velocityX.toInt(),
                velocityY.toInt(),
                (-(bitmap.width * bigScale - width) / 2).toInt(),
                ((bitmap.width * bigScale - width) / 2).toInt(),
                (-(bitmap.height * bigScale - height) / 2).toInt(),
                ((bitmap.height * bigScale - height) / 2).toInt(),
                30.dp.toInt(), 30.dp.toInt()
            )
//            post(this)                        // 马上把runnable推到主线程
//            postOnAnimation(this)             // 在下一帧把runnable推到主线程
            ViewCompat.postOnAnimation(this, this)
        }
        return false
    }

    override fun run() {
        // 计算此时的位置，并且如果滑动已经结束，就停止
        if (overScroller.computeScrollOffset()) {
            //把此时的偏移应用于界面
            offsetX = overScroller.currX.toFloat()
            offsetY = overScroller.currY.toFloat()
            invalidate()
            //如果还在计算滑动便宜，那么就继续调用
//            post(this)
//            postOnAnimation(this)             // API版本比较高，下面的是兼容版本
            ViewCompat.postOnAnimation(this, this) // 下一帧刷新
        }
    }

    //在存在双击事件的前提下，因为有个延迟等待的时间，比起onSingleTapUp的单击事件显得更精确
    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        //用户的一次点击不会立即调用，这个方法，而是在一定时间后（300ms），确认用户没有进行双击，这个方法才会被调用
        return false
    }

    //双击事件，具有防抖动功能，间隔低于40ms的双击不会被认为是双击事件
    override fun onDoubleTap(e: MotionEvent): Boolean {
        isBig = !isBig
        if (isBig) {
            offsetX = (e.x - width/2) * (1 - bigScale/smallScale)
            offsetY = (e.y - height/2) * (1 - bigScale/smallScale)

            scaleAnimator.start()
        } else {
            scaleAnimator.reverse()
        }
        return false
    }

    //在双击后，存在后续事件（如：双击操作的第二次按下后不要放手，可进行移动）
    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        return false
    }

}