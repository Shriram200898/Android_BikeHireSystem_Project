package com.example.bikehire;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CompanyBookingReport extends AppCompatActivity {
    private TextInputEditText startdate, enddate;
    private TextView fullname,email;
    private Button generate,download;
    private DatePickerDialog.OnDateSetListener mDateSetListener,mDateSetListener1;
    private SearchView searchBooking;
    private ImageView img;

    DatabaseHelper dataHelper = new DatabaseHelper(CompanyBookingReport.this);
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_booking_report);

        searchBooking = findViewById(R.id.search_cpbooking_report);
        fullname = findViewById(R.id.cpname5);
        email = findViewById(R.id.cpemail5);
        generate = findViewById(R.id.generate_booking_report);
        download = findViewById(R.id.download_booking_report);
        startdate = findViewById(R.id.startdate_report);
        enddate = findViewById(R.id.enddate_report);
        img = findViewById(R.id.imgcp5);
        TableLayout tableLayout = (TableLayout) findViewById(R.id.tablelayout4);

        Intent i = getIntent();
        String id1 = i.getStringExtra("id");
        id=id1;

        String name = i.getStringExtra("name");
        String aemail = i.getStringExtra("email");
        Bitmap str3 = i.getParcelableExtra("photo");
        fullname.setText(name);
        email.setText(aemail);
        if (str3 == null) {

        } else {
            img.setImageBitmap(str3);
        }
        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(CompanyBookingReport.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setTitle("Set Date");
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String fm=""+month;
                String fd=""+day;
                month = month + 1;
                if(month<10){
                    fm ="0"+month;
                }
                if (day<10){
                    fd="0"+day;
                }
                String date =  fd + "/" + fm + "/" + year;
                startdate.setText(date);
            }
        };

        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(CompanyBookingReport.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener1,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setTitle("Set Date");
                dialog.show();
            }
        });

        mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String fm=""+month;
                String fd=""+day;
                month = month + 1;
                if(month<10){
                    fm ="0"+month;
                }
                if (day<10){
                    fd="0"+day;
                }
                String date =  fd + "/" + fm + "/" + year;
                enddate.setText(date);
                System.out.println(id);


            }
        };




        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date1 = startdate.getText().toString();
                String date2 = enddate.getText().toString();

                // Add header row
                TableRow rowHeader = new TableRow(CompanyBookingReport.this);
                rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
                rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                tableLayout.setPadding(20, 20, 20, 30);
                String[] headerText = {"Booking Id", "Date", "CompanyName", "CustomerName", "BikeName","Booking Type(Rent)", "JourneyDate", "ReturnDate", "DeliveryTime", "ReturnTime", "DeliveryAddress", "BookingAmount", "BookingStatus"};
                for (String c : headerText) {
                    TextView tv = new TextView(CompanyBookingReport.this);
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(18);
                    tv.setPadding(5, 5, 5, 5);
                    tv.setText(c);
                    rowHeader.addView(tv);
                }
                tableLayout.addView(rowHeader);

                // Get data from sqlite database and add them to the table
                // Open the database for reading
                SQLiteDatabase db = dataHelper.getReadableDatabase();
                // Start the transaction.

                try {
                    Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.BOOKING_TABLE + " WHERE " + DatabaseHelper.BOOKING_COMPANYID +" =? AND "+ DatabaseHelper.BOOKING_DATE + " BETWEEN ? AND ? " ,new String[] {(id),(date1),(date2)});
                    if (cursor.getCount() > 0) {
                        while (cursor.moveToNext()) {
                            int bookingid=cursor.getInt(0);
                            int bcustomerid=cursor.getInt(1);
                            int bcompanyid=cursor.getInt(2);
                            int bbikeid=cursor.getInt(3);
                            String btype=cursor.getString(4);
                            int bstatus=cursor.getInt(5);
                            int bnoofhdw=cursor.getInt(6);
                            String bdate=cursor.getString(7);
                            String bjdate=cursor.getString(8);
                            String brdate=cursor.getString(9);
                            String bdtime=cursor.getString(10);
                            String brtime=cursor.getString(11);
                            String baddress=cursor.getString(12);
                            byte[] bpic1=cursor.getBlob(13);
                            byte[] bpic2=cursor.getBlob(14);
                            Double bamount = cursor.getDouble(15);
                            Float brating = cursor.getFloat(16);
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            Bitmap bdlpic = BitmapFactory.decodeByteArray(bpic1, 0, bpic1.length, options);
                            Bitmap bdpic = BitmapFactory.decodeByteArray(bpic2, 0, bpic2.length, options);

                            String custname = dataHelper.getUserName(bcustomerid);
                            String compname = dataHelper.getCompanyName(Integer.parseInt(id));
                            String bikename = dataHelper.getBikeName(bbikeid);

                            String bookingSt;
                            if (bstatus == 1)
                            {
                                bookingSt = "Pending";
                            }
                            else if(bstatus==2){
                                bookingSt = "Completed";
                            }
                            else{
                                bookingSt = "Cancelled";
                            }


                            // dara rows
                            TableRow row = new TableRow(CompanyBookingReport.this);
                            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                                    TableLayout.LayoutParams.WRAP_CONTENT));
                            String[] colText = {bookingid + "", bdate, compname, custname, bikename,btype, bjdate, brdate,bdtime,brtime, baddress, "\u20B9 "+bamount, bookingSt };
                            for (String text : colText) {
                                TextView tv = new TextView(CompanyBookingReport.this);
                                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                        TableRow.LayoutParams.WRAP_CONTENT));
                                tv.setGravity(Gravity.CENTER);
                                tv.setTextSize(16);
                                tv.setPadding(5, 5, 5, 5);
                                tv.setText(text);
                                row.addView(tv);
                            }
                            tableLayout.addView(row);

                        }

                    }

                } catch (SQLiteException e) {
                    e.printStackTrace();

                }

            }
        });


        download.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                boolean success = exportDB();
                if (success == true) {
                    Toast.makeText(CompanyBookingReport.this, "Report Downloaded Successfully...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CompanyBookingReport.this, "Failed to Download Report...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean exportDB() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String date = sdf.format(new Date());
        String date1 = startdate.getText().toString();
        String date2 = enddate.getText().toString();

        DatabaseHelper dbhelper = new DatabaseHelper(getApplicationContext());

        File exportDir = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)));
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "BikeHire-BookingReport"+date+".csv");
        try {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.BOOKING_TABLE + " WHERE " + DatabaseHelper.BOOKING_DATE + " BETWEEN ? AND ? AND "+ DatabaseHelper.BOOKING_COMPANYID +"=?",new String[] {(date1),(date2),(id)});
            String[] headerText = {"Booking Id", "Date", "CompanyName", "CustomerName", "BikeName","Booking Type(Rent)", "JourneyDate", "ReturnDate", "DeliveryTime", "ReturnTime", "DeliveryAddress", "BookingAmount", "BookingStatus"};

            csvWrite.writeNext(headerText);
            while (cursor.moveToNext()) {
                int bookingid=cursor.getInt(0);
                int bcustomerid=cursor.getInt(1);
                int bcompanyid=cursor.getInt(2);
                int bbikeid=cursor.getInt(3);
                String btype=cursor.getString(4);
                int bstatus=cursor.getInt(5);
                int bnoofhdw=cursor.getInt(6);
                String bdate=cursor.getString(7);
                String bjdate=cursor.getString(8);
                String brdate=cursor.getString(9);
                String bdtime=cursor.getString(10);
                String brtime=cursor.getString(11);
                String baddress=cursor.getString(12);
                byte[] bpic1=cursor.getBlob(13);
                byte[] bpic2=cursor.getBlob(14);
                Double bamount = cursor.getDouble(15);
                Float brating = cursor.getFloat(16);
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap bdlpic = BitmapFactory.decodeByteArray(bpic1, 0, bpic1.length, options);
                Bitmap bdpic = BitmapFactory.decodeByteArray(bpic2, 0, bpic2.length, options);

                String custname = dataHelper.getUserName(bcustomerid);
                String compname = dataHelper.getCompanyName(bcompanyid);
                String bikename = dataHelper.getBikeName(bbikeid);

                String bookingSt;
                if (bstatus == 1)
                {
                    bookingSt = "Pending";
                }
                else if(bstatus==2){
                    bookingSt = "Completed";
                }
                else{
                    bookingSt = "Cancelled";
                }

                String arrStr[] = {bookingid + "", bdate, compname, custname, bikename,btype, bjdate, brdate,bdtime,brtime, baddress, "\u20B9 "+bamount, bookingSt };
                csvWrite.writeNext(arrStr);


            }
            csvWrite.close();
            cursor.close();
        } catch (Exception sqlEx) {
            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
        }
        return true;

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
                Intent intent = new Intent(CompanyBookingReport.this,CompanyDashboard.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                Intent intent1 = new Intent(CompanyBookingReport.this,CompanyLogin.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}


