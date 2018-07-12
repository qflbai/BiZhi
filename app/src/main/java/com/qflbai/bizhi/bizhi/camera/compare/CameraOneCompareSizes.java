package com.qflbai.bizhi.bizhi.camera.compare;

import android.hardware.Camera;

import java.util.Comparator;

/**
 * @author WenXian Bai
 * @Date: 2018/5/4.
 * @Description:
 */

public class CameraOneCompareSizes implements Comparator<Camera.Size> {
    @Override
    public int compare(Camera.Size o1, Camera.Size o2) {
        return Long.signum((long) o1.width * o1.height -
                (long) o2.width * o2.height);
    }
}
