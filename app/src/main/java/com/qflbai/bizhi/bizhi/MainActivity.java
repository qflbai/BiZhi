package com.qflbai.bizhi.bizhi;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.qflbai.bizhi.bizhi.camera.CameraManage;
import com.qflbai.bizhi.bizhi.camera.view.AutoFitTextureView;
import com.qflbai.bizhi.bizhi.service.CameraWallpaperService;

public class MainActivity extends AppCompatActivity {

    private CameraManage mCameraManage;
    private AutoFitTextureView mTextureView;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //hideStatusNavigationBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mCameraManage = new CameraManage();
        mCameraManage.initDevice();
        mTextureView = findViewById(R.id.act_preview);

        if (Integer.valueOf(android.os.Build.VERSION.SDK_INT) >= Build.VERSION_CODES.M) {
            // 判断是否有权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                // 申请权限
                this.requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            } else {
                // 初始化
                // mCameraManage.openDevice(this, mTextureView);
                startPreview();
            }

        } else {
            // 初始化
            // mCameraManage.openDevice(this, mTextureView);
            startPreview();
        }
    }

    /**
     * 隐藏状态栏与导航栏
     */
    private void hideStatusNavigationBar() {
        View decorView = this.getWindow().getDecorView();
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            decorView.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            int uiOptions =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
       // backgroundAlpha(0.5f);
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    /**
     * 权限申请状态值
     */
    private static final int REQUEST_CAMERA_PERMISSION = 2;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               // mCameraManage.openDevice(this, mTextureView);
                startPreview();
            } else {
                Log.e("tag", "权限申请失败");
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // mCameraManage.closeDevice();
    }

    private void startPreview(){
        Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,new ComponentName(mContext, CameraWallpaperService.class));
        startActivity(intent);
        finish();
    }
}
