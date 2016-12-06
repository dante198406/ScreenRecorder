package com.erobbing.squareprogressbar.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by zhangzhaolei on 2016/11/24.
 */

public class CalculationUtil {

    public static int convertDpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }
}
