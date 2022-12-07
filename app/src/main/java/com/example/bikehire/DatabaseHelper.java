package com.example.bikehire;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.example.bikehire.Model.AdminModel;
import com.example.bikehire.Model.BikeModel;
import com.example.bikehire.Model.BookingModel;
import com.example.bikehire.Model.CategoryModel;
import com.example.bikehire.Model.CompanyModel;
import com.example.bikehire.Model.FeedbackModel;
import com.example.bikehire.Model.PaymentModel;
import com.example.bikehire.Model.UserModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String USER_TABLE = "USER_TABLE";
    public static final String USER_ID = "USER_ID";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_PASSWORD = "USER_PASSWORD";
    public static final String USER_PHONENO = "USER_PHONENO";
    public static final String USER_ADDRESS = "USER_ADDRESS";
    public static final String USER_PROFILEPHOTO = "USER_PROFILEPHOTO";


    public static final String ADMIN_TABLE = "ADMIN_TABLE";
    public static final String ADMIN_ID = "ADMIN_ID";
    public static final String ADMIN_NAME = "ADMIN_NAME";
    public static final String ADMIN_EMAIL = "ADMIN_EMAIL";
    public static final String ADMIN_PASSWORD = "ADMIN_PASSWORD";

    public static final String COMPANY_TABLE = "COMPANY_TABLE";
    public static final String COMPANY_ID = "COMPANY_ID";
    public static final String COMPANY_NAME = "COMPANY_NAME";
    public static final String COMPANY_EMAIL = "COMPANY_EMAIL";
    public static final String COMPANY_PASSWORD = "COMPANY_PASSWORD";
    public static final String COMPANY_PHONENO = "COMPANY_PHONENO";
    public static final String COMPANY_ADDRESS = "COMPANY_ADDRESS";
    public static final String COMPANY_PROFILEPHOTO = "COMPANY_PROFILEPHOTO";
    public static final String COMPANY_ISACTIVE = "COMPANY_ISACTIVE";
    public static final String COMPANY_RATING = "COMPANY_RATING";
    public static final String REG_DATE = "REG_DATE";

    public static final String CATEGORY_TABLE = "CATEGORY_TABLE";
    public static final String CATEGORY_ID = "CATEGORY_ID";
    public static final String CATEGORY_NAME = "CATEGORY_NAME";
    public static final String CATEGORY_DESC = "CATEGORY_DESC";
    public static final String CATEGORY_PICTURE = "CATEGORY_PICTURE";

    public static final String BIKE_TABLE = "BIKE_TABLE";
    public static final String BIKE_NO = "BIKE_NO";
    public static final String BIKE_REG_NO = "BIKE_REG_NO";
    public static final String BIKE_COMPANYID = "BIKE_COMPANYID";
    public static final String BIKE_NAME = "BIKE_NAME";
    public static final String BIKE_CATEGORY = "BIKE_CATEGORY";
    public static final String BIKE_HOURRENT = "BIKE_HOURRENT";
    public static final String BIKE_DAYRENT = "BIKE_DAYRENT";
    public static final String BIKE_WEEKRENT = "BIKE_WEEKRENT";
    public static final String BIKE_DEPOSIT = "BIKE_DEPOSIT";
    public static final String BIKE_MILEAGE = "BIKE_MILEAGE";
    public static final String BIKE_ENGINE = "BIKE_ENGINE";
    public static final String BIKE_STARTTYPE = "BIKE_STARTTYPE";
    public static final String BIKE_FUELTYPE = "BIKE_FUELTYPE";
    public static final String BIKE_MODEL = "BIKE_MODEL";
    public static final String BIKE_RC = "BIKE_RC";
    public static final String BIKE_DESCRIPTION = "BIKE_DESCRIPTION";
    public static final String BIKE_STATUS = "BIKE_STATUS";
    public static final String BIKE_IMAGE = "BIKE_IMAGE";


    public static final String BOOKING_TABLE = "BOOKING_TABLE";
    public static final String BOOKING_ID = "BOOKING_ID";
    public static final String BOOKING_CUSTOMERID = "BOOKING_CUSTOMERID";
    public static final String BOOKING_COMPANYID = "BOOKING_COMPANYID";
    public static final String BOOKING_BIKEID = "BOOKING_BIKEID";
    public static final String BOOKING_STATUS = "BOOKING_STATUS";
    public static final String BOOKING_TYPE = "BOOKING_TYPE";
    public static final String BOOKING_NOOFHOURSDAYSWEEK = "BOOKING_NOOFHOURSDAYSWEEK";
    public static final String BOOKING_DATE = "BOOKING_DATE";
    public static final String JOURNEY_DATE = "JOURNEY_DATE";
    public static final String RETURN_DATE = "RETURN_DATE";
    public static final String DELIVERY_TIME = "DELIVERY_TIME";
    public static final String RETURN_TIME = "RETURN_TIME";
    public static final String BOOKING_ADDRESS = "BOOKING_ADDRESS";
    public static final String BOOKING_DLPHOTO = "BOOKING_DLPHOTO";
    public static final String BOOKING_DELIVERYPHOTO = "BOOKING_DELIVERYPHOTO";
    public static final String BOOKING_AMOUNT = "BOOKING_AMOUNT";
    public static final String BOOKING_RATING = "BOOKING_RATING";

    public static final String FEEDBACK_TABLE = "FEEDBACK_TABLE";
    public static final String FEEDBACK_ID = "FEEDBACK_ID";
    public static final String FEEDBACK_CUSTOMERID = "FEEDBACK_CUSTOMERID";
    public static final String FEEDBACK_COMPANYID = "FEEDBACK_COMPANYID";
    public static final String FEEDBACK_BOOKINGID = "FEEDBACK_BOOKINGID";
    public static final String FEEDBACK_COMPANY_RATING = "FEEDBACK_COMPANY_RATING";
    public static final String FEEDBACK_SATISFACTION = "FEEDBACK_SATISFACTION";
    public static final String FEEDBACK_SUGGESTION = "FEEDBACK_SUGGESTION";
    public static final String FEEDBACK_DATE = "FEEDBACK_DATE";

    public static final String PAYMENT_TABLE = "PAYMENT_TABLE";
    public static final String PAYMENT_ID = "PAYMENT_ID";
    public static final String PAYMENT_CUSTOMERID = "PAYMENT_CUSTOMERID";
    public static final String PAYMENT_BOOKINGID = "PAYMENT_BOOKINGID";
    public static final String PAYMENT_STATUS = "PAYMENT_STATUS";
    public static final String PAYMENT_DATE = "PAYMENT_DATE";
    public static final String PAYMENT_AMOUNT = "PAYMENT_AMOUNT";


    private ByteArrayOutputStream objectByteOutputStream,objectByteOutputStream1;
    private  byte[] imageInByte,imageInByte1;
    PasswordEncryption pe;

    public DatabaseHelper(@Nullable Context context) {
        super(context, "bikehire.db", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + USER_TABLE + "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_NAME + " VARCHAR(50)," + USER_EMAIL + " VARCHAR(50), " + USER_PASSWORD + " VARCHAR(20)," + USER_PHONENO + " VARCHAR(10)," + USER_ADDRESS + " VARCHAR(2000)," + USER_PROFILEPHOTO + " BLOB)");
        db.execSQL("CREATE TABLE " + COMPANY_TABLE + " (" + COMPANY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COMPANY_NAME + " VARCHAR(50), " + COMPANY_EMAIL + " VARCHAR(50), " + COMPANY_PASSWORD + " VARCHAR(20)," + COMPANY_PHONENO + " VARCHAR(10), " + COMPANY_ADDRESS + " VARCHAR(2000)," + COMPANY_PROFILEPHOTO + " BLOB, " + COMPANY_ISACTIVE + " BOOLEAN," + COMPANY_RATING + " FLOAT," + REG_DATE + " DATE)");
        db.execSQL("CREATE TABLE " + ADMIN_TABLE + " (" + ADMIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ADMIN_NAME + " VARCHAR(50)," + ADMIN_EMAIL + " VARCHAR(50)," + ADMIN_PASSWORD + " VARCHAR(20))");
        db.execSQL("CREATE TABLE " + CATEGORY_TABLE  + " (" + CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + CATEGORY_NAME + " VARCHAR(50)," + CATEGORY_DESC + " TEXT," + CATEGORY_PICTURE + " BLOB)");
        db.execSQL("INSERT INTO " +ADMIN_TABLE + " VALUES (1,'Admin','admin123@gmail.com','Admin@123')");
        db.execSQL("CREATE TABLE " + BIKE_TABLE + " (" + BIKE_NO + " INTEGER PRIMARY KEY AUTOINCREMENT, " + BIKE_REG_NO + " VARCHAR(100), " + BIKE_NAME + " VARCHAR(100), " + BIKE_COMPANYID + " INTEGER, " + BIKE_CATEGORY + " VARCHAR(100), " + BIKE_STARTTYPE + " VARCHAR(200), " + BIKE_FUELTYPE + " VARCHAR(200), " + BIKE_ENGINE + " VARCHAR(100)," + BIKE_HOURRENT + " DOUBLE," + BIKE_DAYRENT + " DOUBLE," + BIKE_WEEKRENT + " DOUBLE, " + BIKE_DEPOSIT + " DOUBLE, " + BIKE_MILEAGE + " INTEGER, " + BIKE_MODEL + " INTEGER, " + BIKE_DESCRIPTION + " TEXT, " + BIKE_STATUS + " INTEGER, " + BIKE_IMAGE + " BLOB, " + BIKE_RC + " BLOB)");
        db.execSQL("CREATE TABLE " + BOOKING_TABLE + " (" + BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + BOOKING_CUSTOMERID + " INTEGER," + BOOKING_COMPANYID + " INTEGER," + BOOKING_BIKEID + " INTEGER," + BOOKING_TYPE + " VARCHAR(200)," + BOOKING_STATUS + " INTEGER," + BOOKING_NOOFHOURSDAYSWEEK + " INTEGER," + BOOKING_DATE + " DATE," + JOURNEY_DATE + " DATE," + RETURN_DATE + " DATE," + DELIVERY_TIME + " TEXT," + RETURN_TIME + " TEXT," + BOOKING_ADDRESS + " VARCAHR(255)," + BOOKING_DLPHOTO + " BLOB," + BOOKING_DELIVERYPHOTO + " BLOB," + BOOKING_AMOUNT + " DOUBLE," + BOOKING_RATING + " FLOAT)");
        db.execSQL("CREATE TABLE " + PAYMENT_TABLE + " (" + PAYMENT_ID + " VARCHAR(255) PRIMARY KEY , " + PAYMENT_CUSTOMERID + " INTEGER, " + PAYMENT_BOOKINGID + " INTEGER," + PAYMENT_STATUS + " INTEGER," + PAYMENT_DATE + " DATE," + PAYMENT_AMOUNT + " DOUBLE)");
        db.execSQL("CREATE TABLE " + FEEDBACK_TABLE + " (" + FEEDBACK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FEEDBACK_CUSTOMERID + " INTEGER, " + FEEDBACK_COMPANYID + " INTEGER," + FEEDBACK_BOOKINGID + " INTEGER," + FEEDBACK_COMPANY_RATING + " FLOAT," + FEEDBACK_SATISFACTION + " TEXT," + FEEDBACK_SUGGESTION + " TEXT," + FEEDBACK_DATE + " DATE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }

    public List<AdminModel> viewAdminDetails(){
        List<AdminModel> returnList = new ArrayList<>();
        String qry="SELECT * FROM " + ADMIN_TABLE;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(qry,null);
        if (cursor.moveToFirst()){
            do{
                int adminid=cursor.getInt(0);
                String adminname=cursor.getString(1);
                String adminemail=cursor.getString(2);
                String adminpwd=cursor.getString(3);


                AdminModel newadmin=new AdminModel(adminid,adminname,adminemail,adminpwd);
                returnList.add(newadmin);

            }while (cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return returnList;
    }

    public boolean checkadminemail(String adminemail){
        SQLiteDatabase db1=this.getWritableDatabase();
        Cursor cursor = db1.rawQuery("SELECT * FROM " + ADMIN_TABLE + " WHERE " + ADMIN_EMAIL + "=?",new String[] {adminemail});

        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean checkadminemailpassword(String adminemail,String adminpassword){
        SQLiteDatabase db1=this.getWritableDatabase();
        Cursor cursor = db1.rawQuery("SELECT * FROM " + ADMIN_TABLE + " WHERE " + ADMIN_EMAIL + "=? AND " + ADMIN_PASSWORD + "=?",new String[] {adminemail,adminpassword});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean updateAdminPassword(String email,String pwd){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(ADMIN_PASSWORD ,pwd);

        long update = db.update(ADMIN_TABLE, cv, ADMIN_EMAIL + "=?", new String[]{email});
        db.close();
        if(update==-1){
            return false;
        }
        else{
            return true;
        }
    }




    public boolean addOneUser(UserModel userModel){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(USER_NAME,userModel.getName());
        cv.put(USER_EMAIL,userModel.getEmail());
        cv.put(USER_PASSWORD,userModel.getPwd());
        cv.put(USER_PHONENO,userModel.getPhoneno());
        cv.put(USER_ADDRESS,userModel.getAdd());
        cv.put(USER_PROFILEPHOTO,"");

        long insert = db.insert(USER_TABLE,null,cv);
        if(insert==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public List<UserModel> viewAllUser(String email){
        List<UserModel> returnList = new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM " + USER_TABLE + " WHERE " + USER_EMAIL + "=?",new String[] {email});
        if (cursor.moveToFirst()){
            do{
                int uid=cursor.getInt(0);
                String uname=cursor.getString(1);
                String uemail=cursor.getString(2);
                String upwd=cursor.getString(3);
                String uphoneno=cursor.getString(4);
                String uaddress=cursor.getString(5);
                byte[] uprofilephoto=cursor.getBlob(6);
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap uphoto = BitmapFactory.decodeByteArray(uprofilephoto, 0, uprofilephoto.length, options);


                UserModel newuser=new UserModel(uid,uname,uemail,upwd,uphoneno,uaddress,uphoto);
                returnList.add(newuser);

            }while (cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return returnList;
    }

    public List<UserModel> viewOneUser(int id){
        List<UserModel> returnList = new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM " + USER_TABLE + " WHERE " + USER_ID + "=?",new String[] {String.valueOf(id)});
        if (cursor.moveToFirst()){
            do{
                int uid=cursor.getInt(0);
                String uname=cursor.getString(1);
                String uemail=cursor.getString(2);
                String upwd=cursor.getString(3);
                String uphoneno=cursor.getString(4);
                String uaddress=cursor.getString(5);
                byte[] uprofilephoto=cursor.getBlob(6);
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap uphoto = BitmapFactory.decodeByteArray(uprofilephoto, 0, uprofilephoto.length, options);


                UserModel newuser=new UserModel(uid,uname,uemail,upwd,uphoneno,uaddress,uphoto);
                returnList.add(newuser);

            }while (cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return returnList;
    }

    public boolean updateUser(UserModel userModel){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        Bitmap imageToStore=userModel.getPhoto();
        objectByteOutputStream=new ByteArrayOutputStream();
        imageToStore.compress(Bitmap.CompressFormat.JPEG,100,objectByteOutputStream);
        imageInByte=objectByteOutputStream.toByteArray();
        cv.put(USER_NAME,userModel.getName());
        cv.put(USER_EMAIL,userModel.getEmail());
        cv.put(USER_PHONENO,userModel.getPhoneno());
        cv.put(USER_ADDRESS,userModel.getAdd());
        cv.put(USER_PROFILEPHOTO,imageInByte);
        long update = db.update(USER_TABLE, cv, USER_ID + "=?", new String[]{String.valueOf(userModel.getId())});
        db.close();
        if(update==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean checkuseremail(String useremail){
        SQLiteDatabase db1=this.getWritableDatabase();
        Cursor cursor = db1.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE " + USER_EMAIL + "=?",new String[] {useremail});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean checkuseremailpassword(String uemail,String userpassword) {
        SQLiteDatabase db1=this.getWritableDatabase();
        Cursor cursor = db1.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE " + USER_EMAIL + "=? and " + USER_PASSWORD + "=?",new String[] {uemail,userpassword});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public String getUserName(int id) {
        String name = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(" SELECT "+USER_NAME+" FROM " + USER_TABLE + " WHERE " + USER_ID + "=?",new String[] {String.valueOf(id)});
        if (cursor.moveToFirst())
            name = cursor.getString(0);
        return name;
    }


    public boolean updateUserPassword(String email,String pwd){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(USER_PASSWORD ,pwd);

        long update = db.update(USER_TABLE, cv, USER_EMAIL + "=?", new String[]{email});
        db.close();
        if(update==-1){
            return false;
        }
        else{
            return true;
        }
    }



//Company
    public boolean addOneCompany(CompanyModel companyModel){
        SQLiteDatabase db1=this.getWritableDatabase();
        ContentValues cv1=new ContentValues();
        cv1.put(COMPANY_NAME,companyModel.getCompname());
        cv1.put(COMPANY_EMAIL,companyModel.getCompemail());
        cv1.put(COMPANY_PASSWORD,companyModel.getComppwd());
        cv1.put(COMPANY_PHONENO,companyModel.getCompphoneno());
        cv1.put(COMPANY_ADDRESS,companyModel.getCompaddress());
        cv1.put(REG_DATE,companyModel.getRegDate());
        cv1.put(COMPANY_PROFILEPHOTO,"");
        cv1.put(COMPANY_ISACTIVE,false);
        cv1.put(COMPANY_RATING,companyModel.getComprating());

        long insert1 = db1.insert(COMPANY_TABLE,null,cv1);
        if(insert1==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public String getCompanyName(int id) {
        String name = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(" SELECT "+COMPANY_NAME+" FROM " + COMPANY_TABLE + " WHERE " + COMPANY_ID + "=?",new String[] {String.valueOf(id)});
        if (cursor.moveToFirst())
            name = cursor.getString(0);
        cursor.close();
        db.close();
        return name;
    }

    public int getCompanyId(String email) {
        int id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(" SELECT "+COMPANY_ID+" FROM " + COMPANY_TABLE + " WHERE " + COMPANY_EMAIL + "=?",new String[] {email});
        if (cursor.moveToFirst())
            id = cursor.getInt(0);
        cursor.close();
        db.close();
        return id;
    }


    public float getCompanyRating(int compid) {
        float result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select "+ COMPANY_RATING +" from " + COMPANY_TABLE + " WHERE " + COMPANY_ID + "=?",new String[] {String.valueOf(compid)});
        if (cursor.moveToFirst())
            result = cursor.getFloat(0);
        cursor.close();
        db.close();
        return result;
    }

    public boolean updateCompanyRating(int cid,float rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COMPANY_RATING, rating);

        long update = db.update(COMPANY_TABLE, cv, COMPANY_ID + "=?", new String[]{String.valueOf(cid)});
        db.close();
        if (update == -1) {
            return false;
        } else {
            return true;
        }
    }


    public List<CompanyModel> viewAllCompany(){
        List<CompanyModel> returnList = new ArrayList<>();
        String qry = "SELECT * FROM " + COMPANY_TABLE;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(qry,null);
        if (cursor.moveToFirst()){
            do{
                int Companyid=cursor.getInt(0);
                String compname=cursor.getString(1);
                String compemail=cursor.getString(2);
                String comppwd=cursor.getString(3);
                String compphoneno=cursor.getString(4);
                String compaddress=cursor.getString(5);
                byte[] compprofilephoto=cursor.getBlob(6);
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap compphoto = BitmapFactory.decodeByteArray(compprofilephoto, 0, compprofilephoto.length, options);
                int compisactive = cursor.getInt(7);
                float comprate = cursor.getFloat(8);
                String compdate=cursor.getString(9);


                CompanyModel newCompany=new CompanyModel(Companyid,compname,compemail,comppwd,compphoneno,compaddress,compdate,compphoto,compisactive,comprate);
                returnList.add(newCompany);

            }while (cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return returnList;
    }

    public List<CompanyModel> viewCompany(String email){
        List<CompanyModel> returnList = new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM " + COMPANY_TABLE + " WHERE " + COMPANY_EMAIL + "=?",new String[] {email});
        if (cursor.moveToFirst()){
            do{
                int Companyid=cursor.getInt(0);
                String compname=cursor.getString(1);
                String compemail=cursor.getString(2);
                String comppwd=cursor.getString(3);
                String compphoneno=cursor.getString(4);
                String compaddress=cursor.getString(5);
                byte[] compprofilephoto=cursor.getBlob(6);
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap compphoto = BitmapFactory.decodeByteArray(compprofilephoto, 0, compprofilephoto.length, options);
                int compisactive = cursor.getInt(7);
                float comprate = cursor.getFloat(8);
                String compdate=cursor.getString(9);


                CompanyModel newCompany=new CompanyModel(Companyid,compname,compemail,comppwd,compphoneno,compaddress,compdate,compphoto,compisactive,comprate);
                returnList.add(newCompany);

            }while (cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return returnList;
    }

    public List<CompanyModel> viewOneCompany(int id){
        List<CompanyModel> returnList = new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM " + COMPANY_TABLE + " WHERE " + COMPANY_ID + "=?",new String[] {String.valueOf(id)});
        if (cursor.moveToFirst()){
            do{
                int Companyid=cursor.getInt(0);
                String compname=cursor.getString(1);
                String compemail=cursor.getString(2);
                String comppwd=cursor.getString(3);
                String compphoneno=cursor.getString(4);
                String compaddress=cursor.getString(5);
                byte[] compprofilephoto=cursor.getBlob(6);
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap compphoto = BitmapFactory.decodeByteArray(compprofilephoto, 0, compprofilephoto.length, options);
                int compisactive = cursor.getInt(7);
                float comprate = cursor.getFloat(8);
                String compdate=cursor.getString(9);

                CompanyModel newCompany=new CompanyModel(Companyid,compname,compemail,comppwd,compphoneno,compaddress,compdate,compphoto,compisactive,comprate);
                returnList.add(newCompany);

            }while (cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return returnList;
    }

    public boolean updateCompany(CompanyModel companyModel){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv1=new ContentValues();
        Bitmap imageToStore=companyModel.getCompprofileimage();
        objectByteOutputStream=new ByteArrayOutputStream();
        imageToStore.compress(Bitmap.CompressFormat.JPEG,100,objectByteOutputStream);
        imageInByte=objectByteOutputStream.toByteArray();
        cv1.put(COMPANY_NAME,companyModel.getCompname());
        cv1.put(COMPANY_EMAIL,companyModel.getCompemail());
        cv1.put(COMPANY_PHONENO,companyModel.getCompphoneno());
        cv1.put(COMPANY_ADDRESS,companyModel.getCompaddress());
        cv1.put(COMPANY_PROFILEPHOTO,imageInByte);
        long update = db.update(COMPANY_TABLE, cv1, COMPANY_ID + "=?", new String[]{String.valueOf(companyModel.getCompid())});
        db.close();
        if(update==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean deleteCompany(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        long delete=db.delete(COMPANY_TABLE,COMPANY_ID + "=?", new String[] {String.valueOf(id)});
        db.close();
        if(delete==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean checkcompemail(String compemail){
        SQLiteDatabase db1=this.getWritableDatabase();
        Cursor cursor = db1.rawQuery("SELECT * FROM " + COMPANY_TABLE + " WHERE " + COMPANY_EMAIL + "=?",new String[] {compemail});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public boolean checkcompemailpassword(String compemail,String comppassword){
        SQLiteDatabase db1=this.getWritableDatabase();
        Cursor cursor = db1.rawQuery("SELECT * FROM " + COMPANY_TABLE + " WHERE " + COMPANY_EMAIL + "=? AND " + COMPANY_PASSWORD + "=?",new String[] {compemail,comppassword});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean updateCompanyPassword(String email,String pwd){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COMPANY_PASSWORD ,pwd);

        long update = db.update(COMPANY_TABLE, cv, COMPANY_EMAIL + "=?", new String[]{email});
        db.close();
        if(update==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean isCompanyActive(int id,boolean isactive){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv1=new ContentValues();
        cv1.put(COMPANY_ISACTIVE,isactive);
        long update = db.update(COMPANY_TABLE, cv1, COMPANY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        if(update==-1){
            return false;
        }
        else{
            return true;
        }
    }







    public boolean addCategory(CategoryModel categoryModel){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        Bitmap imageToStore=categoryModel.getCatepicture();
        objectByteOutputStream=new ByteArrayOutputStream();
        imageToStore.compress(Bitmap.CompressFormat.JPEG,100,objectByteOutputStream);
        imageInByte=objectByteOutputStream.toByteArray();
        cv.put(CATEGORY_NAME,categoryModel.getCatename());
        cv.put(CATEGORY_DESC,categoryModel.getCatedesc());
        cv.put(CATEGORY_PICTURE,imageInByte);

        long insert = db.insert(CATEGORY_TABLE,null,cv);
        if(insert==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public List<CategoryModel> viewAllCategory(){
        List<CategoryModel> returnList = new ArrayList<>();
        String qry="SELECT * FROM " + CATEGORY_TABLE;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(qry,null);
        if (cursor.moveToFirst()){
            do{
                int Categoryid=cursor.getInt(0);
                String catename=cursor.getString(1);
                String catedesc=cursor.getString(2);
                byte[] catepic=cursor.getBlob(3);
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeByteArray(catepic, 0, catepic.length, options);

                CategoryModel newCategory=new CategoryModel(Categoryid,catename,catedesc,bitmap);
                returnList.add(newCategory);

            }while (cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return returnList;
    }



    public boolean updateCategory(CategoryModel categoryModel){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        Bitmap imageToStore=categoryModel.getCatepicture();
        objectByteOutputStream=new ByteArrayOutputStream();
        imageToStore.compress(Bitmap.CompressFormat.JPEG,100,objectByteOutputStream);
        imageInByte=objectByteOutputStream.toByteArray();
        cv.put(CATEGORY_NAME ,categoryModel.getCatename());
        cv.put(CATEGORY_DESC,categoryModel.getCatedesc());
        cv.put(CATEGORY_PICTURE,imageInByte);

        long update = db.update(CATEGORY_TABLE, cv, CATEGORY_ID + "=?", new String[]{String.valueOf(categoryModel.getId())});
        db.close();
        if(update==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean deleteCategory(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        long delete=db.delete(CATEGORY_TABLE,CATEGORY_ID + "=?", new String[] {String.valueOf(id)});
        db.close();
        if(delete==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean checkCategory(String Categoryname){
        SQLiteDatabase db1=this.getWritableDatabase();
        Cursor cursor = db1.rawQuery("SELECT * FROM " + CATEGORY_TABLE + " WHERE " + CATEGORY_NAME + "=?",new String[] {Categoryname});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }









    public boolean addBike(BikeModel bikeModel){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        Bitmap imageToStore=bikeModel.getBikeimage();
        objectByteOutputStream=new ByteArrayOutputStream();
        imageToStore.compress(Bitmap.CompressFormat.JPEG,100,objectByteOutputStream);
        imageInByte=objectByteOutputStream.toByteArray();
        Bitmap imageToStore1=bikeModel.getBikerc();
        objectByteOutputStream1=new ByteArrayOutputStream();
        imageToStore1.compress(Bitmap.CompressFormat.JPEG,100,objectByteOutputStream1);
        imageInByte1=objectByteOutputStream1.toByteArray();
        cv.put(BIKE_REG_NO,bikeModel.getBikeregno());
        cv.put(BIKE_NAME,bikeModel.getBikename());
        cv.put(BIKE_COMPANYID,bikeModel.getCompanyid());
        cv.put(BIKE_CATEGORY,bikeModel.getBikecategory());
        cv.put(BIKE_STARTTYPE,bikeModel.getStarttype());
        cv.put(BIKE_FUELTYPE,bikeModel.getFueltype());
        cv.put(BIKE_ENGINE,bikeModel.getEngine());
        cv.put(BIKE_HOURRENT,bikeModel.getBikehourrent());
        cv.put(BIKE_DAYRENT,bikeModel.getBikedayrent());
        cv.put(BIKE_WEEKRENT,bikeModel.getBikeweekrent());
        cv.put(BIKE_DEPOSIT,bikeModel.getDeposit());
        cv.put(BIKE_MILEAGE,bikeModel.getMileage());
        cv.put(BIKE_MODEL,bikeModel.getModel());
        cv.put(BIKE_DESCRIPTION,bikeModel.getDescription());
        cv.put(BIKE_STATUS,bikeModel.getBikestatus());
        cv.put(BIKE_IMAGE,imageInByte);
        cv.put(BIKE_RC,imageInByte1);

        long insert = db.insert(BIKE_TABLE,null,cv);
        if(insert==-1){
            return false;
        }
        else{
            return true;
        }
    }


    public String getBikeName(int id) {
        String name = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(" SELECT "+BIKE_NAME+" FROM " + BIKE_TABLE + " WHERE " + BIKE_NO + "=?",new String[] {String.valueOf(id)});
        if (cursor.moveToFirst())
            name = cursor.getString(0);
        cursor.close();
        db.close();
        return name;
    }


    public int getCompanyIdBooking(int bikeid) {
        int cid = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(" SELECT "+ BIKE_COMPANYID +" FROM " + BIKE_TABLE + " WHERE " + BIKE_NO + "=?",new String[] {String.valueOf(bikeid)});
        if (cursor.moveToFirst())
            cid = cursor.getInt(0);
        return cid;
    }



    public List<BikeModel> viewCategoryBike(String category){
        List<BikeModel> returnList = new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + BIKE_TABLE + " WHERE " + BIKE_CATEGORY + "=?",new String[] {category} );
        if (cursor.moveToFirst()){
            do{
                int bid=cursor.getInt(0);
                String bregno=cursor.getString(1);
                String bname=cursor.getString(2);
                int cid=cursor.getInt(3);
                String bcate=cursor.getString(4);
                String bstarttype=cursor.getString(5);
                String bfueltype=cursor.getString(6);
                String bengine=cursor.getString(7);
                Double bhourrent=cursor.getDouble(8);
                Double bdayrent=cursor.getDouble(9);
                Double bweekrent=cursor.getDouble(10);
                Double bdeposit=cursor.getDouble(11);
                int bmileage=cursor.getInt(12);
                int bmodel=cursor.getInt(13);
                String bdesc=cursor.getString(14);
                int bstatus=cursor.getInt(15);
                byte[] bpic=cursor.getBlob(16);
                byte[] brc=cursor.getBlob(17);
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap bikepic = BitmapFactory.decodeByteArray(bpic, 0, bpic.length, options);
                Bitmap bikerc = BitmapFactory.decodeByteArray(brc, 0, brc.length, options);

                BikeModel newBike=new BikeModel(bid,bregno,cid,bname,bcate,bdesc,bstarttype,bengine,bfueltype,bhourrent,bdayrent,bweekrent,bdeposit,bstatus,bmileage,bmodel,bikepic,bikerc);
                returnList.add(newBike);

            }while (cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return returnList;
    }


    public List<BikeModel> viewBike(int id){
        List<BikeModel> returnList = new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + BIKE_TABLE + " WHERE " + BIKE_NO + "=?",new String[] {String.valueOf(id)} );
        if (cursor.moveToFirst()){
            do{
                int bid=cursor.getInt(0);
                String bregno=cursor.getString(1);
                String bname=cursor.getString(2);
                int cid=cursor.getInt(3);
                String bcate=cursor.getString(4);
                String bstarttype=cursor.getString(5);
                String bfueltype=cursor.getString(6);
                String bengine=cursor.getString(7);
                Double bhourrent=cursor.getDouble(8);
                Double bdayrent=cursor.getDouble(9);
                Double bweekrent=cursor.getDouble(10);
                Double bdeposit=cursor.getDouble(11);
                int bmileage=cursor.getInt(12);
                int bmodel=cursor.getInt(13);
                String bdesc=cursor.getString(14);
                int bstatus=cursor.getInt(15);
                byte[] bpic=cursor.getBlob(16);
                byte[] brc=cursor.getBlob(17);
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap bikepic = BitmapFactory.decodeByteArray(bpic, 0, bpic.length, options);
                Bitmap bikerc = BitmapFactory.decodeByteArray(brc, 0, brc.length, options);

                BikeModel newBike=new BikeModel(bid,bregno,cid,bname,bcate,bdesc,bstarttype,bengine,bfueltype,bhourrent,bdayrent,bweekrent,bdeposit,bstatus,bmileage,bmodel,bikepic,bikerc);
                returnList.add(newBike);

            }while (cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return returnList;
    }

    public List<BikeModel> viewCompanyBike(int id){
        List<BikeModel> returnList = new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + BIKE_TABLE + " WHERE " + BIKE_COMPANYID + "=?",new String[] {String.valueOf(id)} );
        if (cursor.moveToFirst()){
            do{
                int bid=cursor.getInt(0);
                String bregno=cursor.getString(1);
                String bname=cursor.getString(2);
                int cid=cursor.getInt(3);
                String bcate=cursor.getString(4);
                String bstarttype=cursor.getString(5);
                String bfueltype=cursor.getString(6);
                String bengine=cursor.getString(7);
                Double bhourrent=cursor.getDouble(8);
                Double bdayrent=cursor.getDouble(9);
                Double bweekrent=cursor.getDouble(10);
                Double bdeposit=cursor.getDouble(11);
                int bmileage=cursor.getInt(12);
                int bmodel=cursor.getInt(13);
                String bdesc=cursor.getString(14);
                int bstatus=cursor.getInt(15);
                byte[] bpic=cursor.getBlob(16);
                byte[] brc=cursor.getBlob(17);
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap bikepic = BitmapFactory.decodeByteArray(bpic, 0, bpic.length, options);
                Bitmap bikerc = BitmapFactory.decodeByteArray(brc, 0, brc.length, options);

                BikeModel newBike=new BikeModel(bid,bregno,cid,bname,bcate,bdesc,bstarttype,bengine,bfueltype,bhourrent,bdayrent,bweekrent,bdeposit,bstatus,bmileage,bmodel,bikepic,bikerc);
                returnList.add(newBike);

            }while (cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return returnList;
    }




    public boolean updateBike(BikeModel bikeModel){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        Bitmap imageToStore=bikeModel.getBikeimage();
        objectByteOutputStream=new ByteArrayOutputStream();
        imageToStore.compress(Bitmap.CompressFormat.JPEG,100,objectByteOutputStream);
        imageInByte=objectByteOutputStream.toByteArray();
        Bitmap imageToStore1=bikeModel.getBikerc();
        objectByteOutputStream1=new ByteArrayOutputStream();
        imageToStore1.compress(Bitmap.CompressFormat.JPEG,100,objectByteOutputStream1);
        imageInByte1=objectByteOutputStream1.toByteArray();
        cv.put(BIKE_REG_NO,bikeModel.getBikeregno());
        cv.put(BIKE_NAME,bikeModel.getBikename());
        cv.put(BIKE_COMPANYID,bikeModel.getCompanyid());
        cv.put(BIKE_CATEGORY,bikeModel.getBikecategory());
        cv.put(BIKE_STARTTYPE,bikeModel.getStarttype());
        cv.put(BIKE_FUELTYPE,bikeModel.getFueltype());
        cv.put(BIKE_ENGINE,bikeModel.getEngine());
        cv.put(BIKE_HOURRENT,bikeModel.getBikehourrent());
        cv.put(BIKE_DAYRENT,bikeModel.getBikedayrent());
        cv.put(BIKE_WEEKRENT,bikeModel.getBikeweekrent());
        cv.put(BIKE_DEPOSIT,bikeModel.getDeposit());
        cv.put(BIKE_MILEAGE,bikeModel.getMileage());
        cv.put(BIKE_MODEL,bikeModel.getModel());
        cv.put(BIKE_DESCRIPTION,bikeModel.getDescription());
        cv.put(BIKE_STATUS,bikeModel.getBikestatus());
        cv.put(BIKE_IMAGE,imageInByte);
        cv.put(BIKE_RC,imageInByte1);

        long update = db.update(BIKE_TABLE, cv, BIKE_NO + "=?", new String[]{String.valueOf(bikeModel.getBikeNo())});
        db.close();
        if(update==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean updateBikeStatus(int bid,int status){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(BIKE_STATUS,status);

        long update = db.update(BIKE_TABLE, cv, BIKE_NO + "=?", new String[]{String.valueOf(bid)});
        db.close();
        if(update==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean deleteBike(int bid){
        SQLiteDatabase db=this.getWritableDatabase();
        long delete=db.delete(BIKE_TABLE,BIKE_NO + "=?", new String[] {String.valueOf(bid)});
        db.close();
        if(delete==-1){
            return false;
        }
        else{
            return true;
        }
    }




    
    
    public boolean makeBooking(BookingModel bookingModel){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        Bitmap imageToStore=bookingModel.getBookingDrivingLicense();
        objectByteOutputStream=new ByteArrayOutputStream();
        imageToStore.compress(Bitmap.CompressFormat.JPEG,100,objectByteOutputStream);
        imageInByte=objectByteOutputStream.toByteArray();
        cv.put(BOOKING_CUSTOMERID,bookingModel.getBookingCustomerId());
        cv.put(BOOKING_COMPANYID,bookingModel.getBookingCompanyId());
        cv.put(BOOKING_BIKEID,bookingModel.getBookingBikeId());
        cv.put(BOOKING_TYPE,bookingModel.getBookingtype());
        cv.put(BOOKING_STATUS,bookingModel.getBookingStatus());
        cv.put(BOOKING_NOOFHOURSDAYSWEEK,bookingModel.getBookingNoOfHoursDaysWeek());
        cv.put(BOOKING_DATE,bookingModel.getBookingDate());
        cv.put(JOURNEY_DATE,bookingModel.getBookingJourneyDate());
        cv.put(RETURN_DATE,bookingModel.getBookingReturnDate());
        cv.put(DELIVERY_TIME,bookingModel.getDeliverytime());
        cv.put(RETURN_TIME,bookingModel.getReturntime());
        cv.put(BOOKING_ADDRESS,bookingModel.getBookingDeliveryAddress());
        cv.put(BOOKING_DLPHOTO,imageInByte);
        cv.put(BOOKING_DELIVERYPHOTO,"");
        cv.put(BOOKING_AMOUNT,bookingModel.getBookingAmount());
        cv.put(BOOKING_RATING,bookingModel.getBookingRating());


        long insert = db.insert(BOOKING_TABLE,null,cv);
        if(insert==-1){
            return false;
        }
        else{
            return true;
        }
    }


    public List<BookingModel> viewCustomerBooking(int customerid){
        List<BookingModel> returnList = new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + BOOKING_TABLE + " WHERE " + BOOKING_CUSTOMERID + "=?", new String[]{String.valueOf(customerid)});
        if (cursor.moveToFirst()){
            do{
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

                BookingModel newbooking=new BookingModel(bookingid,bcustomerid,bcompanyid,bbikeid,bstatus,bnoofhdw,bdate,bjdate,brdate,baddress,btype,bdtime,brtime,bdlpic,bdpic,bamount,brating);
                returnList.add(newbooking);

            }while (cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return returnList;
    }

    public List<BookingModel> viewBooking(int bid){
        List<BookingModel> returnList = new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + BOOKING_TABLE + " WHERE " + BOOKING_ID + "=?", new String[]{String.valueOf(bid)});
        if (cursor.moveToFirst()){
            do{
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

                BookingModel newbooking=new BookingModel(bookingid,bcustomerid,bcompanyid,bbikeid,bstatus,bnoofhdw,bdate,bjdate,brdate,baddress,btype,bdtime,brtime,bdlpic,bdpic,bamount,brating);
                returnList.add(newbooking);

            }while (cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return returnList;
    }


    public List<BookingModel> viewCompanyBooking(int companyid){
        List<BookingModel> returnList = new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + BOOKING_TABLE + " WHERE " + BOOKING_COMPANYID + "=?", new String[]{String.valueOf(companyid)});
        if (cursor.moveToFirst()){
            do{
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

                BookingModel newbooking=new BookingModel(bookingid,bcustomerid,bcompanyid,bbikeid,bstatus,bnoofhdw,bdate,bjdate,brdate,baddress,btype,bdtime,brtime,bdlpic,bdpic,bamount,brating);
                returnList.add(newbooking);

            }while (cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return returnList;
    }


    public Bitmap viewBookingDeliveryPhoto(int bid){
        Bitmap dlphoto = null;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+ BOOKING_DELIVERYPHOTO +" FROM " + BOOKING_TABLE + " WHERE " + BOOKING_ID + "=?", new String[]{String.valueOf(bid)});
        if (cursor.moveToFirst()) {
            byte[] bpic2 = cursor.getBlob(0);
            BitmapFactory.Options options = new BitmapFactory.Options();
            dlphoto = BitmapFactory.decodeByteArray(bpic2, 0, bpic2.length, options);
        }
        return dlphoto;
    }


    public boolean updateBookingStatus(int bid,int status){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(BOOKING_STATUS,status);

        long update = db.update(BOOKING_TABLE, cv, BOOKING_ID + "=?", new String[]{String.valueOf(bid)});
        db.close();
        if(update==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean updateBookingAddress(int bid,String address){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(BOOKING_ADDRESS,address);

        long update = db.update(BOOKING_TABLE, cv, BOOKING_ID + "=?", new String[]{String.valueOf(bid)});
        db.close();
        if(update==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean updateDeliveryPhoto(int bid,Bitmap photo){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        objectByteOutputStream=new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG,100,objectByteOutputStream);
        imageInByte=objectByteOutputStream.toByteArray();
        cv.put(BOOKING_DELIVERYPHOTO,imageInByte);

        long update = db.update(BOOKING_TABLE, cv, BOOKING_ID + "=?", new String[]{String.valueOf(bid)});
        db.close();
        if(update==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean updateBookingDetails(int bid,String ddate,String rdate,String dtime,String rtime,Bitmap img){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        objectByteOutputStream=new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG,100,objectByteOutputStream);
        imageInByte=objectByteOutputStream.toByteArray();
        cv.put(JOURNEY_DATE,ddate);
        cv.put(RETURN_DATE,rdate);
        cv.put(DELIVERY_TIME,dtime);
        cv.put(RETURN_TIME,rtime);
        cv.put(BOOKING_DLPHOTO,imageInByte);


        long update = db.update(BOOKING_TABLE, cv, BOOKING_ID + "=?", new String[]{String.valueOf(bid)});
        db.close();
        if(update==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public int getLatestBookingId() {
        int bid = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(" SELECT "+ BOOKING_ID +" FROM " + BOOKING_TABLE +" ORDER BY " + BOOKING_ID + " DESC LIMIT 1 ",null);
        if (cursor.moveToFirst())
            bid = cursor.getInt(0);
        return bid;
    }

    public int getBookingBikeId(int id) {
        int bid = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(" SELECT "+ BOOKING_BIKEID +" FROM " + BOOKING_TABLE +" WHERE " + BOOKING_ID + " =? ",new String[] {String.valueOf(id)});
        if (cursor.moveToFirst())
            bid = cursor.getInt(0);
        return bid;
    }

    public boolean cancelBooking(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        long delete=db.delete(BOOKING_TABLE,BOOKING_ID + "=?", new String[] {String.valueOf(id)});
        db.close();
        if(delete==-1){
            return false;
        }
        else{
            return true;
        }
    }







    public boolean giveFeedback(FeedbackModel feedbackModel){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(FEEDBACK_CUSTOMERID,feedbackModel.getFeedbackCustomerId());
        cv.put(FEEDBACK_COMPANYID,feedbackModel.getFeedbackCompanyId());
        cv.put(FEEDBACK_BOOKINGID,feedbackModel.getGetFeedbackBookingId());
        cv.put(FEEDBACK_COMPANY_RATING,feedbackModel.getFeedbackCompanyRating());
        cv.put(FEEDBACK_SATISFACTION,feedbackModel.getFeedbackSatisfaction());
        cv.put(FEEDBACK_SUGGESTION,feedbackModel.getFeedbackSuggestion());
        cv.put(FEEDBACK_DATE,feedbackModel.getFeedbackDate());

        long insert = db.insert(FEEDBACK_TABLE,null,cv);
        if(insert==-1){
            return false;
        }
        else{
            return true;
        }
    }


    public List<FeedbackModel> viewFeedback(int id){
        List<FeedbackModel> returnList = new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + FEEDBACK_TABLE + " WHERE " + FEEDBACK_BOOKINGID + "=?",new String[] {String.valueOf(id)});
        if (cursor.moveToFirst()){
            do{
                int fid=cursor.getInt(0);
                int fuserid=cursor.getInt(1);
                int fCompanyid=cursor.getInt(2);
                int frequestid=cursor.getInt(3);
                float fCompanyrate = cursor.getFloat(4);
                String fsatisfaction=cursor.getString(5);
                String fsuggestion=cursor.getString(6);
                String fdate=cursor.getString(7);

                FeedbackModel newfeedback=new FeedbackModel(fid,fuserid,fCompanyid,frequestid,fCompanyrate,fsatisfaction,fsuggestion,fdate);
                returnList.add(newfeedback);


            }while (cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return returnList;
    }




    public boolean makeBookingPayment(PaymentModel paymentModel){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(PAYMENT_ID,paymentModel.getPaymentId());
        cv.put(PAYMENT_CUSTOMERID,paymentModel.getPaymentCustomerId());
        cv.put(PAYMENT_BOOKINGID,paymentModel.getPaymentBookingId());
        cv.put(PAYMENT_STATUS,paymentModel.getPaymentStatus());
        cv.put(PAYMENT_DATE,paymentModel.getPaymentDate());
        cv.put(PAYMENT_AMOUNT,paymentModel.getPaymentAmount());

        long insert = db.insert(PAYMENT_TABLE,null,cv);
        if(insert==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public List<PaymentModel> viewPayment(int id){
        List<PaymentModel> returnList = new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + PAYMENT_TABLE + " WHERE " + PAYMENT_BOOKINGID + "=?",new String[] {String.valueOf(id)});
        if (cursor.moveToFirst()){
            do{
                String pid=cursor.getString(0);
                int puserid=cursor.getInt(1);
                int pbookingid=cursor.getInt(2);
                int pstatus = cursor.getInt(3);
                String pdate=cursor.getString(4);
                double pamount=cursor.getDouble(5);

                PaymentModel newpayment=new PaymentModel(pid,puserid,pbookingid,pstatus,pdate,pamount);
                returnList.add(newpayment);

            }while (cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return returnList;
    }

}
