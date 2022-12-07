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

import com.example.bikehire.Model.CompanyModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class CompanyProfile extends AppCompatActivity {
    private TextInputEditText name,email,phoneno,address;
    private TextView cname,cemail;
    private Button update;
    private ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);

        name = findViewById(R.id.comptxt1);
        email = findViewById(R.id.comptxt2);
        phoneno = findViewById(R.id.comptxt3);
        address = findViewById(R.id.comptxt4);
        cname = findViewById(R.id.compname);
        cemail = findViewById(R.id.compemail);
        update = findViewById(R.id.compupdate);
        photo =  findViewById(R.id.compprofile_photo);

        Intent i =getIntent();
        String str = i.getStringExtra("email");
        DatabaseHelper dbhelper = new DatabaseHelper(CompanyProfile.this);
        List<CompanyModel> all=dbhelper.viewCompany(str);
        name.setText(all.get(0).getCompname());
        email.setText(all.get(0).getCompemail());
        phoneno.setText(all.get(0).getCompphoneno());
        address.setText(all.get(0).getCompaddress());
        Bitmap pt = all.get(0).getCompprofileimage();
        if(pt == null){

        }
        else{
            photo.setImageBitmap(all.get(0).getCompprofileimage());
        }

        cname.setText(all.get(0).getCompname());
        cemail.setText(all.get(0).getCompemail());


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = all.get(0).getCompid();
                String str = String.valueOf(id);
                String str1 = all.get(0).getCompname();
                String str3 = email.getText().toString();
                String str4 = all.get(0).getComppwd();
                String str5 = phoneno.getText().toString();
                String str6 = address.getText().toString();
                String str8 = all.get(0).getRegDate();

                int b = all.get(0).getIsactive();
                String str7 = String.valueOf(b);

                photo.buildDrawingCache();
                Bitmap img = photo.getDrawingCache();
                Intent i = new Intent(CompanyProfile.this,CompanyProfileUpdate.class);
                i.putExtra("id",str);
                i.putExtra("fname",str1);
                i.putExtra("email",str3);
                i.putExtra("pwd",str4);
                i.putExtra("phoneno",str5);
                i.putExtra("address",str6);
                i.putExtra("date",str8);
                i.putExtra("photo",img);
                i.putExtra("isactive",str7);
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
                Intent intent = new Intent(CompanyProfile.this,CompanyDashboard.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                Intent intent1 = new Intent(CompanyProfile.this,CompanyLogin.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}