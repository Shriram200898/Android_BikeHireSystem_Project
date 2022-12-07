package com.example.bikehire;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class UserLogin extends AppCompatActivity {
    private TextInputEditText useremail,userpwd;
    private Button userregister,userlogin,forgetpwd;
    String password=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        useremail = (TextInputEditText) findViewById(R.id.user_email);
        userpwd = (TextInputEditText) findViewById(R.id.user_pwd);
        userregister = (Button) findViewById(R.id.user_register);
        userlogin= (Button) findViewById(R.id.user_login);
        forgetpwd= (Button) findViewById(R.id.fp1);

        userlogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DatabaseHelper DB = new DatabaseHelper(UserLogin.this);
                String uem=useremail.getText().toString();
                String upd=userpwd.getText().toString();

                if(TextUtils.isEmpty(uem)){
                    useremail.setError("Name is required");
                }if(TextUtils.isEmpty(upd)){
                    userpwd.setError("Password is required");
                }else{
                    try {
                        PasswordEncryption td = new PasswordEncryption();
                        password=td.encrypt(upd);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    boolean checkuseremailpassword = DB.checkuseremailpassword(uem,password);
                    if(checkuseremailpassword==true){
                        Toast.makeText(UserLogin.this,"Login Successful...",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), UserDashboard.class);
                        i.putExtra("email",uem);
                        startActivity(i);
                    }else{
                        Toast.makeText(UserLogin.this,"Incorrect Username & Password...",Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        userregister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLogin.this, UserRegistration.class);
                startActivity(intent);
            }
        });

        forgetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLogin.this, UserForgetPassword.class);
                startActivity(intent);
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
                Intent intent = new Intent(UserLogin.this, HomePage.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}