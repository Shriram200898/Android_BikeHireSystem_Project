package com.example.bikehire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class CompanyResetPassword extends AppCompatActivity {
    private TextView email;
    private TextInputEditText pwd,cpwd;
    private Button reset;
    String password=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_reset_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        email = findViewById(R.id.txt4);
        pwd = findViewById(R.id.pwdcompany);
        cpwd = findViewById(R.id.cpwdcompany);
        reset = findViewById(R.id.reset3);

        Intent intent = getIntent();
        String str = intent.getStringExtra("email");
        email.setText("Your Email Address : \n"+str);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pd = pwd.getText().toString();
                String cpd = cpwd.getText().toString();
                DatabaseHelper DB = new DatabaseHelper(CompanyResetPassword.this);
                if(TextUtils.isEmpty(pd)){
                    pwd.setError("Password is required");
                }if(TextUtils.isEmpty(cpd)){
                    cpwd.setError("Confirm Password is required");
                }else {
                    if(pd.equals(cpd)){
                        try {
                            PasswordEncryption td = new PasswordEncryption();
                            password=td.encrypt(pd);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        boolean success = DB.updateCompanyPassword(str,password);
                        if (success == true) {
                            Toast.makeText(CompanyResetPassword.this, "Password Changed Successfully...!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(CompanyResetPassword.this, CompanyLogin.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(CompanyResetPassword.this, "Failed to Change Password...!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        cpwd.setError("Password did not match..");
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
                Intent intent = new Intent(CompanyResetPassword.this,HomePage.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}