package com.aplicacion.ejercicio21;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static int CAMERA_PERMISSION_CODE = 100;
    private static int VIDEO_RECORD_CODE = 101;
    private Uri videoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isCameraPresentInPhone()){
            Log.i("VIDEO_RECORD_TAG", "CAMERA IS DETECTED");
            getCameraPermission();
        }
        else{
            Log.i("VIDEO_RECORD_TAG", "CAMERA IS NOT DETECTED");
        }
    }

    public void recordVideoButtonPressed(View view){
    recordVideo();

    }

    private boolean isCameraPresentInPhone() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }
    private void getCameraPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                ==PackageManager.PERMISSION_DENIED){

            ActivityCompat.requestPermissions(this, new String[]{new String(Manifest.permission.CAMERA)}, CAMERA_PERMISSION_CODE);

        }
    }

    private void recordVideo(){
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, VIDEO_RECORD_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
     if(requestCode==VIDEO_RECORD_CODE) {
         if (resultCode == RESULT_OK) {
             videoPath = data.getData();
            Log.i("VIDEO_RECORD_TAG","VIDEO is record and availbe at path"+ videoPath);
         }
         else if(resultCode==RESULT_CANCELED){
             Log.i("VIDEO_RECORD_TAG","Recording video is cancelled");
         }
         else{
             Log.i("VIDEO_RECORD_TAG","Recording video has got some error");
         }
     }
    }
}

