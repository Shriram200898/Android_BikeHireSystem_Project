package com.example.bikehire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bikehire.Model.CompanyModel;

import java.util.List;

public class CompanyDashboard extends AppCompatActivity {
    private TextView email,name;
    private ImageView profile,viewbooking,bikes,report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_dashboard);
        email = findViewById(R.id.cpemail);
        name = findViewById(R.id.cpname);
        profile = findViewById(R.id.imgcp);
        viewbooking = findViewById(R.id.cpview_booking);
        bikes = findViewById(R.id.cpadd_bike);
        report=findViewById(R.id.cpview_bookingreport);

        Intent i = getIntent();
        String str = i.getStringExtra("email");
        email.setText(str);

        DatabaseHelper dbhelper = new DatabaseHelper(CompanyDashboard.this);
        List<CompanyModel> all=dbhelper.viewCompany(str);
        int id = all.get(0).getCompid();
        String mid = String.valueOf(id);
        String str1 = all.get(0).getCompname();
        name.setText(str1);
        Bitmap pt = all.get(0).getCompprofileimage();
        if(pt == null){

        }
        else{
            profile.setImageBitmap(all.get(0).getCompprofileimage());
        }
       profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CompanyDashboard.this,CompanyProfile.class);
                i.putExtra("email",str);
                startActivity(i);
            }
        });
       bikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CompanyDashboard.this,CompanyViewBike.class);
                i.putExtra("id",mid);
                i.putExtra("name",str1);
                i.putExtra("email",str);
                i.putExtra("photo",pt);
                startActivity(i);
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CompanyDashboard.this,CompanyBookingReport.class);
                i.putExtra("id",mid);
                i.putExtra("name",str1);
                i.putExtra("email",str);
                i.putExtra("photo",pt);
                startActivity(i);
            }
        });
        viewbooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CompanyDashboard.this,CompanyViewBooking.class);
                i.putExtra("id",mid);
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
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item1:
                Intent intent = new Intent(CompanyDashboard.this,CompanyDashboard.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                Intent intent1 = new Intent(CompanyDashboard.this,CompanyLogin.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}