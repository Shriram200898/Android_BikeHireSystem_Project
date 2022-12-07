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

public class AdminForgetPassword extends AppCompatActivity {
    TextInputEditText adminEmail;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_forget_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        adminEmail = findViewById(R.id.admin_fgpwd_email);
        submit = findViewById(R.id.submit1);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseHelper DB = new DatabaseHelper(AdminForgetPassword.this);

                String em = adminEmail.getText().toString();
                if(TextUtils.isEmpty(em)){
                    adminEmail.setError("Email Id is required");
                }
                else {
                    Boolean checkadmin = DB.checkadminemail(em);
                    if (checkadmin == false) {
                        Toast.makeText(AdminForgetPassword.this, "Please enter registered email address..", Toast.LENGTH_SHORT).show();

                    } else {
                        Intent i = new Intent(AdminForgetPassword.this, AdminResetPassword.class);
                        i.putExtra("email", em);
                        startActivity(i);
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
        switch (id){
            case R.id.item1:
                Intent intent = new Intent(AdminForgetPassword.this,HomePage.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}