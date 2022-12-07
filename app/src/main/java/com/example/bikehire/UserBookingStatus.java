package com.example.bikehire;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bikehire.Model.BikeModel;
import com.example.bikehire.Model.BookingModel;
import com.example.bikehire.Model.PaymentModel;
import com.example.bikehire.Model.UserModel;
import com.google.android.material.textfield.TextInputEditText;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class UserBookingStatus extends AppCompatActivity {
    private View view_order_placed,view_order_confirmed,view_order_processed,view_order_pickup,con_divider,ready_divider,placed_divider;
    private ImageView img_orderconfirmed,orderprocessed,orderpickup,dlimg;
    private TextView textorderpickup,text_confirmed,textorderprocessed,date,id,address,amount;
    private EditText changeaddress;
    private Button updateaddress,cancelbooking,selectimg,updatebook,invoice;
    private TextInputEditText jdate,rdate,deliverytime,returntime;
    private DatePickerDialog.OnDateSetListener mDateSetListener,mDateSetListener1;
    private Date jdate1,rdate1;
    int noofdays=0,bid=0,bikeid=0;
    private static final int SELECT_PICTURE = 1;
    String bookingdate,bookingjdate,bookingrdate,bookingdtime,bookingid,add;
    DatabaseHelper DB = new DatabaseHelper(UserBookingStatus.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_booking_status);

        id=findViewById(R.id.textid);
        date=findViewById(R.id.textdate);
        address=findViewById(R.id.shippingaddress);
        changeaddress=findViewById(R.id.changeshippingaddress);
        updateaddress=findViewById(R.id.updateaddress);
        cancelbooking=findViewById(R.id.cancelorder);
        amount=findViewById(R.id.textbkamt);
        selectimg=findViewById(R.id.updatebooking_uploadDL);
        invoice=findViewById(R.id.invoice);
        dlimg=findViewById(R.id.updatebooking_DLimg);
        updatebook=findViewById(R.id.updatebooking);
        jdate=findViewById(R.id.updatebooking_deliverydate1);
        rdate=findViewById(R.id.updatebooking_returndate1);
        deliverytime=(TextInputEditText) findViewById(R.id.updatebooking_deliverytime1);
        returntime=(TextInputEditText) findViewById(R.id.updatebooking_returntime1);

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
        bookingid=intent.getStringExtra("bookingid");
        bookingdate=intent.getStringExtra("bookingdate");
        bookingjdate=intent.getStringExtra("deliverydate");
        bookingrdate=intent.getStringExtra("returndate");
        bookingdtime=intent.getStringExtra("deliverytime");
        String bookingrtime=intent.getStringExtra("returntime");
        String bookingStatus=intent.getStringExtra("bookingStatus");
        add=intent.getStringExtra("address");
        String str=intent.getStringExtra("amount");
        Bitmap img = intent.getParcelableExtra("dlphoto");

        double amt=Double.parseDouble(str);
        amount.setText("Booking Amount : \u20B9 "+amt);

        bid = Integer.parseInt(bookingid);
        bikeid=DB.getBookingBikeId(bid);

        id.setText(bookingid);
        date.setText(bookingdate);
        address.setText("Delivery Address : "+add);
        jdate.setText(bookingjdate);
        rdate.setText(bookingrdate);
        deliverytime.setText(bookingdtime);
        returntime.setText(bookingrtime);
        dlimg.setImageBitmap(img);

        getOrderStatus(bookingStatus);

        int st  = Integer.parseInt(bookingStatus);
        if(st == 3){
            cancelbooking.setEnabled(false);
            changeaddress.setEnabled(false);
            updateaddress.setEnabled(false);
            invoice.setEnabled(false);
            jdate.setEnabled(false);
            rdate.setEnabled(false);
            selectimg.setEnabled(false);
            updatebook.setEnabled(false);

        }else if(st == 4){
            cancelbooking.setEnabled(false);
            changeaddress.setEnabled(false);
            updateaddress.setEnabled(false);
            invoice.setEnabled(true);
            jdate.setEnabled(false);
            rdate.setEnabled(false);
            selectimg.setEnabled(false);
            updatebook.setEnabled(false);

        }else{
            cancelbooking.setEnabled(true);
            invoice.setEnabled(false);
            changeaddress.setEnabled(true);
            updateaddress.setEnabled(true);
            jdate.setEnabled(true);
            rdate.setEnabled(true);
            selectimg.setEnabled(true);
            updatebook.setEnabled(true);
        }

        selectimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });


        jdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(UserBookingStatus.this,
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
                jdate.setText(date);
                try {
                    jdate1=new SimpleDateFormat("dd/MM/yyyy").parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };

        rdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog = new DatePickerDialog(UserBookingStatus.this,
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
                rdate.setText(date);
                try {
                    rdate1=new SimpleDateFormat("dd/MM/yyyy").parse(date);
                    long diff = rdate1.getTime()-jdate1.getTime();
                    noofdays = (int) ((diff / (1000 * 60 * 60 * 24)) % 365);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };


        updateaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String changeadd = changeaddress.getText().toString();
                if(TextUtils.isEmpty(changeadd))
                    Toast.makeText(UserBookingStatus.this,"Field Required..",Toast.LENGTH_SHORT).show();
                else{
                    boolean success = DB.updateBookingAddress(bid,changeadd);
                    if(success==true){
                        Toast.makeText(UserBookingStatus.this, "Address Changed Successfully...!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(UserBookingStatus.this, UserBookingStatus.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(UserBookingStatus.this, "Failed to change address...!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        updatebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String journeydate = jdate.getText().toString();
                String returndate = rdate.getText().toString();
                String dtime = deliverytime.getText().toString();
                String rtime = returntime.getText().toString();
                dlimg.buildDrawingCache();
                Bitmap bimg = dlimg.getDrawingCache();

                if(TextUtils.isEmpty(journeydate) || TextUtils.isEmpty(returndate) || TextUtils.isEmpty(dtime) || TextUtils.isEmpty(rtime) || dlimg.getDrawable() == null)
                    Toast.makeText(UserBookingStatus.this,"Field Required..",Toast.LENGTH_SHORT).show();
                else{
                    boolean success = DB.updateBookingDetails(bid,journeydate,returndate,dtime,rtime,bimg);
                    if(success==true){
                        Toast.makeText(UserBookingStatus.this, "Booking Updated Successfully...!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(UserBookingStatus.this, UserBookingStatus.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(UserBookingStatus.this, "Failed to Update booking...!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        cancelbooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean success = DB.updateBookingStatus(bid,3);
                boolean success1 = DB.updateBikeStatus(bikeid,1);
                if(success==true && success1 == true){
                    Toast.makeText(UserBookingStatus.this, "Booking Cancelled Successfully...!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(UserBookingStatus.this, UserBookingStatus.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(UserBookingStatus.this, "Failed to cancel booking...!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    generatePdf();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
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
                    dlimg.setImageURI(selectedImageUri);
                }
            }
        }
    }

    private void generatePdf() throws FileNotFoundException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String date = sdf.format(new Date());

        List<BookingModel> all = DB.viewBooking(bid);
          double amount=all.get(0).getBookingAmount();
          int customerid=all.get(0).getBookingCustomerId();
          int bikeid=all.get(0).getBookingBikeId();
          int companyid=all.get(0).getBookingCompanyId();
          String compname = DB.getCompanyName(companyid);
          System.out.println(compname);
          List<UserModel> alluser=DB.viewOneUser(customerid);
          List<BikeModel> allbike=DB.viewBike(bikeid);
          List<PaymentModel> allpayment=DB.viewPayment(bid);
          String paymentid=allpayment.get(0).getPaymentId();
          String paymentdate=allpayment.get(0).getPaymentDate();
          double deposit=allbike.get(0).getDeposit();
          double rent=amount-deposit;
          String bikeno=allbike.get(0).getBikeregno();
          String bikename=allbike.get(0).getBikename();
          String engine = allbike.get(0).getEngine();
          String fuel = allbike.get(0).getFueltype();
          int model=allbike.get(0).getModel();

        Bitmap bikeimg=allbike.get(0).getBikeimage();
        Random random = new Random();
        int invoiceno = random.nextInt(5000);


        File pdfPath = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)));
        if (!pdfPath.exists()) {
            pdfPath.mkdirs();
        }
        File file = new File(pdfPath, "BikeHire-Receipt-"+invoiceno+"-"+date+".pdf");
        try {
            file.createNewFile();
            OutputStream outputStream = new FileOutputStream(file);
            PdfWriter pdfWriter = new PdfWriter(file);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            DeviceRgb blue = new DeviceRgb(4,118,208);
            DeviceRgb lightblue = new DeviceRgb(10,186,181);
            DeviceRgb gray = new DeviceRgb(245,245,245);

            float columnWidth[]={140, 140, 140, 140};
            Table table1 = new Table(columnWidth);

            Drawable d1 = getDrawable(R.drawable.logo6);
            Bitmap bitmap1 = ((BitmapDrawable)d1).getBitmap();
            ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.JPEG,100,stream1);
            byte[] bitmapData1 = stream1.toByteArray();
            ImageData imageData1 = ImageDataFactory.create(bitmapData1);
            Image image1 = new Image(imageData1);
            image1.setWidth(150f);

            Drawable d2 = getDrawable(R.drawable.thankyou);
            Bitmap bitmap2 = ((BitmapDrawable)d2).getBitmap();
            ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
            bitmap2.compress(Bitmap.CompressFormat.JPEG,100,stream2);
            byte[] bitmapData2 = stream2.toByteArray();
            ImageData imageData2 = ImageDataFactory.create(bitmapData2);
            Image image2 = new Image(imageData2);
            image2.setWidth(100f);
            image2.setHeight(60f);
            image2.setHorizontalAlignment(HorizontalAlignment.CENTER);

            ByteArrayOutputStream stream3 = new ByteArrayOutputStream();
            bikeimg.compress(Bitmap.CompressFormat.JPEG,100,stream3);
            byte[] bitmapData3 = stream3.toByteArray();
            ImageData imageData3 = ImageDataFactory.create(bitmapData3);
            Image image3 = new Image(imageData3);
            image3.setHeight(40f);
            image3.setWidth(50f);
            image3.setHorizontalAlignment(HorizontalAlignment.CENTER);


            table1.addCell(new Cell(6,1).add(image1).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell(1,3).add(new Paragraph("Booking Receipt").setFontSize(26f).setFontColor(blue).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));

            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("Receipt No.:")).setBold().setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("#"+invoiceno)).setBorder(Border.NO_BORDER));

            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("Booking ID:")).setBold().setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("#"+bookingid)).setBorder(Border.NO_BORDER));

            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("Booking Date:")).setBold().setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph(bookingdate)).setBorder(Border.NO_BORDER));

            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("Payment ID:")).setBold().setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph(paymentid)).setBorder(Border.NO_BORDER));

            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("Payment Date:")).setBold().setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph(paymentdate)).setBorder(Border.NO_BORDER));

            table1.addCell(new Cell().add(new Paragraph("\n")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));


            table1.addCell(new Cell().add(new Paragraph("Paid By:")).setBold().setFontSize(16f).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell(1,2).add(new Paragraph("Booking Details:")).setBold().setFontSize(16f).setBorder(Border.NO_BORDER));

            table1.addCell(new Cell().add(new Paragraph(alluser.get(0).getName())).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("Journey Date:")).setBold().setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph(bookingjdate)).setBorder(Border.NO_BORDER));

            table1.addCell(new Cell().add(new Paragraph(alluser.get(0).getEmail())).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("Return Date:")).setBold().setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph(bookingrdate)).setBorder(Border.NO_BORDER));

            table1.addCell(new Cell().add(new Paragraph(alluser.get(0).getPhoneno())).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("Company Name:")).setBold().setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph(compname)).setBorder(Border.NO_BORDER));

            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("Delivery Time:")).setBold().setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph(bookingdtime)).setBorder(Border.NO_BORDER));

            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("Delivery Address:")).setBold().setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph(add)).setBorder(Border.NO_BORDER));


            float columnWidth2[]={60,220,80,80,90};
            Table table2 = new Table(columnWidth2);
            table2.setTextAlignment(TextAlignment.CENTER);

            table2.addCell(new Cell().add(new Paragraph("Bike Image").setFontColor(ColorConstants.WHITE)).setBackgroundColor(lightblue));
            table2.addCell(new Cell().add(new Paragraph("Bike Description").setFontColor(ColorConstants.WHITE)).setBackgroundColor(lightblue));
            table2.addCell(new Cell().add(new Paragraph("Deposit").setFontColor(ColorConstants.WHITE)).setBackgroundColor(lightblue));
            table2.addCell(new Cell().add(new Paragraph("Rent").setFontColor(ColorConstants.WHITE)).setBackgroundColor(lightblue));
            table2.addCell(new Cell().add(new Paragraph("Total Amount").setFontColor(ColorConstants.WHITE)).setBackgroundColor(lightblue));

            table2.addCell(new Cell().add(image3).setBackgroundColor(gray).setHeight(60f));
            table2.addCell(new Cell().add(new Paragraph(bikeno+", "+bikename+", "+engine+"cc, "+fuel+", "+model+" model").setBackgroundColor(gray).setHeight(60f)));
            table2.addCell(new Cell().add(new Paragraph(""+deposit).setBackgroundColor(gray).setHeight(60f)));
            table2.addCell(new Cell().add(new Paragraph(""+rent).setBackgroundColor(gray).setHeight(60f)));
            table2.addCell(new Cell().add(new Paragraph(""+amount).setBackgroundColor(gray).setHeight(60f)));

            table2.addCell(new Cell(1,4).add(new Paragraph("Total Booking Amount:").setTextAlignment(TextAlignment.RIGHT)));
            table2.addCell(new Cell().add(new Paragraph(""+amount)));

            float columnWidth3[]={100,400};
            Table table3 = new Table(columnWidth3);

            table3.addCell(new Cell().add(new Paragraph("Note:")).setBorder(Border.NO_BORDER));
            table3.addCell(new Cell().add(new Paragraph("Thank you for booking bike from Bike Hire! We look forward for next booking....")).setBorder(Border.NO_BORDER));


            document.add(table1);
            document.add(new Paragraph("\n"));
            document.add(table2);
            document.add(new Paragraph("\n"));
            document.add(table3);
            document.add(new Paragraph("\n\n\n(Authorised Signature)\n\n").setFontSize(15f).setTextAlignment(TextAlignment.RIGHT));
            document.add(image2);
            document.close();
            Toast.makeText(this,"Receipt Downloaded...",Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
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
                Intent intent = new Intent(UserBookingStatus.this,UserDashboard.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                Intent intent1 = new Intent(UserBookingStatus.this,UserLogin.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}