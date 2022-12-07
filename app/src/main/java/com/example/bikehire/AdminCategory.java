package com.example.bikehire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.bikehire.Adapter.RecyclerViewAdapterAdminCategory;
import com.example.bikehire.Model.CategoryModel;

import java.util.List;

public class AdminCategory extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapterAdminCategory mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button addcategory;
    private TextView fullname,email;
    private SearchView searchCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        recyclerView=(RecyclerView) findViewById(R.id.recyclerviewcategory);
        addcategory=(Button) findViewById(R.id.addnewcategory);
        searchCategory=findViewById(R.id.search_admincategory);
        fullname = findViewById(R.id.adminname1);
        email = findViewById(R.id.adminemail1);

        Intent i = getIntent();
        String name = i.getStringExtra("name");
        String aemail = i.getStringExtra("email");
        fullname.setText(name);
        email.setText(aemail);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DatabaseHelper dbhelper = new DatabaseHelper(AdminCategory.this);
        List<CategoryModel> all=dbhelper.viewAllCategory();
        mAdapter = new RecyclerViewAdapterAdminCategory(all,name,aemail,AdminCategory.this);
        recyclerView.setAdapter(mAdapter);
        addcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategory.this,AdminAddCategory.class);
                intent.putExtra("name",name);
                intent.putExtra("email",aemail);
                startActivity(intent);
            }
        });

        searchCategory.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
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
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item1:
                Intent intent = new Intent(AdminCategory.this,AdminDashboard.class);
                startActivity(intent);
                return true;
            case R.id.item2:
                Intent intent1 = new Intent(AdminCategory.this,AdminLogin.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}