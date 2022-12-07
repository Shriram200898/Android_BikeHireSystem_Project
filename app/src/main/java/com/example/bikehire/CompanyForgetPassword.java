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

public class CompanyForgetPassword extends AppCompatActivity {
    private TextInputEditText companyEmail;
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_forget_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        companyEmail = findViewById(R.id.email_company);
        submit = findViewById(R.id.submit3);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseHelper DB = new DatabaseHelper(CompanyForgetPassword.this);

                String em = companyEmail.getText().toString();
                if(TextUtils.isEmpty(em)){
                    companyEmail.setError("Email Id is required");
                }
                else {
                    Boolean checkcomp = DB.checkcompemail(em);
                    if (checkcomp == false) {
                        Toast.makeText(CompanyForgetPassword.this, "Please enter registered email address..", Toast.LENGTH_SHORT).show();

                    } else {
                        Intent i = new Intent(CompanyForgetPassword.this, CompanyResetPassword.class);
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
                Intent intent = new Intent(CompanyForgetPassword.this,HomePage.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}