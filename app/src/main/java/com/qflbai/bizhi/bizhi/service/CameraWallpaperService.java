package com.qflbai.bizhi.bizhi.service;

import android.hardware.Camera;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * @author WenXian Bai
 * @Date: 2018/6/27.
 * @Description:
 */
public class CameraWallpaperService extends WallpaperService {
    @Override
    public Engine onCreateEngine() {
        return new CameraEngine();
    }

    class CameraEngine extends Engine{
        Camera camera;
        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            setTouchEventsEnabled(true);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
            //可以在这里处理添加触摸事件，如点击拍照
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            if(visible){
                startPreview();
            }else{
                stopPreview();
            }
        }

        @Override
        public void onDestroy() {
            stopPreview();
            super.onDestroy();
        }

        /**
         * 开始预览
         */
        private void startPreview() {
            camera = Camera.open();
            //需要旋转90度
            camera.setDisplayOrientation(90);
            try {
                camera.setPreviewDisplay(getSurfaceHolder());
                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 关闭预览
         */
        private void stopPreview() {
            if (camera != null) {
                camera.stopPreview();
                camera.release();
                camera = null;
            }
        }
    }
}
