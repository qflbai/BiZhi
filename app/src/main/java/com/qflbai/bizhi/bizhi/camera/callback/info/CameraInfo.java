package com.qflbai.bizhi.bizhi.camera.callback.info;

/**
 * @author WenXian Bai
 * @Date: 2018/5/29.
 * @Description: 相机信息
 */
public class CameraInfo {
    /**
     * 相机一信息
     */
    private CameraOneInfo cameraOneInfo;
    /**
     * 相机2信息
     */
    private Camera2Info camera2Info;

    public CameraOneInfo getCameraOneInfo() {
        return cameraOneInfo;
    }

    public void setCameraOneInfo(CameraOneInfo cameraOneInfo) {
        this.cameraOneInfo = cameraOneInfo;
    }

    public Camera2Info getCamera2Info() {
        return camera2Info;
    }

    public void setCamera2Info(Camera2Info camera2Info) {
        this.camera2Info = camera2Info;
    }
}
