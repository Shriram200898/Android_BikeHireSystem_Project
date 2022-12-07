package com.example.bikehire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.bikehire.Adapter.RecyclerViewAdapterUserBooking;
import com.example.bikehire.Model.BikeModel;
import com.example.bikehire.Model.BookingModel;

import java.util.List;

public class UserViewBooking extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapterUserBooking mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    TextView name,email;
    ImageView img;
    private SearchView searchbooking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_booking);

        img=findViewById(R.id.imageuser4);
        name=findViewById(R.id.username4);
        email=findViewById(R.id.useremail4);
        searchbooking=findViewById(R.id.search_userbooking);


        Intent intent = getIntent();
        String st = intent.getStringExtra("userid");
        int userid = Integer.parseInt(st);
        String str1 = intent.getStringExtra("email");
        String str2 = intent.getStringExtra("name");
        Bitmap str3 = intent.getParcelableExtra("photo");
        email.setText(str1);
        name.setText(str2);

        if(str3 == null){

        }
        else{
            img.setImageBitmap(str3);
        }


        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewuserbooking);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DatabaseHelper dbhelper = new DatabaseHelper(UserViewBooking.this);
        List<BookingModel> all = dbhelper.viewCustomerBooking(userid);
        mAdapter = new RecyclerViewAdapterUserBooking(all,UserViewBooking.this);
        recyclerView.setAdapter(mAdapter);

        searchbooking.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });

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
                Intent intent = new Intent(UserViewBooking.this,UserDashboard.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                Intent intent1 = new Intent(UserViewBooking.this,UserLogin.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}