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

import com.example.bikehire.Model.CompanyModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class CompanyLogin extends AppCompatActivity {
    private TextInputEditText compemail,comppwd;
    private Button compregister,complogin,forgetpwd;
    String password=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        compemail = (TextInputEditText) findViewById(R.id.company_email);
        comppwd = (TextInputEditText) findViewById(R.id.company_pwd);
        compregister = (Button) findViewById(R.id.company_toregister);
        complogin= (Button) findViewById(R.id.company_login);
        forgetpwd= (Button) findViewById(R.id.company_fp);

        complogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DatabaseHelper DB = new DatabaseHelper(CompanyLogin.this);
                String uem=compemail.getText().toString();
                String upd=comppwd.getText().toString();

                if(TextUtils.isEmpty(uem)){
                    compemail.setError("Name is required");
                }if(TextUtils.isEmpty(upd)){
                    comppwd.setError("Password is required");
                }else{
                    try {
                        PasswordEncryption td = new PasswordEncryption();
                        password=td.encrypt(upd);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    boolean checkcompemailpassword = DB.checkcompemailpassword(uem,password);
                    List<CompanyModel> all = DB.viewCompany(uem);
                    int active= all.get(0).getIsactive();
                        if (checkcompemailpassword == true) {
                            if (active == 1) {
                                Toast.makeText(CompanyLogin.this, "Login Successful...", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), CompanyDashboard.class);
                                i.putExtra("email", uem);
                                startActivity(i);
                            } else {
                                Toast.makeText(CompanyLogin.this, "Company is not active...!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CompanyLogin.this, "Incorrect Username & Password...", Toast.LENGTH_SHORT).show();

                        }
                }
            }
        });

        compregister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompanyLogin.this, CompanyRegistration.class);
                startActivity(intent);
            }
        });

        forgetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompanyLogin.this, CompanyForgetPassword.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.help_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item1:
                Intent intent = new Intent(CompanyLogin.this,HomePage.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}