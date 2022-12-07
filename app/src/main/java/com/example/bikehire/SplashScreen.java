package com.example.bikehire;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_SCREEN=3000;

    private Animation topanim,bottomanim;
    private ImageView image;
    private TextView logo,slogan;
    private ConstraintLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash_screen);


        topanim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomanim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        image = findViewById(R.id.appimage);
        logo = findViewById(R.id.appname);
        slogan=findViewById(R.id.tagline);
        layout=findViewById(R.id.constraint_layout);

        image.setAnimation(topanim);
        logo.setAnimation(bottomanim);
        slogan.setAnimation(bottomanim);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SplashScreen.this,HomePage.class);
                startActivity(i);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, HomePage.class);
                Pair[] pairs = new Pair[2];
                pairs[0]= new Pair<View,String>(image,"logo_image");
                pairs[1] = new Pair<View,String>(logo,"logo_text");

                ActivityOptions option = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    option = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this,pairs);
                }
                startActivity(intent,option.toBundle());
            }
        },SPLASH_SCREEN);
    }
}