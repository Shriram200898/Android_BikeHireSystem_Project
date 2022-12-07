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

public class AdminResetPassword extends AppCompatActivity {
    private TextView email;
    private TextInputEditText pwd,cpwd;
    private Button reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reset_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        email = findViewById(R.id.txt4);
        pwd = findViewById(R.id.pwdadmin_fgpwd);
        cpwd = findViewById(R.id.cpwdadmin_fgpwd);
        reset = findViewById(R.id.reset1);

        Intent intent = getIntent();
        String str = intent.getStringExtra("email");
        email.setText("Your Email Address : \n"+str);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pd = pwd.getText().toString();
                String cpd = cpwd.getText().toString();
                DatabaseHelper DB = new DatabaseHelper(AdminResetPassword.this);
                if(TextUtils.isEmpty(pd)){
                    pwd.setError("Password is required");
                }if(TextUtils.isEmpty(cpd)){
                    cpwd.setError("Confirm Password is required");
                }
                else {
                    if(pd.equals(cpd)){
                        boolean success = DB.updateAdminPassword(str,pd);
                        if (success == true) {
                            Toast.makeText(AdminResetPassword.this, "Password Changed Successfully...!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(AdminResetPassword.this, AdminLogin.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(AdminResetPassword.this, "Failed to Change Password...!", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(AdminResetPassword.this,HomePage.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}