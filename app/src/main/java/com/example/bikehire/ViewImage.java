package com.example.bikehire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ablanco.zoomy.TapListener;
import com.ablanco.zoomy.Zoomy;

public class ViewImage extends AppCompatActivity {
private  ImageView zoomimg;
private Bitmap rc=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        zoomimg=findViewById(R.id.zoomimage);

        Intent intent = getIntent();
        rc = intent.getParcelableExtra("img");

        zoomimg.setImageBitmap(rc);

        Zoomy.Builder builder=new Zoomy.Builder(this)
                .target(zoomimg)
                .animateZooming(false)
                .enableImmersiveMode(false)
                .tapListener(new TapListener() {
                    @Override
                    public void onTap(View v) {
                    }
                });
        builder.register();
    }
}