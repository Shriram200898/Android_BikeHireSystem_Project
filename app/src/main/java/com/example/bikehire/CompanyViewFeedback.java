package com.example.bikehire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.bikehire.Model.FeedbackModel;
import com.example.bikehire.Model.UserModel;

import java.util.List;

public class CompanyViewFeedback extends AppCompatActivity {
    private TextView fdate,fbookingid,fusername,femailid,fsatisfaction,fsuggestion,frating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_view_feedback);

        fdate = findViewById(R.id.view_fdate);
        fbookingid = findViewById(R.id.view_frequestid);
        fusername = findViewById(R.id.view_fusername);
        femailid = findViewById(R.id.view_femailid);
        fsatisfaction = findViewById(R.id.view_fsatisfaction);
        fsuggestion = findViewById(R.id.view_fsuggestion);
        frating = findViewById(R.id.view_frating);

        Intent i = getIntent();
        String id = i.getStringExtra("bookingId");
        int bookingId = Integer.parseInt(id);

        DatabaseHelper db=new DatabaseHelper(CompanyViewFeedback.this);
        List<FeedbackModel> all = db.viewFeedback(bookingId);
        if(all.isEmpty()){
        }
        else{
            int customerId = all.get(0).getFeedbackCustomerId();
            String date= all.get(0).getFeedbackDate();
            String satis = all.get(0).getFeedbackSatisfaction();
            String sugg = all.get(0).getFeedbackSuggestion();
            float rate = all.get(0).getFeedbackCompanyRating();
            List<UserModel> all1 = db.viewOneUser(customerId);
            String name = all1.get(0).getName();
            String email = all1.get(0).getEmail();
            fdate.setText("Booked On, "+date);
            fbookingid.setText("Booking Id : "+bookingId);
            femailid.setText(email);
            fusername.setText(name);
            fsatisfaction.setText(satis);
            fsuggestion.setText(sugg);
            frating.setText(" "+String.valueOf(rate));
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
                Intent intent = new Intent(CompanyViewFeedback.this,CompanyDashboard.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                Intent intent1 = new Intent(CompanyViewFeedback.this,CompanyLogin.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}