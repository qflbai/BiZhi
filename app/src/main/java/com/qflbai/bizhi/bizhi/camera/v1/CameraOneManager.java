package com.qflbai.bizhi.bizhi.camera.v1;

import android.app.Activity;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.TextureView;

import com.qflbai.bizhi.bizhi.camera.CameraAgent;
import com.qflbai.bizhi.bizhi.camera.callback.CameraOnePreviewCallback;
import com.qflbai.bizhi.bizhi.camera.compare.CameraOneCompareSizes;
import com.qflbai.bizhi.bizhi.camera.view.AutoFitTextureView;
import com.qflbai.bizhi.bizhi.util.SystemUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author WenXian Bai
 * @Date: 2018/5/4.
 * @Description:
 */

public class CameraOneManager implements CameraAgent, TextureView.SurfaceTextureListener {
    private final String TAG = CameraOneManager.class.getSimpleName();

    private CameraOnePreviewCallback mPreviewCallback;

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        openCamera(surface);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    private AutoFitTextureView mTextureView;
    private Activity mActivity;

    @Override
    public void initDevice() {
    }

    @Override
    public void openDevice(Activity activity, TextureView textureView) {
        mTextureView = (AutoFitTextureView) textureView;
        mActivity = activity;
        if (mTextureView.isAvailable()) {
            openCamera(mTextureView.getSurfaceTexture());
        } else {
            mTextureView.setSurfaceTextureListener(this);
        }
    }

    @Override
    public void closeDevice() {
        closeCamera();
    }

    @Override
    public boolean switchFlashlight(boolean isOpen) {
        if (isOpen) {
            return openLight();
        } else {
            return offLight();
        }
    }

    /**
     * 相机
     */
    private Camera mCamera;

    /**
     * 打开相机
     */
    private void openCamera(SurfaceTexture surfaceTexture) {
        try {
            if (null == mCamera) {
                mCamera = Camera.open(0);
            }

            if (null != mCamera) {
                //为了连接Camera和Surface,设置Surface被实时预览使
                mCamera.setPreviewTexture(surfaceTexture);
                Camera.Parameters parameters = mCamera.getParameters();
                setDesiredCameraParameters(mCamera);
                //开始捕捉和绘制预览帧到屏幕上
                mCamera.startPreview();

               // mCamera.setPreviewCallback(mPreviewCallback);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 预览尺寸
     */
    private Camera.Size mPreviewSize;

    /**
     * 相机参数设置
     *
     * @param camera
     */
    void setDesiredCameraParameters(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        //parameters.setPreviewFormat(PixelFormat.YCbCr_420_SP);
        parameters.setPreviewFormat(ImageFormat.NV21);

        mPreviewSize = getMaxPreviewSizeValue(parameters);
        parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);

        setFlash(parameters);
        setZoom(parameters);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        camera.setParameters(parameters);

        setCameraDisplayOrientation(camera);
    }

    /**
     * 相机预览方向设置
     */
    private void setCameraDisplayOrientation(Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(0, info);
        int rotation = mActivity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
            default:
                break;
        }
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;   // compensate the mirror
        } else {
            // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    /**
     * 返回适合预览的尺寸
     *
     * @param parameters
     * @return
     */
    private Camera.Size getMaxPreviewSizeValue(Camera.Parameters parameters) {

        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();
        CameraOneCompareSizes cameraOneCompareSizes = new CameraOneCompareSizes();
        Collections.sort(sizeList, cameraOneCompareSizes);

        DisplayMetrics metrics = getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;
        int temp = 0;
        if (screenWidth < screenHeight) {
            temp = screenWidth;
            screenWidth = screenHeight;
            screenHeight = temp;
        }

        // 收集至少和预览表面一样大的支持的分辨率
        List<Camera.Size> bigEnough = new ArrayList<>();
        for (Camera.Size size : sizeList) {
            if (size.height == (size.width * screenHeight / screenWidth)) {
                bigEnough.add(size);
            }
        }

        if (bigEnough.size() > 0) {
            return Collections.max(bigEnough, new CameraOneCompareSizes());
        } else {
            screenWidth = screenWidth - SystemUtil.getStatusBarHeight(mActivity);
            for (Camera.Size size : sizeList) {
                if (size.height == (size.width * screenHeight / screenWidth)) {
                    bigEnough.add(size);
                }
            }

            if (bigEnough.size() > 0) {
                return Collections.max(bigEnough, new CameraOneCompareSizes());
            } else {
                return Collections.max(sizeList, new CameraOneCompareSizes());
            }
        }
    }

    /**
     * 获取屏幕分辨率
     *
     * @return
     */
    @NonNull
    public DisplayMetrics getDisplayMetrics() {
        DisplayMetrics metrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        return metrics;
    }

    /**
     * 获取相机预览分辨率
     */
    public Camera.Size getmPreviewSize() {
        return mPreviewSize;
    }

    /**
     * 设置闪光灯
     *
     * @param parameters
     */
    private void setFlash(Camera.Parameters parameters) {
        if (Build.MODEL.contains("Behold II") && Build.VERSION.SDK_INT == 3) { // 3
            parameters.set("flash-value", 1);
        } else {
            parameters.set("flash-value", 2);
        }
        parameters.set("flash-mode", "off");
    }

    /**
     * 设置预览放大放大
     *
     * @param parameters
     */
    private void setZoom(Camera.Parameters parameters) {

        String maxZoomString = parameters.get("max-zoom");
        String motZoomValuesString = parameters.get("mot-zoom-values");

        if (maxZoomString != null || motZoomValuesString != null) {
            //parameters.set("zoom", String.valueOf(i));
            // todo:待做
        }
    }


    private void closeCamera() {
        if (null != mCamera) {

            try {
                mCamera.setOneShotPreviewCallback(null);
                mCamera.autoFocus(null);
                mCamera.stopPreview();  //停止捕捉和绘制预览帧到屏幕上
                mCamera.release();
                mCamera = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean openLight() {
        boolean flag = false;
        try {
            if (null != mCamera) {
                Camera.Parameters parameter = mCamera.getParameters();
                parameter.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(parameter);
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    private boolean offLight() {
        boolean flag = false;
        try {
            if (null != mCamera) {
                Camera.Parameters parameter = mCamera.getParameters();
                parameter.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(parameter);
                flag = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
