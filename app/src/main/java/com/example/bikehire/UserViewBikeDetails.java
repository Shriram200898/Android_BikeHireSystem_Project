package com.example.bikehire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bikehire.Model.BikeModel;

import java.util.List;

public class UserViewBikeDetails extends AppCompatActivity {

    private ImageView img,viewbimg,viewrc;
    private TextView fullname,email,viewbname,viewbcompany,viewbregno,viewhrent,viewdrent,viewwrent,viewbdeposit,viewbmileage,viewbengine,viewbstarttype,viewbmodel,viewbfueltype,viewtnc;
    private Button viewrcbtn,viewbookbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_bike_details);

        img=findViewById(R.id.imgcp6);
        fullname=findViewById(R.id.cpname6);
        email=findViewById(R.id.cpemail6);
        viewbimg=findViewById(R.id.bike_img);
        viewrc=findViewById(R.id.view_bikerc);
        viewbname=findViewById(R.id.bike_name);
        viewbcompany=findViewById(R.id.bike_companyname);
        viewbregno=findViewById(R.id.bike_regno);
        viewhrent=findViewById(R.id.bike_hourrent);
        viewdrent=findViewById(R.id.bike_dayrent);
        viewwrent=findViewById(R.id.bike_weekrent);
        viewbdeposit=findViewById(R.id.bike_deposit);
        viewbmileage=findViewById(R.id.bike_mileage);
        viewbengine=findViewById(R.id.bike_engine);
        viewbstarttype=findViewById(R.id.bike_starttype);
        viewbmodel=findViewById(R.id.bike_model);
        viewbfueltype=findViewById(R.id.bike_fueltype);
        viewtnc=findViewById(R.id.termsandconditions);
        viewrcbtn=findViewById(R.id.viewRCImg);
        viewbookbtn=findViewById(R.id.bike_book);

        Intent intent = getIntent();
        String str = intent.getStringExtra("userid");
        String str2 = intent.getStringExtra("name");
        String str1 = intent.getStringExtra("email");
        Bitmap str3 = intent.getParcelableExtra("photo");
        String str4 = intent.getStringExtra("bikeid");
        int bikeid=Integer.parseInt(str4);



        email.setText(str1);
        fullname.setText(str2);
        if (str3 == null) {

        } else {
            img.setImageBitmap(str3);
        }
        DatabaseHelper db = new DatabaseHelper(UserViewBikeDetails.this);

        List<BikeModel> all = db.viewBike(bikeid);
        int compid=all.get(0).getCompanyid();
        String compname= db.getCompanyName(compid);
        viewbimg.setImageBitmap(all.get(0).getBikeimage());
        viewbname.setText(all.get(0).getBikename());
        viewbcompany.setText(compname);
        viewbregno.setText(all.get(0).getBikeregno());
        viewhrent.setText("\u20B9"+all.get(0).getBikehourrent()+"/hour");
        viewdrent.setText("\u20B9"+all.get(0).getBikedayrent()+"/day");
        viewwrent.setText("\u20B9"+all.get(0).getBikeweekrent()+"/week");
        viewbdeposit.setText("Deposit : \u20B9"+all.get(0).getDeposit());
        viewbmileage.setText(all.get(0).getMileage()+"km/L");
        viewbengine.setText(all.get(0).getEngine()+"cc");
        viewbstarttype.setText(all.get(0).getStarttype());
        viewbmodel.setText(all.get(0).getModel()+"");
        viewbfueltype.setText(all.get(0).getFueltype());

        viewrcbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewrc.setImageBitmap(all.get(0).getBikerc());
            }
        });

        viewbookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserViewBikeDetails.this, UserBikeBooking.class);
                i.putExtra("userid",str);
                i.putExtra("name",str2);
                i.putExtra("email",str1);
                i.putExtra("photo",str3);
                i.putExtra("bikeid",str4);
                startActivity(i);
            }
        });


       viewrc.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Bitmap rc=all.get(0).getBikerc();
               Intent i = new Intent(UserViewBikeDetails.this, ViewImage.class);
               i.putExtra("img",rc);
               startActivity(i);
           }
       });


        viewtnc.setText("1. One Helmet will be given with the bike. Another will be available on request (Charges may apply).\n" +
                "\n"+
                "2. Security Deposit is refundable once we receive the bike in proper condition. # Passport Size Photo is required for Authorization and paper works.\n" +
                "\n" +
                "3. There is no km limit. We provide bikes with unlimited kms.\n" +
                "\n" +
                "4. You are not allowed to abandon our bikes at any place. If you do so, strict actions will be taken.\n" +
                "\n" +
                "5. Any traffic violations and challans will be on you. Better to follow traffic rules.\n" +
                "\n" +
                "6. In case of any damages or accident, the rider has to incur the cost for the damages and also have to pay the rental amount till the time the motorcycle is under repairs. The original ID proof and security deposit will be held back as security.\n" +
                "\n" +
                "7. If the bike is returned before the stipulated period of time, no refund will be provided in such a scenario.\n" +
                "\n" +
                "8. The driver’s license necessary to drive the Rental Car.\n");


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
                Intent intent = new Intent(UserViewBikeDetails.this,UserDashboard.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                Intent intent1 = new Intent(UserViewBikeDetails.this,UserLogin.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}