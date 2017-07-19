package com.aliya.view.ratio;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;

/**
 * 宽高比 - 助手
 *
 * @author a_liYa
 * @date 2017/7/18 23:00.
 */
public class RatioHelper {

    private float ratio_w2h = -1;

    private static final String RATIO_SYMBOL = ":";

    public RatioHelper(@NonNull Context context, @Nullable AttributeSet attrs) {
        if (attrs == null) return;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
        String w_h = ta.getString(R.styleable.RatioLayout_ratio_w2h);
        if (!TextUtils.isEmpty(w_h) && w_h.contains(RATIO_SYMBOL)) {
            String[] split = w_h.trim().split(RATIO_SYMBOL);
            if (split != null && split.length == 2) {
                try {
                    ratio_w2h = Float.parseFloat(split[0].trim())
                            / Float.parseFloat(split[1].trim());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        ta.recycle();
    }

    public float getRatio() {
        return ratio_w2h;
    }

    public RatioHelper setRatio(float w2h) {
        this.ratio_w2h = w2h;
        return this;
    }

    public int widthMeasureSpec(int widthMeasureSpec, int heightMeasureSpec) {
        if (ratio_w2h > 0) {
            int wMode = MeasureSpec.getMode(widthMeasureSpec);
            int hMode = MeasureSpec.getMode(heightMeasureSpec);

            int hSize = MeasureSpec.getSize(heightMeasureSpec);

            if (wMode != MeasureSpec.EXACTLY && hMode == MeasureSpec.EXACTLY) {
                widthMeasureSpec = MeasureSpec
                        .makeMeasureSpec(Math.round(hSize * ratio_w2h), View.MeasureSpec.EXACTLY);
            }
        }
        return widthMeasureSpec;
    }

    public int heightMeasureSpec(int widthMeasureSpec, int heightMeasureSpec) {
        if (ratio_w2h > 0) {
            int wMode = MeasureSpec.getMode(widthMeasureSpec);
            int hMode = MeasureSpec.getMode(heightMeasureSpec);

            int wSize = MeasureSpec.getSize(widthMeasureSpec);

            if (wMode == MeasureSpec.EXACTLY && hMode != MeasureSpec.EXACTLY) {
                heightMeasureSpec = MeasureSpec
                        .makeMeasureSpec(Math.round(wSize / ratio_w2h), MeasureSpec.EXACTLY);
            }
        }
        return heightMeasureSpec;
    }
}
