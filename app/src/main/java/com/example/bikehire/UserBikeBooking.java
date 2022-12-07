package com.example.bikehire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.bikehire.Model.BikeModel;
import com.example.bikehire.Model.BookingModel;
import com.example.bikehire.Model.CategoryModel;
import com.example.bikehire.Model.CompanyModel;
import com.example.bikehire.Model.PaymentModel;
import com.example.bikehire.Model.UserModel;
import com.google.android.material.textfield.TextInputEditText;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserBikeBooking extends AppCompatActivity implements PaymentResultListener {
    private TextInputEditText journeydate, returndate, deliverytime, returntime, address, rent, deposit, noofhourdayweek,mobileno;
    private ImageView dl, img;
    private TextView fullname, email;
    private Button book, selectdl;
    private RadioGroup rgroup;
    private RadioButton rhours, rdays, rweeks;
    private DatePickerDialog.OnDateSetListener mDateSetListener, mDateSetListener1;
    private static final int SELECT_PICTURE = 1;
    //private Date jdate,rdate;
    String custname, custemail,custid, currentdate, bikename,ddate,rdate,dtime,rtime,daddress,booktype,totalrent,depo,paymentid;
    int userid = 0, companyid = 0, bikeid = 0,noofhdw=0;
    double total=0;
    Bitmap sp,custphoto;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    String[] number,message;
    DatabaseHelper DB = new DatabaseHelper(UserBikeBooking.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bike_booking);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String bdate = sdf.format(new Date());
        currentdate = bdate;

        Checkout.preload(getApplicationContext());

        journeydate = (TextInputEditText) findViewById(R.id.deliverydate1);
        returndate = (TextInputEditText) findViewById(R.id.returndate1);
        deliverytime = (TextInputEditText) findViewById(R.id.deliverytime1);
        returntime = (TextInputEditText) findViewById(R.id.returntime1);
        noofhourdayweek = (TextInputEditText) findViewById(R.id.booking_noofhourdayweek);
        address = (TextInputEditText) findViewById(R.id.booking_deliveryaddress);
        rent = (TextInputEditText) findViewById(R.id.booking_totalrent);
        deposit = (TextInputEditText) findViewById(R.id.booking_deposit);
        dl = (ImageView) findViewById(R.id.DLimg);
        book = (Button) findViewById(R.id.book);
        selectdl = (Button) findViewById(R.id.upload_DL);
        rgroup = (RadioGroup) findViewById(R.id.groupradio);
        rhours = (RadioButton) findViewById(R.id.radio_hours);
        rdays = (RadioButton) findViewById(R.id.radio_days);
        rweeks = (RadioButton) findViewById(R.id.radio_weeks);
        mobileno = (TextInputEditText)findViewById(R.id.booking_mobileno);
        fullname = findViewById(R.id.username3);
        email = findViewById(R.id.useremail3);
        img = findViewById(R.id.imageuser3);

        rgroup.clearCheck();


        Intent intent = getIntent();
        custid = intent.getStringExtra("userid");
        custname = intent.getStringExtra("name");
        custemail = intent.getStringExtra("email");
        custphoto = intent.getParcelableExtra("photo");
        String str4 = intent.getStringExtra("bikeid");

        email.setText(custemail);
        fullname.setText(custname);
        if (custphoto == null) {

        } else {
            img.setImageBitmap(custphoto);
        }

        userid = Integer.parseInt(custid);
        bikeid = Integer.parseInt(str4);
        companyid = DB.getCompanyIdBooking(bikeid);
        System.out.println(companyid);

        List<BikeModel> all = DB.viewBike(bikeid);
        bikename = all.get(0).getBikename();
        rhours.setText("\u20B9"+all.get(0).getBikehourrent() + "/Hour");
        rdays.setText("\u20B9"+all.get(0).getBikedayrent() + "/Day");
        rweeks.setText("\u20B9"+all.get(0).getBikeweekrent() + "/Week");
        deposit.setText(all.get(0).getDeposit() + "");

        List<UserModel> alluser=DB.viewOneUser(userid);
        mobileno.setText(alluser.get(0).getPhoneno());

        rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = rgroup.getCheckedRadioButtonId();
                String nod=noofhourdayweek.getText().toString();
                if (selectedId==2131362732){
                    if(TextUtils.isEmpty(nod)){
                        double hourrent = all.get(0).getBikehourrent();
                        rent.setText(hourrent+"");
                    }else{
                        double hourrent = all.get(0).getBikehourrent();
                        double nh=Double.valueOf(noofhourdayweek.getText().toString());
                        double total=hourrent*nh;
                        rent.setText(total+"");
                    }

                }else if(selectedId==2131362731){
                    if(TextUtils.isEmpty(nod)){
                        double dayrent = all.get(0).getBikedayrent();
                        rent.setText(dayrent+"");
                    }else {
                        double dayrent = all.get(0).getBikedayrent();
                        double nh = Double.valueOf(noofhourdayweek.getText().toString());
                        double total = dayrent * nh;
                        rent.setText(total + "");
                    }

                }else if(selectedId==2131362733){
                    if(TextUtils.isEmpty(nod)){
                        double weekrent = all.get(0).getBikeweekrent();
                        rent.setText(weekrent+"");
                    }else {
                        double weekrent = all.get(0).getBikeweekrent();
                        double nh = Double.valueOf(noofhourdayweek.getText().toString());
                        double total = weekrent * nh;
                        rent.setText(total + "");
                    }
                }else{

                }
            }
        });


        selectdl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        journeydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(UserBikeBooking.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setTitle("Set Date");
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String fm = "" + month;
                String fd = "" + day;
                month = month + 1;
                if (month < 10) {
                    fm = "0" + month;
                }
                if (day < 10) {
                    fd = "0" + day;
                }
                String date = fd + "/" + fm + "/" + year;
                journeydate.setText(date);
                /*try {
                     jdate=new SimpleDateFormat("dd/MM/yyyy").parse(date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }*/
            }
        };

        returndate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog = new DatePickerDialog(UserBikeBooking.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener1,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setTitle("Set Date");
                dialog.show();
            }
        });

        mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String fm = "" + month;
                String fd = "" + day;
                month = month + 1;
                if (month < 10) {
                    fm = "0" + month;
                }
                if (day < 10) {
                    fd = "0" + day;
                }
                String date = fd + "/" + fm + "/" + year;
                returndate.setText(date);
                /*try {
                     rdate=new SimpleDateFormat("dd/MM/yyyy").parse(date);
                    long diff = rdate.getTime()-jdate.getTime();
                    noofdays = (int) ((diff / (1000 * 60 * 60 * 24)) % 365);

                } catch (ParseException e) {
                    e.printStackTrace();
                }*/
            }

        };

        deliverytime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(deliverytime);
            }
        });
        returntime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog1(returntime);
            }
        });


        rgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
            }
        });

         book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ddate = journeydate.getText().toString();
                rdate = returndate.getText().toString();
                noofhdw = Integer.parseInt(noofhourdayweek.getText().toString());
                dtime = deliverytime.getText().toString();
                rtime = returntime.getText().toString();
                daddress = address.getText().toString();
                totalrent = rent.getText().toString();
                depo = deposit.getText().toString();
                total = Double.parseDouble(totalrent) + Double.parseDouble(depo);
                dl.buildDrawingCache();
                sp = dl.getDrawingCache();

                String amt = String.valueOf(total);
                int selectedId = rgroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton)rgroup.findViewById(selectedId);
                booktype=radioButton.getText().toString();


                if (TextUtils.isEmpty(ddate) || TextUtils.isEmpty(rdate) || TextUtils.isEmpty(String.valueOf(noofhdw)) || TextUtils.isEmpty(dtime) || TextUtils.isEmpty(rtime) || TextUtils.isEmpty(totalrent) || TextUtils.isEmpty(daddress) || TextUtils.isEmpty(depo) || dl.getDrawable() == null || selectedId==-1)
                    Toast.makeText(UserBikeBooking.this, "All fields Required..", Toast.LENGTH_SHORT).show();
                else{
                    PaymentNow(amt);
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
                    dl.setImageURI(selectedImageUri);
                }
            }
        }
    }

    private void showTimeDialog(final TextInputEditText deliverytime) {
        final Calendar calendar = Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                deliverytime.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        new TimePickerDialog(UserBikeBooking.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

    private void showTimeDialog1(final TextInputEditText returntime) {
        final Calendar calendar = Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                returntime.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        new TimePickerDialog(UserBikeBooking.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }







    public void PaymentNow(String amount) {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_vAiWuvZz6RYBP2");
        checkout.setImage(R.drawable.logocircle);
        final Activity activity = this;

        double finalAmount = Float.parseFloat(amount)*100;

        try {
            JSONObject options = new JSONObject();

            options.put("name", "Bike Hire");
            options.put("description", "Make your trip better");
            options.put("image",R.drawable.logocircle);
            options.put("theme.color", "#FDA232");
            options.put("currency", "INR");
            options.put("amount", finalAmount+"");//pass amount in currency subunits
            options.put("prefill.email", custemail);
            options.put("prefill.name",custname);
            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
       try{
           Toast.makeText(this, "Payment Successful!\nPayment ID : "+s, Toast.LENGTH_SHORT).show();
          paymentid=s;
           makeBooking();
       }catch (Exception e){
           e.printStackTrace();
       }
          }

    @Override
    public void onPaymentError(int i, String s) {
        try {
            Toast.makeText(this, "Payment failed: " + Integer.toString(i) + " " + s, Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Log.e("com.merchant", e.getMessage(), e);
        }    }


    public void makeBooking(){
        BookingModel bookingModel;

        bookingModel= new BookingModel(-1, userid, companyid, bikeid, 1, noofhdw, currentdate, ddate, rdate, daddress,booktype,dtime,rtime,sp, null, total, 0.0f);
        boolean success = DB.makeBooking(bookingModel);
        if (success == true) {
            makePayment(paymentid);
        } else {
            Toast.makeText(UserBikeBooking.this, "Booking Failed...!", Toast.LENGTH_SHORT).show();
        }
    }


    public void makePayment(String s){
        PaymentModel paymentModel;
        int bookingid=DB.getLatestBookingId();
        paymentModel = new PaymentModel(s, userid,bookingid,1,currentdate,total);
        boolean success = DB.makeBookingPayment(paymentModel);
        if (success == true) {
            sendSMS();
            Toast.makeText(UserBikeBooking.this, "Booking Successful...!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(UserBikeBooking.this, UserViewBooking.class);
            i.putExtra("userid", custid);
            i.putExtra("email",custemail);
            i.putExtra("name",custname);
            i.putExtra("photo",custphoto);
            startActivity(i);
        } else {
            Toast.makeText(UserBikeBooking.this, "Booking Failed...!", Toast.LENGTH_SHORT).show();
        }
    }


    protected void sendSMS() {
        List<UserModel> alluser=DB.viewOneUser(userid);
        List<CompanyModel> allcomp=DB.viewOneCompany(companyid);
        String mno=mobileno.getText().toString();
        String name = alluser.get(0).getName();
        String[] arr=name.split(" ");
        String fname = arr[0];

        String compno = allcomp.get(0).getCompphoneno();
        String compname = allcomp.get(0).getCompname();
        String ddate=journeydate.getText().toString();
        String dtime=deliverytime.getText().toString();
        String add=address.getText().toString();
        String msg="Hi "+fname+",\nYou have successfully made booking on "+currentdate+" for bike "+bikename+" will be delivered on "+ddate+" at "+dtime+".";
        String compmsg="Hi "+compname+",\nYou have received new booking request on "+currentdate+" for bike "+bikename+" from Customer - "+name+". Bike should be delivered at "+add+" on "+ddate+" at "+dtime+".";

        number= new String[]{mno,compno};
        message= new String[]{msg, compmsg};

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);

        } else {
            SendTextMsg();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SendTextMsg();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void SendTextMsg() {
        SmsManager smsManager = SmsManager.getDefault();
        for(int i=0;i<=1;i++){
            ArrayList<String> parts = smsManager.divideMessage(message[i]);
            smsManager.sendMultipartTextMessage(number[i], "Bike Hire", parts, null, null);
            System.out.println(number[i]+"\n"+message[i]);
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
                Intent intent = new Intent(UserBikeBooking.this,UserDashboard.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                Intent intent1 = new Intent(UserBikeBooking.this,UserLogin.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}