package com.example.bikehire;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.example.bikehire.Model.CategoryModel;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AdminAddCategory extends AppCompatActivity {
    private TextInputEditText ctname,ctdesc;
    private ImageView ctpicture;
    private TextView fullname,email;
    private Button addct,select;

    private static final int SELECT_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_category);

        ctname=(TextInputEditText) findViewById(R.id.update_ctname);
        ctdesc=(TextInputEditText) findViewById(R.id.update_ctdesc);
        ctpicture=(ImageView) findViewById(R.id.updatectpicture);
        addct = (Button) findViewById(R.id.updatecategory);
        select = (Button) findViewById(R.id.select_ctpicture);
        fullname = findViewById(R.id.adminname2);
        email = findViewById(R.id.adminemail2);

        Intent i = getIntent();
        String name = i.getStringExtra("name");
        String aemail = i.getStringExtra("email");
        fullname.setText(name);
        email.setText(aemail);

        select.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        imageChooser();
        }
        });

        addct.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        CategoryModel categoryModel;
        DatabaseHelper DB = new DatabaseHelper(AdminAddCategory.this);
        String ctnm=ctname.getText().toString();
        String ctdes=ctdesc.getText().toString();
        ctpicture.buildDrawingCache();
        Bitmap sp = ctpicture.getDrawingCache();



        if(TextUtils.isEmpty(ctnm) || TextUtils.isEmpty(ctdes) || ctpicture.getDrawable()==null)
        Toast.makeText(AdminAddCategory.this,"All fields Required..",Toast.LENGTH_SHORT).show();
        else{
        Boolean checkservice = DB.checkCategory(ctnm);
        if(checkservice==false){
        categoryModel = new CategoryModel(-1, ctnm,ctdes,sp);
        boolean success = DB.addCategory(categoryModel);
        if(success==true){
        Toast.makeText(AdminAddCategory.this, "Category Added Successfully...!", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(AdminAddCategory.this,AdminCategory.class);
        startActivity(i);
        }else{
        Toast.makeText(AdminAddCategory.this, "Adding Category Failed...!", Toast.LENGTH_SHORT).show();
        }
        }else {
        Toast.makeText(AdminAddCategory.this, "Service Already Exists...!", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(AdminAddCategory.this,AdminCategory.class);
        startActivity(i);
        }
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
        ctpicture.setImageURI(selectedImageUri);
        }
        }
        }
        }
@Override
public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
        }

@Override
public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
        case R.id.item1:
        Intent intent = new Intent(AdminAddCategory.this,AdminDashboard.class);
        startActivity(intent);
        return true;
        case R.id.item2:
        Intent intent1 = new Intent(AdminAddCategory.this,AdminLogin.class);
        startActivity(intent1);
        return true;
default:
        return super.onOptionsItemSelected(item);
        }
        }

        }