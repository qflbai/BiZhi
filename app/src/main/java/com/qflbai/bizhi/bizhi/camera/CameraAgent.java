package com.qflbai.bizhi.bizhi.camera;

import android.app.Activity;
import android.view.TextureView;

/**
 * @author WenXian Bai
 * @Date: 2018/5/4.
 * @Description:
 */

public interface CameraAgent {
    /**
     * 初始化
     */
    void initDevice();

    /**
     * 打开设备
     *
     * @param activity
     * @param textureView
     */
    void openDevice(Activity activity, TextureView textureView);

    /**
     * 关闭设备
     */
    void closeDevice();

    /**
     * 开启闪光灯
     *
     * @param isOpen 是否开启闪光灯
     */
    boolean switchFlashlight(boolean isOpen);

}
