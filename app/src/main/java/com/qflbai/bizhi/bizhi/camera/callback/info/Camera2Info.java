package com.qflbai.bizhi.bizhi.camera.callback.info;

import android.graphics.Rect;

import java.nio.ByteBuffer;

/**
 * @author WenXian Bai
 * @Date: 2018/5/29.
 * @Description:
 */
public class Camera2Info {
    /**
     * Image 宽高
     */
    private int imageWidth;
    /**
     * Image 宽高
     */
    private int imageHeight;
    /**
     * Image CropRect
     */
    private Rect imageCropRect;
    /**
     * y通道缓冲区
     */
    private ByteBuffer yPlaneBuffer;
    /**
     * y通道 PixelStride
     */
    private int yPixelStride;
    /**
     * y通道 RowStride
     */
    private int yRowStride;
    /**
     * y通道完整信息
     */
    private byte[] yData;

    // todo: 后续需要v u 数据在添加


    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public Rect getImageCropRect() {
        return imageCropRect;
    }

    public void setImageCropRect(Rect imageCropRect) {
        this.imageCropRect = imageCropRect;
    }

    public ByteBuffer getyPlaneBuffer() {
        return yPlaneBuffer;
    }

    public void setyPlaneBuffer(ByteBuffer yPlaneBuffer) {
        this.yPlaneBuffer = yPlaneBuffer;
    }

    public int getyPixelStride() {
        return yPixelStride;
    }

    public void setyPixelStride(int yPixelStride) {
        this.yPixelStride = yPixelStride;
    }

    public int getyRowStride() {
        return yRowStride;
    }

    public void setyRowStride(int yRowStride) {
        this.yRowStride = yRowStride;
    }

    public byte[] getyData() {
        return yData;
    }

    public void setyData(byte[] yData) {
        this.yData = yData;
    }
}
