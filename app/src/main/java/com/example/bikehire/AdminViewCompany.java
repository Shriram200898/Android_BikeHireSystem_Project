package com.example.bikehire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.bikehire.Adapter.RecyclerViewAdapterAdminViewCompany;
import com.example.bikehire.Model.CompanyModel;

import java.util.List;

public class AdminViewCompany extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapterAdminViewCompany mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    SearchView searchCompany;
    TextView fullname,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_company);

        searchCompany=findViewById(R.id.search_company);
        fullname = findViewById(R.id.adminname1);
        email = findViewById(R.id.adminemail1);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerviewcomp);


        Intent i = getIntent();
        String name = i.getStringExtra("name");
        String aemail = i.getStringExtra("email");
        fullname.setText(name);
        email.setText(aemail);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DatabaseHelper dbhelper = new DatabaseHelper(AdminViewCompany.this);
        List<CompanyModel> all=dbhelper.viewAllCompany();
        mAdapter = new RecyclerViewAdapterAdminViewCompany(all,AdminViewCompany.this);
        recyclerView.setAdapter(mAdapter);

        searchCompany.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
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
                Intent intent = new Intent(AdminViewCompany.this,AdminDashboard.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                Intent intent1 = new Intent(AdminViewCompany.this,AdminLogin.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}