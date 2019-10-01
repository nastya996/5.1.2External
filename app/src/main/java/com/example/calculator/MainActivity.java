package com.example.calculator;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.ImageView;

import android.widget.Toast;

import java.io.File;

public class MainActivity extends Activity {
    public static final String IMAGE = "Image";
    private ImageView BackGraund;
    public static SharedPreferences sharedPreferences;

    private Button button_setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onOperationClick(View view) {
        View view1 = (View) findViewById(R.id.vis_panel);

        if (view1.getVisibility() == View.GONE) {
            view1.setVisibility(View.VISIBLE);

        } else {
            view1.setVisibility(View.GONE);
        }
    }

    public void onClickSetting(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivityForResult(intent, 20);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            return;
        }

        File myFile = new File(sharedPreferences.getString(IMAGE, ""));
        if (myFile.exists()) {

            Bitmap mybitmap = BitmapFactory.decodeFile(myFile.getAbsolutePath());

            BackGraund.setImageBitmap(mybitmap);

            Toast.makeText(MainActivity.this, "Image " + myFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Not image", Toast.LENGTH_SHORT).show();
        }

        recreate();


    }
}

