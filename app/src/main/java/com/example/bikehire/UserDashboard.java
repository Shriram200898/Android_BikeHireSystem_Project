package com.example.bikehire;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bikehire.Model.UserModel;

import java.util.List;

public class UserDashboard extends AppCompatActivity {
    private TextView email,name;
    private ImageView profile,bikes,booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        email = findViewById(R.id.useremail);
        name = findViewById(R.id.username);
        profile = findViewById(R.id.imageuser);
        bikes = findViewById(R.id.user_bikes);
        booking= findViewById(R.id.user_booking);


        Intent i = getIntent();
        String str = i.getStringExtra("email");
        email.setText(str);

        DatabaseHelper dbhelper = new DatabaseHelper(UserDashboard.this);
        List<UserModel> all=dbhelper.viewAllUser(str);
        int userid = all.get(0).getId();
        String usid = String.valueOf(userid);
        name.setText(all.get(0).getName());
        String str1 = all.get(0).getName();
        Bitmap pt = all.get(0).getPhoto();
        if(pt == null){

        }
        else{
            profile.setImageBitmap(all.get(0).getPhoto());
        }

       profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserDashboard.this, UserProfile.class);
                i.putExtra("email",str);
                startActivity(i);
            }
        });

        bikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserDashboard.this,UserViewBikeCategory.class);
                i.putExtra("userid",usid);
                i.putExtra("name",str1);
                i.putExtra("email",str);
                i.putExtra("photo",pt);
                startActivity(i);
            }
        });

        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserDashboard.this,UserViewBooking.class);
                i.putExtra("userid",usid);
                i.putExtra("email",str);
                i.putExtra("name",str1);
                i.putExtra("photo",pt);
                startActivity(i);
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout_help_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item1:
                Intent intent = new Intent(UserDashboard.this,UserDashboard.class);
                startActivity(intent);
                return true;
            case R.id.item2:
                Intent intent1 = new Intent(UserDashboard.this,UserLogin.class);
                startActivity(intent1);
                return true;
            case R.id.help:
                Intent intent2 = new Intent(UserDashboard.this,ChatBot.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}