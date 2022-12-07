package com.example.bikehire;


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

import androidx.appcompat.app.AppCompatActivity;

import com.example.bikehire.Model.UserModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

public class UserRegistration extends AppCompatActivity {
    private TextInputEditText sendname,sendemail,sendaddress,sendpwd,sendcpwd,sendvehicleno,sendphoneno;
    private Button uregister,ulogin;
    private TextView show;
    String password=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sendname = (TextInputEditText) findViewById(R.id.u_name);
        sendemail = (TextInputEditText)findViewById(R.id.u_email);
        sendpwd = (TextInputEditText)findViewById(R.id.u_pwd);
        sendcpwd = (TextInputEditText)findViewById(R.id.u_cpwd);
        sendphoneno = (TextInputEditText)findViewById(R.id.u_phoneno);
        sendaddress = (TextInputEditText)findViewById(R.id.u_address);
        uregister=(Button)findViewById(R.id.u_register);
        ulogin=(Button)findViewById(R.id.u_login);
        show=findViewById(R.id.showpwd2);

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


        uregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModel userModel;
                DatabaseHelper DB = new DatabaseHelper(UserRegistration.this);
                String nm=sendname.getText().toString();
                String em=sendemail.getText().toString();
                String pd=sendpwd.getText().toString();
                String cpd=sendcpwd.getText().toString();
                String phn=sendphoneno.getText().toString();
                String add=sendaddress.getText().toString();

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
                } else{
                    if(pd.equals(cpd)){
                        Boolean checkuser = DB.checkuseremail(em);
                        if(checkuser==false){
                            try {
                                PasswordEncryption td = new PasswordEncryption();
                                password=td.encrypt(pd);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            userModel = new UserModel(-1, nm, em, password,phn,add,null);
                            boolean success = DB.addOneUser(userModel);
                            if(success==true){
                                Toast.makeText(UserRegistration.this, "Registered Successfully...!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(UserRegistration.this, UserLogin.class);
                                startActivity(i);
                            }else{
                                Toast.makeText(UserRegistration.this, "Registeration Failed...!", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(UserRegistration.this, "User Already Exists...!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        sendcpwd.setError("Password did not match...");
                    }
                }
            }
        });
        ulogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserRegistration.this, UserLogin.class);
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
        switch (id){
            case R.id.item1:
                Intent intent = new Intent(UserRegistration.this, HomePage.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
