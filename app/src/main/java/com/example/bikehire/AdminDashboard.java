package com.example.bikehire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bikehire.Model.AdminModel;

import java.util.List;

public class AdminDashboard extends AppCompatActivity {
    ImageView category,company,report;
    TextView fullname,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        fullname = findViewById(R.id.adminname);
        email = findViewById(R.id.adminemail);

        category=(ImageView) findViewById(R.id.admin_category);
        company=(ImageView) findViewById(R.id.admin_company);
        report=(ImageView) findViewById(R.id.admin_report);


        DatabaseHelper dbhelper = new DatabaseHelper(AdminDashboard.this);
        List<AdminModel> all=dbhelper.viewAdminDetails();
        String name = all.get(0).getAdminName();
        String aemail = all.get(0).getAdminEmail();
        fullname.setText(name);
        email.setText(aemail);


        category.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminDashboard.this, AdminCategory.class);
                i.putExtra("name",name);
                i.putExtra("email",aemail);
                startActivity(i);
            }
        });

        company.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminDashboard.this, AdminViewCompany.class);
                i.putExtra("name",name);
                i.putExtra("email",aemail);
                startActivity(i);
            }
        });


        report.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminDashboard.this, ReportDashboard.class);
                i.putExtra("name",name);
                i.putExtra("email",aemail);
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
                Intent intent = new Intent(AdminDashboard.this,AdminDashboard.class);
                startActivity(intent);
                return true;

            case R.id.logout:
                Intent intent1 = new Intent(AdminDashboard.this,AdminLogin.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}