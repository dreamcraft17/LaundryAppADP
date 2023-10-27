package com.ADP.bubblelaundryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity {
    private static final String STORAGE_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String CAMERA_PERMISSION = Manifest.permission.CAMERA;
    private static final int PERMISSION_REQ_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        requestRuntimePermission(STORAGE_PERMISSION);

        Button cameraReq = findViewById(R.id.cameraBtn);
        cameraReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestRuntimePermission(CAMERA_PERMISSION);
            }
        });
    }
    private void requestRuntimePermission(String reqPermission){
        if(ActivityCompat.checkSelfPermission(this, reqPermission) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }else if(ActivityCompat.shouldShowRequestPermissionRationale(this, reqPermission)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("This App request permission for this and that")
                    .setTitle("Permission Required")
                    .setPositiveButton("Ok", ((dialog, which) -> {
                        ActivityCompat.requestPermissions(DashboardActivity.this, new String[]{reqPermission}, PERMISSION_REQ_CODE);

                        dialog.dismiss();
                    }))
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

            builder.show();

        }else{
            ActivityCompat.requestPermissions(this, new String[]{reqPermission}, PERMISSION_REQ_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PERMISSION_REQ_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }else if(!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])){

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("This feature is unavailable. Allow permission from setting to access this feature")
                        .setTitle("Permission Required")
                        .setCancelable(false)
                        .setNegativeButton("Cancel", ((dialog, which) -> dialog.dismiss()))
                        .setPositiveButton("Setting", (dialog, which) -> {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);

                            dialog.dismiss();
                        });

                builder.show();
            }else {
                requestRuntimePermission(permissions[0]);
            }
        }
    }
}