package com.example.bikehire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bikehire.Model.UserModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class UserProfile extends AppCompatActivity {
    private TextInputEditText name,email,phoneno,address;
    private TextView uname,uemail;
    private Button update;
    private ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        name = findViewById(R.id.usertxt1);
        email = findViewById(R.id.usertxt2);
        phoneno = findViewById(R.id.usertxt3);
        address = findViewById(R.id.usertxt4);
        uname = findViewById(R.id.name);
        uemail = findViewById(R.id.uemail);
        update = findViewById(R.id.updateu);
        photo =  findViewById(R.id.uprofile_photo);

        Intent i =getIntent();
        String str = i.getStringExtra("email");
        DatabaseHelper dbhelper = new DatabaseHelper(UserProfile.this);
        List<UserModel> all=dbhelper.viewAllUser(str);
        name.setText(all.get(0).getName());
        email.setText(all.get(0).getEmail());
        phoneno.setText(all.get(0).getPhoneno());
        address.setText(all.get(0).getAdd());
        Bitmap pt = all.get(0).getPhoto();
        if(pt == null){

        }
        else{
            photo.setImageBitmap(all.get(0).getPhoto());
        }

        uname.setText(all.get(0).getName());
        uemail.setText(all.get(0).getEmail());


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = all.get(0).getId();
                String str = String.valueOf(id);
                String str1 = all.get(0).getName();
                String str3 = email.getText().toString();
                String str7 = all.get(0).getPwd();
                String str5 = phoneno.getText().toString();
                String str6 = address.getText().toString();
                photo.buildDrawingCache();
                Bitmap img = photo.getDrawingCache();
                Intent i = new Intent(UserProfile.this,UserProfileUpdate.class);
                i.putExtra("id",str);
                i.putExtra("fname",str1);
                i.putExtra("email",str3);
                i.putExtra("phoneno",str5);
                i.putExtra("address",str6);
                i.putExtra("pwd",str7);
                i.putExtra("photo",img);
                startActivity(i);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item1:
                Intent intent = new Intent(UserProfile.this,UserDashboard.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                Intent intent1 = new Intent(UserProfile.this,UserLogin.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}