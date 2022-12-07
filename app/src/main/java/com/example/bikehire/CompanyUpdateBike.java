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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bikehire.Model.BikeModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class CompanyUpdateBike extends AppCompatActivity {
    private TextInputEditText bregno,bname,hourrent,dayrent,weekrent,deposit,mileage,model,engine,desc;
    private ImageView bikeimg,bikercimg,compimg;
    private MaterialSpinner category,starttype,fueltype;
    private Button updatebike,selectbikeimg,selectrc;
    private TextView fullname,email;
    private static final int SELECT_PICTURE = 1;
    private static final int SELECT_RC = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_update_bike);

        bregno=(TextInputEditText) findViewById(R.id.updatebike_bikeRegNo);
        bname=(TextInputEditText) findViewById(R.id.updatebike_bikeName);
        category=(MaterialSpinner) findViewById(R.id.updatebike_bikeCategory);
        starttype=(MaterialSpinner) findViewById(R.id.updatebike_bikestarttype);
        fueltype=(MaterialSpinner) findViewById(R.id.updatebike_bikefueltype);
        hourrent=(TextInputEditText) findViewById(R.id.updatebike_bikehourlyrent);
        dayrent=(TextInputEditText) findViewById(R.id.updatebike_bikedailyrent);
        weekrent=(TextInputEditText) findViewById(R.id.updatebike_bikeweeklyrent);
        model=(TextInputEditText) findViewById(R.id.updatebike_bikeModel);
        engine=(TextInputEditText) findViewById(R.id.updatebike_bikeengine);
        deposit=(TextInputEditText) findViewById(R.id.updatebike_bikeDeposit);
        mileage=(TextInputEditText) findViewById(R.id.updatebike_bikeMileage);
        desc=(TextInputEditText) findViewById(R.id.updatebike_bikeDesc);
        bikeimg=(ImageView) findViewById(R.id.updatebike_bikePicture);
        compimg=(ImageView) findViewById(R.id.imgcp3);
        bikercimg=(ImageView) findViewById(R.id.updatebike_uploadrc);
        updatebike = (Button) findViewById(R.id.updatebike_Bike);
        selectbikeimg = (Button) findViewById(R.id.updatebike_selecticture);
        selectrc = (Button) findViewById(R.id.updatebike_selectrc);
        fullname = findViewById(R.id.cpname3);
        email = findViewById(R.id.cpemail3);

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


        Intent intent = getIntent();
        String str = intent.getStringExtra("id");
        int bikeid=Integer.parseInt(str);
        String name = intent.getStringExtra("companyname");
        String aemail = intent.getStringExtra("companyemail");
        Bitmap str3 = intent.getParcelableExtra("photo");
        fullname.setText(name);
        email.setText(aemail);
        if (str3 == null) {

        } else {
            compimg.setImageBitmap(str3);
        }

        DatabaseHelper db = new DatabaseHelper(CompanyUpdateBike.this);
        List<BikeModel> all=db.viewBike(bikeid);

        bregno.setText(all.get(0).getBikeregno());
        bname.setText(all.get(0).getBikename());
        hourrent.setText(""+all.get(0).getBikehourrent());
        dayrent.setText(""+all.get(0).getBikedayrent());
        weekrent.setText(""+all.get(0).getBikeweekrent());
        deposit.setText(""+all.get(0).getDeposit());
        mileage.setText(""+all.get(0).getMileage());
        desc.setText(all.get(0).getDescription());
        model.setText(""+all.get(0).getModel());
        engine.setText(all.get(0).getEngine());
        bikeimg.setImageBitmap(all.get(0).getBikeimage());
        bikercimg.setImageBitmap(all.get(0).getBikerc());

        updatebike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BikeModel bikeModel;
                DatabaseHelper DB = new DatabaseHelper(CompanyUpdateBike.this);
                int id = all.get(0).getBikeNo();
                int cid= all.get(0).getCompanyid();
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

                if(TextUtils.isEmpty(brgno) || TextUtils.isEmpty(bnm) || TextUtils.isEmpty(cate) || TextUtils.isEmpty(String.valueOf(hrt)) || TextUtils.isEmpty(String.valueOf(drt)) || TextUtils.isEmpty(String.valueOf(wrt)) || TextUtils.isEmpty(String.valueOf(dep)) || TextUtils.isEmpty(pd) || TextUtils.isEmpty(String.valueOf(mg)) || TextUtils.isEmpty(String.valueOf(mdl)) || TextUtils.isEmpty(srtty) || TextUtils.isEmpty(flty) || TextUtils.isEmpty(eng) || bikeimg.getDrawable()==null || bikercimg.getDrawable()==null)
                    Toast.makeText(CompanyUpdateBike.this,"All fields Required..",Toast.LENGTH_SHORT).show();
                else {
                    bikeModel = new BikeModel(id, brgno, cid, bnm, cate, pd, srtty, eng, flty, hrt, drt, wrt, dep, 1, mg, mdl, bpic, brc);
                    boolean success = DB.updateBike(bikeModel);
                    if (success == true) {
                        Toast.makeText(CompanyUpdateBike.this, "Bike Updated Successfully...!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(CompanyUpdateBike.this, CompanyViewBike.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(CompanyUpdateBike.this, "Bike Updation Failed...!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        loadSpinnerData();

    }
    void imageChooser() {

        // create an instance of the
        // intent of the type image
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
                Intent intent = new Intent(CompanyUpdateBike.this,CompanyDashboard.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                Intent intent1 = new Intent(CompanyUpdateBike.this,CompanyLogin.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadSpinnerData() {
        // database handler
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
        // Spinner Drop down elements

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, category_array);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        category.setAdapter(dataAdapter);
    }


}