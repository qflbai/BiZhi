package com.qflbai.bizhi.bizhi.camera.callback.info;

/**
 * @author WenXian Bai
 * @Date: 2018/5/29.
 * @Description:
 */
public class CameraOneInfo {
    /**
     * N21 数据宽高
     */
    private int width;
    private int height;
    /**
     * N21字节数组数据
     */
    private byte[] imageData;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
