package com.example.bikehire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bikehire.Model.CompanyModel;
import com.example.bikehire.Model.UserModel;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class CompanyRegistration extends AppCompatActivity {
    private TextInputEditText sendname, sendemail, sendaddress, sendpwd, sendcpwd, sendphoneno;
    private Button cregister, clogin;
    private TextView show;
    String password=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_registration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sendname = (TextInputEditText) findViewById(R.id.company_name);
        sendemail = (TextInputEditText) findViewById(R.id.company_email);
        sendpwd = (TextInputEditText) findViewById(R.id.company_pwd);
        sendcpwd = (TextInputEditText) findViewById(R.id.company_cpwd);
        sendphoneno = (TextInputEditText) findViewById(R.id.company_phoneno);
        sendaddress = (TextInputEditText) findViewById(R.id.company_address);
        cregister = (Button) findViewById(R.id.company_register);
        clogin = (Button) findViewById(R.id.company_tologin);
        show = findViewById(R.id.showpwd1);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String regdate = sdf.format(new Date());

        sendpwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // get the password when we start typing
                String password = sendpwd.getText().toString();
                validatepass(password);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });


        cregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompanyModel companyModel;
                DatabaseHelper DB = new DatabaseHelper(CompanyRegistration.this);
                String nm = sendname.getText().toString();
                String em = sendemail.getText().toString();
                String pd = sendpwd.getText().toString();
                String cpd = sendcpwd.getText().toString();
                String phn = sendphoneno.getText().toString();
                String add = sendaddress.getText().toString();

                if(TextUtils.isEmpty(nm)){
                    sendname.setError("Name is required");
                }if(TextUtils.isEmpty(em)){
                    sendemail.setError("Email is required");
                }if (TextUtils.isEmpty(cpd)){
                    sendcpwd.setError("Confirm Password is required");
                }if (TextUtils.isEmpty(phn)){
                    sendphoneno.setError("Phone No. is required");
                }if (TextUtils.isEmpty(add)){
                    sendaddress.setError("Address is required");
                }if(TextUtils.isEmpty(pd)){
                    sendpwd.setError("Password is required");
                }else if(sendpwd.length()<8){
                    sendpwd.setError("It should contain at least one capital case letter.\n" +
                            "It should contain at least one lower-case letter.\n" +
                            "It should contain at least one number.\n" +
                            "Length should be 8 characters.\n" +
                            "It should contain one of the following special characters: @, $, #, !.");
                }else {
                    if (pd.equals(cpd)) {
                        Boolean checkuser = DB.checkcompemail(em);
                        if (checkuser == false) {
                            try {
                                PasswordEncryption td = new PasswordEncryption();
                                password=td.encrypt(pd);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            companyModel = new CompanyModel(-1, nm, em, password, phn, add, regdate, null, 0, 3.5F);
                            boolean success = DB.addOneCompany(companyModel);
                            if (success == true) {
                                Toast.makeText(CompanyRegistration.this, "Registered Successfully...!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(CompanyRegistration.this, CompanyLogin.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(CompanyRegistration.this, "Registeration Failed...!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CompanyRegistration.this, "Company Already Exists...!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        sendcpwd.setError("Password did not match..");
                    }
                }
            }
        });
        clogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompanyRegistration.this, CompanyLogin.class);
                startActivity(intent);
            }

        });
    }

    public void validatepass(String password) {

        Pattern uppercase = Pattern.compile("[A-Z]");
        Pattern lowercase = Pattern.compile("[a-z]");
        Pattern digit = Pattern.compile("[0-9]");

        if (!lowercase.matcher(password).find()) {
            show.setText("Weak Password");
            show.setTextColor(Color.RED);
        } else {
            if (!uppercase.matcher(password).find()) {
                show.setText("Weak Password");
                show.setTextColor(Color.RED);
            } else {
                if (!digit.matcher(password).find()) {
                    show.setText("Medium Password");
                    show.setTextColor(0xFFFF9800);
                } else {
                    if (password.length() < 8) {
                        show.setText("Weak Password");
                        show.setTextColor(0xFFFF9800);
                    } else {
                        show.setText("Strong Password");
                        show.setTextColor(Color.GREEN);
                    }
                }
            }
        }
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
                Intent intent = new Intent(CompanyRegistration.this, HomePage.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
