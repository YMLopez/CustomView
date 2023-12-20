package com.lopez.customapplication.utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.TypedValue
import com.lopez.customapplication.R


/**
 * @author Lopez
 * @date 2023/12/14
 * @time 7:26 PM
 */


val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

val Int.dp
    get() = this.toFloat().dp


fun getAvatar(res: Resources, width: Int): Bitmap {
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeResource(res, R.drawable.avatar, options)
    options.inJustDecodeBounds = false
    options.inDensity = options.outWidth
    options.inTargetDensity = width
    return BitmapFactory.decodeResource(res, R.drawable.avatar, options)
}
