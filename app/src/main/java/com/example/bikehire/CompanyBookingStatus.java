package com.example.bikehire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.bikehire.Model.BookingModel;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CompanyBookingStatus extends AppCompatActivity {
    private View view_order_placed,view_order_confirmed,view_order_processed,view_order_pickup,con_divider,ready_divider,placed_divider;
    private ImageView img_orderconfirmed,orderprocessed,orderpickup,dlimg;
    private TextView textorderpickup,text_confirmed,textorderprocessed,date,id,amount;
    private Button cancelbooking,takephoto,updateproof,viewdl;
    private ToggleButton updatestatus,updatecomplete;
    private int bid=0,bikeid=0;
    private static final int SELECT_PICTURE = 1;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    public static Bitmap img=null;

    DatabaseHelper DB = new DatabaseHelper(CompanyBookingStatus.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_booking_status);

        id=findViewById(R.id.company_txtid);
        date=findViewById(R.id.company_txtdate);
        cancelbooking=findViewById(R.id.company_cancelbooking);
        amount=findViewById(R.id.textbkamt1);
        updatestatus=findViewById(R.id.company_bookingstatus);
        updatecomplete=findViewById(R.id.company_bookingcomplete);
        takephoto=findViewById(R.id.takephoto);
        dlimg=findViewById(R.id.company_uploadproof);
        updateproof=findViewById(R.id.update_deliveryproof);
        viewdl=findViewById(R.id.company_viewdl);


        view_order_placed=findViewById(R.id.view_order_placed);
        view_order_confirmed=findViewById(R.id.view_order_confirmed);
        view_order_processed=findViewById(R.id.view_order_processed);
        view_order_pickup=findViewById(R.id.view_order_pickup);
        placed_divider=findViewById(R.id.placed_divider);
        con_divider=findViewById(R.id.con_divider);
        ready_divider=findViewById(R.id.ready_divider);

        textorderpickup=findViewById(R.id.textorderpickup);
        text_confirmed=findViewById(R.id.text_confirmed);
        textorderprocessed=findViewById(R.id.textorderprocessed);

        img_orderconfirmed=findViewById(R.id.img_orderconfirmed);
        orderprocessed=findViewById(R.id.orderprocessed);
        orderpickup=findViewById(R.id.orderpickup);


        Intent intent=getIntent();
        String bookingid=intent.getStringExtra("bookingid");
        String bookingdate=intent.getStringExtra("bookingdate");
        String bookingStatus=intent.getStringExtra("bookingStatus");
        String add=intent.getStringExtra("address");
        String str=intent.getStringExtra("amount");
        double amt=Double.parseDouble(str);
        amount.setText("Booking Amount : \u20B9 "+amt);

        bid = Integer.parseInt(bookingid);
        bikeid=DB.getBookingBikeId(bid);

        id.setText(bookingid);
        date.setText(bookingdate);
        getOrderStatus(bookingStatus);

        int st  = Integer.parseInt(bookingStatus);

        if(st==1){
            updatestatus.setChecked(false);
            updatecomplete.setChecked(false);
            cancelbooking.setEnabled(true);
            updatecomplete.setClickable(false);
            takephoto.setEnabled(false);
            updateproof.setEnabled(false);
        }
        else if(st==2){
            updatestatus.setChecked(true);
            updatestatus.setClickable(false);
            takephoto.setEnabled(true);
            updateproof.setEnabled(true);
        }
        else if(st==3){
            updatestatus.setClickable(false);
            updatecomplete.setClickable(false);
            cancelbooking.setEnabled(false);
            updatecomplete.setClickable(false);
            takephoto.setEnabled(false);
            updateproof.setEnabled(false);

        }else if(st==4){
            updatestatus.setChecked(true);
            updatecomplete.setChecked(true);
            updatestatus.setClickable(false);
            updatecomplete.setClickable(false);
            cancelbooking.setEnabled(false);
            takephoto.setEnabled(false);
            updateproof.setEnabled(false);

        }


        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

        Bitmap dlphoto=DB.viewBookingDeliveryPhoto(bid);
        if(dlphoto==null){
            updateproof.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dlimg.buildDrawingCache();
                    Bitmap ph = dlimg.getDrawingCache();
                    boolean success=DB.updateDeliveryPhoto(bid,ph);
                    if (success == true) {
                        Toast.makeText(CompanyBookingStatus.this, "Photo Updated Successfully...!", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(CompanyBookingStatus.this,CompanyBookingStatus.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(CompanyBookingStatus.this, "Photo Update Failed...!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else{
            dlimg.setImageBitmap(dlphoto);
            takephoto.setEnabled(false);
            updateproof.setEnabled(false);
        }

        dlimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlimg.buildDrawingCache();
                Bitmap ph = dlimg.getDrawingCache();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                ph.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                Intent intent = new Intent(CompanyBookingStatus.this, ViewImage2.class);
                intent.putExtra("dpimg",byteArray);
                startActivity(intent);
            }
        });

        cancelbooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean success = DB.updateBookingStatus(bid,3);
                boolean success1 = DB.updateBikeStatus(bikeid,1);
                if(success==true && success1 == true){
                    Toast.makeText(CompanyBookingStatus.this, "Booking Cancelled Successfully...!", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(CompanyBookingStatus.this,CompanyBookingStatus.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(CompanyBookingStatus.this, "Failed to cancel booking...!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        updatestatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    boolean success = DB.updateBookingStatus(bid, 2);
                    boolean success1 = DB.updateBikeStatus(bikeid,0);
                    if (success == true && success1 == true) {
                        Toast.makeText(CompanyBookingStatus.this, "Booking Confirmed...", Toast.LENGTH_SHORT).show();
                         Intent i=new Intent(CompanyBookingStatus.this,CompanyBookingStatus.class);
                         startActivity(i);
                        finish();
                    }

                } else {
                    boolean success = DB.updateBookingStatus(bid, 1);
                    boolean success1 = DB.updateBikeStatus(bikeid,0);
                    if (success == true && success1 == true) {
                        Toast.makeText(CompanyBookingStatus.this, "Booking Pending...", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(CompanyBookingStatus.this,CompanyBookingStatus.class);
                        startActivity(i);
                        finish();
                    }

                }
            }
        });


        updatecomplete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    boolean success = DB.updateBookingStatus(bid, 4);
                    boolean success1 = DB.updateBikeStatus(bikeid,1);
                    if (success == true && success1 == true) {
                        Toast.makeText(CompanyBookingStatus.this, "Booking Completed...", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(CompanyBookingStatus.this,CompanyBookingStatus.class);
                        startActivity(i);
                        finish();
                    }

                } else {
                    boolean success = DB.updateBookingStatus(bid, 1);
                    boolean success1 = DB.updateBikeStatus(bikeid,0);
                    if (success == true && success1 == true) {
                        Toast.makeText(CompanyBookingStatus.this, "Booking Pending...", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(CompanyBookingStatus.this,CompanyBookingStatus.class);
                        startActivity(i);
                        finish();
                    }

                }
            }
        });

        viewdl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<BookingModel> allbooking=DB.viewBooking(bid);
                Bitmap img=allbooking.get(0).getBookingDrivingLicense();
                Intent intent = new Intent(CompanyBookingStatus.this, ViewImage.class);
                intent.putExtra("img",img);
                startActivity(intent);
            }
        });


    }

    private void getOrderStatus(String orderStatus) {
        if (orderStatus.equals("1")){
            float alfa= (float) 0.5;
            setStatus(alfa);

        }else if (orderStatus.equals("2")){
            float alfa= (float) 1;
            setStatus1(alfa);



        }else if (orderStatus.equals("4")){
            float alfa= (float) 1;
            setStatus2(alfa);


        }else if (orderStatus.equals("3")){
            float alfa= (float) 1;
            setStatus3(alfa);

        }
    }


    private void setStatus(float alfa) {
        float myf= (float) 0.5;
        view_order_placed.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        placed_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        placed_divider.setAlpha(alfa);

        view_order_confirmed.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        con_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        img_orderconfirmed.setAlpha(alfa);
        text_confirmed.setAlpha(alfa);

        view_order_processed.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        orderprocessed.setAlpha(alfa);
        textorderprocessed.setAlpha(alfa);

        view_order_pickup.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        ready_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        orderpickup.setAlpha(alfa);
        textorderpickup.setAlpha(myf);



    }

    private void setStatus1(float alfa) {
        float myf= (float) 0.5;
        view_order_placed.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        placed_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));

        view_order_confirmed.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        con_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        img_orderconfirmed.setAlpha(alfa);
        text_confirmed.setAlpha(alfa);

        view_order_processed.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        orderprocessed.setAlpha(myf);
        textorderprocessed.setAlpha(myf);

        ready_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        view_order_pickup.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        textorderpickup.setAlpha(myf);
        orderpickup.setAlpha(myf);
        view_order_pickup.setAlpha(myf);


    }

    private void setStatus2(float alfa) {
        float myf= (float) 0.5;
        view_order_placed.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        placed_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));

        view_order_confirmed.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        con_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        orderprocessed.setAlpha(alfa);
        img_orderconfirmed.setAlpha(alfa);
        text_confirmed.setAlpha(alfa);


        view_order_processed.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        textorderprocessed.setAlpha(alfa);

        view_order_pickup.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        ready_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        textorderpickup.setAlpha(myf);
        orderpickup.setAlpha(myf);

    }

    private void setStatus3(float alfa) {
        view_order_placed.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        placed_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));

        view_order_confirmed.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        con_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        img_orderconfirmed.setAlpha(alfa);
        text_confirmed.setAlpha(alfa);

        view_order_processed.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        orderprocessed.setAlpha(alfa);
        textorderprocessed.setAlpha(alfa);

        view_order_pickup.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        ready_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        textorderpickup.setAlpha(alfa);
        orderpickup.setAlpha(alfa);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show();
            }
        }    }

    private void takePhoto() {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        }
        else
        {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            dlimg.setImageBitmap(photo);
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
                Intent intent = new Intent(CompanyBookingStatus.this,CompanyDashboard.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                Intent intent1 = new Intent(CompanyBookingStatus.this,CompanyLogin.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}