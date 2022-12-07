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

import com.example.bikehire.Model.CategoryModel;
import com.google.android.material.textfield.TextInputEditText;

public class AdminUpdateCategory extends AppCompatActivity {
    private TextInputEditText ct_name,ct_desc;
    private ImageView ct_picture;
    private TextView fullname,email;
    private Button selectpic,ct_update;

    private static final int SELECT_PICTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_category);

        ct_name = (TextInputEditText) findViewById(R.id.update_catename);
        ct_desc = (TextInputEditText) findViewById(R.id.update_catedesc);
        ct_picture = (ImageView) findViewById(R.id.updatepicture);
        selectpic = (Button) findViewById(R.id.select_catepicture);
        ct_update = (Button) findViewById(R.id.updatecategory);
        fullname = findViewById(R.id.adminname11);
        email = findViewById(R.id.adminemail11);


        selectpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        Intent intent = getIntent();
        String str = intent.getStringExtra("id");
        String str1 = intent.getStringExtra("name");
        String str3 = intent.getStringExtra("desc");
        Bitmap str4 = intent.getParcelableExtra("pic");
        String name = intent.getStringExtra("adminname");
        String aemail = intent.getStringExtra("adminemail");
        fullname.setText(name);
        email.setText(aemail);
        ct_name.setText(str1);
        ct_desc.setText(str3);
        ct_picture.setImageBitmap(str4);

        ct_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryModel categoryModel;
                DatabaseHelper DB = new DatabaseHelper(AdminUpdateCategory.this);

                String snm = ct_name.getText().toString();
                String sdes = ct_desc.getText().toString();
                ct_picture.buildDrawingCache();
                Bitmap sp = ct_picture.getDrawingCache();

                int id = Integer.parseInt(String.valueOf(str));
                categoryModel = new CategoryModel(id, snm, sdes, sp);
                boolean success = DB.updateCategory(categoryModel);
                if (success == true) {
                    Toast.makeText(AdminUpdateCategory.this, "Category Updated Successfully...!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AdminUpdateCategory.this,AdminCategory.class);
                    startActivity(i);

                } else {
                    Toast.makeText(AdminUpdateCategory.this, "Category Updation Failed...!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    void imageChooser() {

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
                    ct_picture.setImageURI(selectedImageUri);
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
                Intent intent = new Intent(AdminUpdateCategory.this,AdminDashboard.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                Intent intent1 = new Intent(AdminUpdateCategory.this,AdminLogin.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}