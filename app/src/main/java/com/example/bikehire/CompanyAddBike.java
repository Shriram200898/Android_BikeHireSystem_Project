package com.example.bikehire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bikehire.Model.BikeModel;
import com.example.bikehire.Model.CategoryModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class CompanyAddBike extends AppCompatActivity {
    private TextInputEditText bregno,bname,hourrent,dayrent,weekrent,deposit,mileage,model,engine,desc;
    private ImageView bikeimg,bikercimg,compimg;
    private MaterialSpinner category,starttype,fueltype;
    private Button addbike,selectbikeimg,selectrc;
    private TextView fullname,email;
    private static final int SELECT_PICTURE = 1;
    private static final int SELECT_RC = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_add_bike);

        bregno=(TextInputEditText) findViewById(R.id.bikeRegNo);
        bname=(TextInputEditText) findViewById(R.id.bikeName);
        category=(MaterialSpinner) findViewById(R.id.bikeCategory);
        starttype=(MaterialSpinner) findViewById(R.id.bikestarttype);
        fueltype=(MaterialSpinner) findViewById(R.id.bikefueltype);
        hourrent=(TextInputEditText) findViewById(R.id.bikehourlyrent);
        dayrent=(TextInputEditText) findViewById(R.id.bikedailyrent);
        weekrent=(TextInputEditText) findViewById(R.id.bikeweeklyrent);
        model=(TextInputEditText) findViewById(R.id.bikeModel);
        engine=(TextInputEditText) findViewById(R.id.bikeengine);
        deposit=(TextInputEditText) findViewById(R.id.bikeDeposit);
        mileage=(TextInputEditText) findViewById(R.id.bikeMileage);
        desc=(TextInputEditText) findViewById(R.id.bikeDesc);
        bikeimg=(ImageView) findViewById(R.id.bikePicture);
        compimg=(ImageView) findViewById(R.id.imgcp1);
        bikercimg=(ImageView) findViewById(R.id.bikeuploadrc);
        addbike = (Button) findViewById(R.id.addBike);
        selectbikeimg = (Button) findViewById(R.id.select_bikePicture);
        selectrc = (Button) findViewById(R.id.selectrc);
        fullname = findViewById(R.id.cpname1);
        email = findViewById(R.id.cpemail1);



        Intent intent = getIntent();
        String str = intent.getStringExtra("id");
        String cemail = intent.getStringExtra("email");
        String str2 = intent.getStringExtra("name");
        Bitmap str3 = intent.getParcelableExtra("photo");
        email.setText(cemail);
        fullname.setText(str2);

        if(str3 == null){

        }
        else{
            compimg.setImageBitmap(str3);
        }
        selectbikeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageChooser();
            }
        });
        selectrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageChooser1();
            }
        });

        addbike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BikeModel bikeModel;
                DatabaseHelper DB = new DatabaseHelper(CompanyAddBike.this);

                String brgno=bregno.getText().toString();
                String bnm=bname.getText().toString();
                String cate=category.getSelectedItem().toString();
                double hrt=Double.parseDouble(hourrent.getText().toString());
                double drt=Double.parseDouble(dayrent.getText().toString());
                double wrt=Double.parseDouble(weekrent.getText().toString());
                double dep=Double.parseDouble(deposit.getText().toString());
                int mg=Integer.parseInt(mileage.getText().toString());
                String srtty=starttype.getSelectedItem().toString();
                String flty=fueltype.getSelectedItem().toString();
                String eng=engine.getText().toString();
                int mdl=Integer.parseInt(model.getText().toString());
                String pd=desc.getText().toString();

                bikeimg.buildDrawingCache();
                Bitmap bpic = bikeimg.getDrawingCache();
                bikercimg.buildDrawingCache();
                Bitmap brc = bikercimg.getDrawingCache();

                int company_id=DB.getCompanyId(cemail);

                if(TextUtils.isEmpty(brgno) || TextUtils.isEmpty(bnm) || TextUtils.isEmpty(cate) || TextUtils.isEmpty(String.valueOf(hrt)) || TextUtils.isEmpty(String.valueOf(drt)) || TextUtils.isEmpty(String.valueOf(wrt)) || TextUtils.isEmpty(String.valueOf(dep)) || TextUtils.isEmpty(pd) || TextUtils.isEmpty(String.valueOf(mg)) || TextUtils.isEmpty(String.valueOf(mdl)) || TextUtils.isEmpty(srtty) || TextUtils.isEmpty(flty) || TextUtils.isEmpty(eng) || bikeimg.getDrawable()==null || bikercimg.getDrawable()==null)
                    Toast.makeText(CompanyAddBike.this,"All fields Required..",Toast.LENGTH_SHORT).show();
                else{
                    bikeModel = new BikeModel(-1,brgno,company_id,bnm,cate,pd,srtty,eng,flty,hrt,drt,wrt,dep,1,mg,mdl,bpic,brc);
                    boolean success = DB.addBike(bikeModel);
                    if(success==true){
                        Toast.makeText(CompanyAddBike.this, "Bike Added Successfully...!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(CompanyAddBike.this,CompanyViewBike.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(CompanyAddBike.this, "Adding Bike Failed...!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        loadSpinnerData();

    }
    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }
    void imageChooser1() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_RC);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    bikeimg.setImageURI(selectedImageUri);
                }
            }
            if (requestCode == SELECT_RC) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    bikercimg.setImageURI(selectedImageUri);
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
                Intent intent = new Intent(CompanyAddBike.this,CompanyDashboard.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                Intent intent1 = new Intent(CompanyAddBike.this,CompanyLogin.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadSpinnerData() {
        String category_array[];
        DatabaseHelper dbhelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.CATEGORY_TABLE,null);
        category_array=new String[cursor.getCount()];
        cursor.moveToFirst();
        for(int i=0;i<category_array.length;i++)
        {
            category_array[i]=cursor.getString(1);
            cursor.moveToNext();
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, category_array);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(dataAdapter);
    }

}


