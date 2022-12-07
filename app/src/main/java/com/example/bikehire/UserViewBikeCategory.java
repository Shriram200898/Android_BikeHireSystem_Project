package com.example.bikehire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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

import com.example.bikehire.Adapter.RecyclerViewAdapterAdminCategory;
import com.example.bikehire.Adapter.RecyclerViewAdapterUserBikeCategory;
import com.example.bikehire.Model.CategoryModel;

import java.util.List;

public class UserViewBikeCategory extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapterUserBikeCategory mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView fullname,email;
    private SearchView searchCategory;
    private ImageView profilephoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_bike_category);

        recyclerView=(RecyclerView) findViewById(R.id.recyclerviewuserbikecategory);
        searchCategory=findViewById(R.id.search_userbikecate);
        fullname = findViewById(R.id.username2);
        email = findViewById(R.id.useremail2);
        profilephoto = findViewById(R.id.imageuser2);

        Intent intent = getIntent();
        String str = intent.getStringExtra("userid");
        String str2 = intent.getStringExtra("name");
        String str1 = intent.getStringExtra("email");
        Bitmap str3 = intent.getParcelableExtra("photo");
        email.setText(str1);
        fullname.setText(str2);
        if (str3 == null) {

        } else {
            profilephoto.setImageBitmap(str3);
        }


        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayout = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayout);
        DatabaseHelper dbhelper = new DatabaseHelper(UserViewBikeCategory.this);
        List<CategoryModel> all=dbhelper.viewAllCategory();
        mAdapter = new RecyclerViewAdapterUserBikeCategory(all,str,str2,str1,str3,UserViewBikeCategory.this);
        recyclerView.setAdapter(mAdapter);

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
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item1:
                Intent intent = new Intent(UserViewBikeCategory.this,UserDashboard.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                Intent intent1 = new Intent(UserViewBikeCategory.this,UserLogin.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}