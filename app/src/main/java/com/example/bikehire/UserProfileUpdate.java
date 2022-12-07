package com.example.bikehire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bikehire.Model.UserModel;
import com.google.android.material.textfield.TextInputEditText;

public class UserProfileUpdate extends AppCompatActivity {
    private TextInputEditText fname,uemail,phoneno,address;
    private ImageView photo,select;
    private TextView name,email;
    private Button update;

    private static final int SELECT_PICTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_update);

        fname = findViewById(R.id.updateuser1);
        uemail = findViewById(R.id.updateuser2);
        phoneno = findViewById(R.id.updateuser3);
        address = findViewById(R.id.updateuser4);
        photo = findViewById(R.id.userprofile_photo);
        name = findViewById(R.id.updateusername);
        email = findViewById(R.id.updateuseremail);
        update = findViewById(R.id.updateuser);
        select = findViewById(R.id.userimgselect);


        Intent i = getIntent();
        String str = i.getStringExtra("id");
        String str1 = i.getStringExtra("fname");
        String str3 = i.getStringExtra("email");
        String str5 = i.getStringExtra("phoneno");
        String str6 = i.getStringExtra("address");
        String pwd = i.getStringExtra("pwd");
        Bitmap ph = i.getParcelableExtra("photo");


        fname.setText(str1);
        uemail.setText(str3);
        phoneno.setText(str5);
        address.setText(str6);
        name.setText(str1);
        email.setText(str3);
        photo.setImageBitmap(ph);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModel userModel;
                DatabaseHelper DB = new DatabaseHelper(UserProfileUpdate.this);

                int id = Integer.parseInt(str);
                String fnm = fname.getText().toString();
                String em = uemail.getText().toString();
                String phno = phoneno.getText().toString();
                String add = address.getText().toString();
                photo.buildDrawingCache();
                Bitmap sp = photo.getDrawingCache();

                userModel = new UserModel(id,fnm,em,pwd,phno,add,sp);
                boolean success = DB.updateUser(userModel);
                if (success == true) {
                    Toast.makeText(UserProfileUpdate.this, "User details Updated Successfully...!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(UserProfileUpdate.this, UserProfile.class);
                    startActivity(i);

                } else {
                    Toast.makeText(UserProfileUpdate.this, "Failed to Update User details...!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    photo.setImageURI(selectedImageUri);
                }
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item1:
                Intent intent = new Intent(UserProfileUpdate.this,UserDashboard.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                Intent intent1 = new Intent(UserProfileUpdate.this,UserLogin.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}