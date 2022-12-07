package com.example.bikehire;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
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

import com.example.bikehire.Adapter.RecyclerViewAdapterCompanyBooking;
import com.example.bikehire.Model.BookingModel;

import java.util.List;

public class CompanyViewBooking extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapterCompanyBooking mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    TextView email,name;
    ImageView profile;
    private SearchView searchcompanybooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_view_booking);

        email = findViewById(R.id.cpemail4);
        name = findViewById(R.id.cpname4);
        profile = findViewById(R.id.imgcp4);
        searchcompanybooking = findViewById(R.id.search_companybooking);


        Intent intent = getIntent();
        String str = intent.getStringExtra("id");
        int compid = Integer.parseInt(str);
        String str1 = intent.getStringExtra("email");
        String str2 = intent.getStringExtra("name");
        Bitmap str3 = intent.getParcelableExtra("photo");
        email.setText(str1);
        name.setText(str2);

        if(str3 == null){

        }
        else{
            profile.setImageBitmap(str3);
        }

        recyclerView=(RecyclerView) findViewById(R.id.recyclerviewmechrequest);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DatabaseHelper dbhelper = new DatabaseHelper(CompanyViewBooking.this);
        List<BookingModel> all=dbhelper.viewCompanyBooking(compid);
        mAdapter = new RecyclerViewAdapterCompanyBooking(all, CompanyViewBooking.this);
        recyclerView.setAdapter(mAdapter);

        searchcompanybooking.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
                Intent intent = new Intent(CompanyViewBooking.this,CompanyDashboard.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                Intent intent1 = new Intent(CompanyViewBooking.this,CompanyLogin.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
