package com.march.turbojpeg;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_compress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        new Thread(new Runnable() {
            @Override
            public void run() {
                work();
            }
        }).start();
    }

    private void work() {
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File fileSrc = new File(dir, "1.jpg");
        File fileDest = new File(dir, System.currentTimeMillis() + ".jpg");

        Bitmap bitmap = BitmapFactory.decodeFile(fileSrc.getAbsolutePath());

        TurboJpegUtils.compress(bitmap, 75, fileDest.getAbsolutePath(), true);
    }
}
