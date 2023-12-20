package com.lopez.customapplication.utils;

import android.content.Context;

/**
 * @author Lopez
 * @date 2022/12/6
 * @time 2:35 PM
 */

public class DpUtil {

    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
