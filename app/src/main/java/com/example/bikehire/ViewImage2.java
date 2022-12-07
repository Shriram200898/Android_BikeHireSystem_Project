package com.example.bikehire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ablanco.zoomy.TapListener;
import com.ablanco.zoomy.Zoomy;

public class ViewImage2 extends AppCompatActivity {
    private ImageView zoomimg;
    private Bitmap rc=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image2);

        zoomimg=findViewById(R.id.zoomimage1);

        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray("dpimg");

        rc = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

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