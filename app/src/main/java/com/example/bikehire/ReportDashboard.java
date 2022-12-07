package com.example.bikehire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ReportDashboard extends AppCompatActivity {
    private TextView fullname,email;
    private ImageView bookingreport,companyreport,paymentreport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_dashboard);

        fullname = findViewById(R.id.adminname5);
        email = findViewById(R.id.adminemail5);
        bookingreport = findViewById(R.id.booking_report);
        companyreport = findViewById(R.id.company_report);
        paymentreport = findViewById(R.id.payment_report);

        Intent i = getIntent();
        String name = i.getStringExtra("name");
        String aemail = i.getStringExtra("email");
        fullname.setText(name);
        email.setText(aemail);

        bookingreport.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ReportDashboard.this, BookingReport.class);
                i.putExtra("name",name);
                i.putExtra("email",aemail);
                startActivity(i);
            }
        });
        companyreport.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ReportDashboard.this, CompanyReport.class);
                i.putExtra("name",name);
                i.putExtra("email",aemail);
                startActivity(i);
            }
        });
        paymentreport.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ReportDashboard.this, PaymentReport.class);
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
                Intent intent = new Intent(ReportDashboard.this,AdminDashboard.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                Intent intent1 = new Intent(ReportDashboard.this,AdminLogin.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}