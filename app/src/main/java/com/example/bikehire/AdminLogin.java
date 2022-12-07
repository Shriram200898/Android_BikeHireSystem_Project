package com.example.bikehire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class AdminLogin extends AppCompatActivity {
    private TextInputEditText adminemail,adminpwd;
    private Button adminlogin,forgetpwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        adminemail = (TextInputEditText) findViewById(R.id.admin_email_login);
        adminpwd = (TextInputEditText) findViewById(R.id.admin_pwd_login);
        adminlogin= (Button) findViewById(R.id.admin_login);
        forgetpwd= (Button) findViewById(R.id.admin_forgetpwd);

        forgetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminLogin.this, AdminForgetPassword.class);
                startActivity(intent);
            }
        });

        adminlogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DatabaseHelper DB = new DatabaseHelper(AdminLogin.this);
                String aem=adminemail.getText().toString();
                String apd=adminpwd.getText().toString();

                if(TextUtils.isEmpty(aem)){
                    adminemail.setError("Name is required");
                }if(TextUtils.isEmpty(apd)){
                    adminpwd.setError("Password is required");
                }else{
                    boolean checkadminemailpassword = DB.checkadminemailpassword(aem,apd);
                    if(checkadminemailpassword==true){
                        Toast.makeText(AdminLogin.this,"Login Successful...",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), AdminDashboard.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(AdminLogin.this,"Incorrect Username & Password...",Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.help_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item1:
                Intent intent = new Intent(AdminLogin.this, HomePage.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}