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

import com.example.bikehire.Model.CompanyModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class CompanyProfileUpdate extends AppCompatActivity {
    private TextInputEditText fname,uemail,phoneno,address;
    private ImageView photo,select;
    private TextView name,email;
    private Button update;

    private static final int SELECT_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile_update);

        fname = findViewById(R.id.updatecomp1);
        uemail = findViewById(R.id.updatecomp2);
        phoneno = findViewById(R.id.updatecomp3);
        address = findViewById(R.id.updatecomp4);
        photo = findViewById(R.id.profilephoto_comp);
        name = findViewById(R.id.updatecompname);
        email = findViewById(R.id.updatecompemail);
        update = findViewById(R.id.updatecomp5);
        select = findViewById(R.id.compimgselect);


        Intent i = getIntent();
        String str = i.getStringExtra("id");
        String str1 = i.getStringExtra("fname");
        String str3 = i.getStringExtra("email");
        String pwd = i.getStringExtra("pwd");
        String str5 = i.getStringExtra("phoneno");
        String str6 = i.getStringExtra("address");
        String regdate = i.getStringExtra("date");
        Bitmap ph = i.getParcelableExtra("photo");
        String str7 = i.getStringExtra("isactive");
        boolean b = Boolean.parseBoolean(str7);
        int isact=(b)?1:0;

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
                CompanyModel companyModel;
                DatabaseHelper DB = new DatabaseHelper(CompanyProfileUpdate.this);
                List<CompanyModel> all = DB.viewCompany(str3);
                float rate = all.get(0).getComprating();

                int id = Integer.parseInt(str);
                String fnm = fname.getText().toString();
                String em = uemail.getText().toString();
                String phno = phoneno.getText().toString();
                String add = address.getText().toString();
                photo.buildDrawingCache();
                Bitmap sp = photo.getDrawingCache();

                companyModel = new CompanyModel(id,fnm,em,pwd,phno,add,regdate,sp,isact,rate);
                boolean success = DB.updateCompany(companyModel);
                if (success == true) {
                    Toast.makeText(CompanyProfileUpdate.this, "Company details Updated Successfully...!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(CompanyProfileUpdate.this,CompanyProfile.class);
                    startActivity(i);

                } else {
                    Toast.makeText(CompanyProfileUpdate.this, "Failed to Update Company details...!", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(CompanyProfileUpdate.this,CompanyDashboard.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                Intent intent1 = new Intent(CompanyProfileUpdate.this,CompanyLogin.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}