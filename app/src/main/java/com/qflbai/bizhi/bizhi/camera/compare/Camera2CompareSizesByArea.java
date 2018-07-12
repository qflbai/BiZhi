package com.qflbai.bizhi.bizhi.camera.compare;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Size;

import java.util.Comparator;

/**
 * @author WenXian Bai
 * @Date: 2018/5/4.
 * @Description:比较尺寸值大小
 *
 */
public class Camera2CompareSizesByArea implements Comparator<Size> {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int compare(Size lhs, Size rhs) {
        // We cast here to ensure the multiplications won't overflow
        return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                (long) rhs.getWidth() * rhs.getHeight());
    }
}
