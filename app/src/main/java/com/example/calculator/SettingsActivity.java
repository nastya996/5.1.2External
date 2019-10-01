package com.example.calculator;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelUuid;
import android.text.method.HideReturnsTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceFragmentCompat;

import java.io.File;
import java.io.FileNotFoundException;

public class SettingsActivity extends AppCompatActivity {
    EditText editText;
    ImageView e;

    public final String[] EXTERNAL_PERMS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public final int EXTERNAL_REQUEST = 138;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        editText = findViewById(R.id.editText);

    }

    public void onClick(View view) throws FileNotFoundException {
        requestForPermission();

        String fileName = editText.getText().toString();
        String sdcardPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() ;

        File sdFile = new File(sdcardPath);
        File[] files = sdFile.listFiles();
        for (File localFile : files) {
            if (localFile.getName().equals(fileName)) {

                SharedPreferences.Editor myEditor = MainActivity.sharedPreferences.edit();
                myEditor.putString(MainActivity.IMAGE, localFile.getAbsolutePath());
                myEditor.apply();
                Intent intent = new Intent();
                intent.putExtra("ImageFileName", sdcardPath + "/" + fileName);
                setResult(RESULT_OK, intent);
                Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "file_not_error", Toast.LENGTH_SHORT).show();
            }

        }

    }

    public boolean requestForPermission() {

        boolean isPermissionOn = true;
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            if (!canAccessExternalSd()) {
                isPermissionOn = false;
                requestPermissions(EXTERNAL_PERMS, EXTERNAL_REQUEST);
            }
        }

        return isPermissionOn;
    }

    public boolean canAccessExternalSd() {
        return (hasPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE));
    }

    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, perm));

    }
}
