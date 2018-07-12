package com.qflbai.bizhi.bizhi.camera;

import android.app.Activity;
import android.os.Build;
import android.view.TextureView;

import com.qflbai.bizhi.bizhi.camera.v1.CameraOneManager;
import com.qflbai.bizhi.bizhi.camera.v2.Camera2Manager;
import com.qflbai.bizhi.bizhi.util.SystemUtil;


/**
 * @author WenXian Bai
 * @Date: 2018/5/7.
 * @Description: 相机管理
 */

public class CameraManage implements CameraAgent {
    /**
     * caemra2
     */
    private CameraAgent mCameraAgent;

    @Override
    public void initDevice() {
        if (SystemUtil.getSDKVersion() < Build.VERSION_CODES.LOLLIPOP) {
            mCameraAgent = new CameraOneManager();
        } else {
            mCameraAgent = new Camera2Manager();

        }
        mCameraAgent.initDevice();
    }

    @Override
    public void openDevice(Activity activity, TextureView textureView) {
        if (mCameraAgent != null) {
            mCameraAgent.openDevice(activity, textureView);
        }
    }

    @Override
    public void closeDevice() {
        if (mCameraAgent != null) {
            mCameraAgent.switchFlashlight(false);
            mCameraAgent.closeDevice();
        }
    }

    @Override
    public boolean switchFlashlight(boolean isOpen) {
        if (mCameraAgent != null) {
            return mCameraAgent.switchFlashlight(isOpen);
        } else {
            return false;
        }
    }

}
