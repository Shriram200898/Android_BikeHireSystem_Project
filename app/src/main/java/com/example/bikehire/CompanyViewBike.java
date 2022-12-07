package com.example.bikehire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.bikehire.Adapter.RecyclerViewAdapterCompanyViewBike;
import com.example.bikehire.Model.BikeModel;

import java.util.List;

public class CompanyViewBike extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapterCompanyViewBike mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView addnew,fullname,email;
    private SearchView searchBike;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_view_bike);

        addnew = (Button) findViewById(R.id.addnewbike);
        searchBike=findViewById(R.id.search_cpbike);
        fullname = findViewById(R.id.cpname2);
        email = findViewById(R.id.cpemail2);
        img = findViewById(R.id.imgcp2);

        Intent i = getIntent();
        String id = i.getStringExtra("id");
        String name = i.getStringExtra("name");
        String aemail = i.getStringExtra("email");
        Bitmap str3 = i.getParcelableExtra("photo");
        fullname.setText(name);
        email.setText(aemail);
        if (str3 == null) {

        } else {
            img.setImageBitmap(str3);
        }


        recyclerView=(RecyclerView) findViewById(R.id.recyclerviewbike);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DatabaseHelper dbhelper = new DatabaseHelper(CompanyViewBike.this);
        int company_id=dbhelper.getCompanyId(aemail);
        List<BikeModel> all=dbhelper.viewCompanyBike(company_id);
        mAdapter = new RecyclerViewAdapterCompanyViewBike(all,name,aemail,str3,CompanyViewBike.this);
        recyclerView.setAdapter(mAdapter);

        addnew.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(CompanyViewBike.this, CompanyAddBike.class);
                i.putExtra("name",name);
                i.putExtra("email",aemail);
                startActivity(i);
            }
        });

        searchBike.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
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
                Intent intent = new Intent(CompanyViewBike.this,CompanyDashboard.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                Intent intent1 = new Intent(CompanyViewBike.this,CompanyLogin.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}