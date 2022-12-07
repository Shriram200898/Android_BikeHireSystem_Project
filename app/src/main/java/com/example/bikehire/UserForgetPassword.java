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

public class UserForgetPassword extends AppCompatActivity {
    private TextInputEditText userEmail;
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_forget_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userEmail = findViewById(R.id.email_user);
        submit = findViewById(R.id.submit2);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseHelper DB = new DatabaseHelper(UserForgetPassword.this);

                String em = userEmail.getText().toString();
                if(TextUtils.isEmpty(em)){
                    userEmail.setError("Email Id is required");
                }
                else {
                    Boolean checkuser = DB.checkuseremail(em);
                    if (checkuser == false) {
                        Toast.makeText(UserForgetPassword.this, "Please enter registered email address..", Toast.LENGTH_SHORT).show();

                    } else {
                        Intent i = new Intent(UserForgetPassword.this, UserResetPassword.class);
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
                Intent intent = new Intent(UserForgetPassword.this,HomePage.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}