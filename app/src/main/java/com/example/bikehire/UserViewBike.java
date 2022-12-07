package com.example.bikehire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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

import com.example.bikehire.Adapter.RecyclerViewAdapterUserViewBike;
import com.example.bikehire.Model.BikeModel;

import java.util.List;

public class UserViewBike extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapterUserViewBike mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ImageView img;
    TextView name, email;
    private SearchView searchBike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_bike);

        img = findViewById(R.id.imageuser1);
        name = findViewById(R.id.username1);
        email = findViewById(R.id.useremail1);
        searchBike = findViewById(R.id.search_userbike);


        Intent intent = getIntent();
        String str = intent.getStringExtra("catename");
        String str5 = intent.getStringExtra("userid");
        String str2 = intent.getStringExtra("name");
        String str1 = intent.getStringExtra("email");
        Bitmap str3 = intent.getParcelableExtra("photo");
        email.setText(str1);
        name.setText(str2);
        if (str3 == null) {

        } else {
            img.setImageBitmap(str3);
        }

        Intent i = new Intent(UserViewBike.this, RecyclerViewAdapterUserViewBike.class);
        i.putExtra("userid", str5);
        i.putExtra("name",str2);
        i.putExtra("email",str1);
        i.putExtra("photo",str3);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewuserbike);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayout = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayout);
        DatabaseHelper dbhelper = new DatabaseHelper(UserViewBike.this);
        List<BikeModel> all = dbhelper.viewCategoryBike(str);
        mAdapter = new RecyclerViewAdapterUserViewBike(all, UserViewBike.this);
        recyclerView.setAdapter(mAdapter);


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
                Intent intent = new Intent(UserViewBike.this,UserDashboard.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                Intent intent1 = new Intent(UserViewBike.this,UserLogin.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}


