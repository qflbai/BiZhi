package com.qflbai.bizhi.bizhi.camera.callback;

import android.media.Image;
import android.media.ImageReader;



/**
 * @author WenXian Bai
 * @Date: 2018/5/4.
 * @Description:camera2预览回调
 */

public class ImageCaptureListener implements ImageReader.OnImageAvailableListener {

    @Override
    public void onImageAvailable(ImageReader reader) {
        Image mImage = reader.acquireNextImage();
        mImage.close();
    }

}
