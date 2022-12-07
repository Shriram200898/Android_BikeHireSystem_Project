package com.example.bikehire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.bikehire.Model.FeedbackModel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserGiveFeedback extends AppCompatActivity {
    private EditText fSuggestion;
    private RatingBar fRating;
    private RadioGroup fSatisfaction;
    private RadioButton verysatisfied,satisfied,neutral,unsatisfied,veryunsatisfied;
    private Button fbutton;
    private int fid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_give_feedback);

        fRating = findViewById(R.id.feedback_rating);
        fSuggestion = findViewById(R.id.feedback_suggestion);
        fSatisfaction = findViewById(R.id.feedback_satisfaction);
        verysatisfied = findViewById(R.id.verysatisfied);
        satisfied = findViewById(R.id.satisfied);
        neutral = findViewById(R.id.neutral);
        unsatisfied = findViewById(R.id.unsatisfied);
        veryunsatisfied = findViewById(R.id.veryunsatisfied);
        fbutton = findViewById(R.id.feedback_btn);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateandTime = sdf.format(new Date());

        Intent i = getIntent();
        String str = i.getStringExtra("bookId");
        String str1 = i.getStringExtra("customerId");
        String str2 = i.getStringExtra("companyId");
        int bookid = Integer.parseInt(str);
        int customerid = Integer.parseInt(str1);
        int companyid = Integer.parseInt(str2);

        DatabaseHelper DB = new DatabaseHelper(UserGiveFeedback.this);

        List<FeedbackModel> all = DB.viewFeedback(bookid);
        if (all.isEmpty()) {
            fbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseHelper DB = new DatabaseHelper(UserGiveFeedback.this);
                    float frate=fRating.getRating();
                    String fsu=fSuggestion.getText().toString();
                    String fsa=((RadioButton)findViewById(fSatisfaction.getCheckedRadioButtonId())).getText().toString();

                    float allrating = DB.getCompanyRating(companyid);
                    System.out.println(allrating);
                    float avgrating = (float) ((allrating+frate)/2.0);
                    double f1 = (Math.round(avgrating*10.0)/10.0);
                    boolean success1 = DB.updateCompanyRating(companyid,(float) f1);


                    if(success1==true){
                        Toast.makeText(UserGiveFeedback.this, "You Rated :  "+frate, Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(UserGiveFeedback.this, "Unable to Rate..."  , Toast.LENGTH_LONG).show();
                    }


                    if(TextUtils.isEmpty(fsu) || TextUtils.isEmpty(fsa))
                        Toast.makeText(UserGiveFeedback.this,"All fields Required..",Toast.LENGTH_SHORT).show();
                    else{
                        FeedbackModel feedbackModel = new FeedbackModel(-1,customerid,companyid,bookid,frate,fsa,fsu,currentDateandTime);
                        boolean success = DB.giveFeedback(feedbackModel);
                        if(success==true){
                            Toast.makeText(UserGiveFeedback.this, "Thank you for valuable feedback...!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(UserGiveFeedback.this,UserViewBooking.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(UserGiveFeedback.this, "Unable to give feedback...!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        }
        else {
            int fid = all.get(0).getFeedbackId();
            String sugg = all.get(0).getFeedbackSuggestion();
            float rate = all.get(0).getFeedbackCompanyRating();
            String sati = all.get(0).getFeedbackSatisfaction();

            if((sugg==null))
            {
                fSuggestion.setText("");
            }else{
                fSuggestion.setText(sugg);
                fSuggestion.setEnabled(false);
                fbutton.setClickable(false);
            }
            if((rate==0))
            {
                fRating.setRating(0);
            }else{
                fRating.setRating(rate);
                fRating.setEnabled(false);
                fbutton.setClickable(false);
            }
            if((sati==null)) {
                verysatisfied.setChecked(false);
                satisfied.setChecked(false);
                neutral.setChecked(false);
                unsatisfied.setChecked(false);
                veryunsatisfied.setChecked(false);

            }else{
                if(sati.equals("Very Satisfied")){
                    verysatisfied.setChecked(true);
                    verysatisfied.setEnabled(false);
                    satisfied.setEnabled(false);
                    neutral.setEnabled(false);
                    unsatisfied.setEnabled(false);
                    veryunsatisfied.setEnabled(false);
                }
                if(sati.equals("Satisfied")){
                    satisfied.setChecked(true);
                    verysatisfied.setEnabled(false);
                    satisfied.setEnabled(false);
                    neutral.setEnabled(false);
                    unsatisfied.setEnabled(false);
                    veryunsatisfied.setEnabled(false);
                }
                if(sati.equals("Neutral")){
                    neutral.setChecked(true);
                    verysatisfied.setEnabled(false);
                    satisfied.setEnabled(false);
                    neutral.setEnabled(false);
                    unsatisfied.setEnabled(false);
                    veryunsatisfied.setEnabled(false);
                }
                if(sati.equals("Unsatisfied")){
                    unsatisfied.setChecked(true);
                    verysatisfied.setEnabled(false);
                    satisfied.setEnabled(false);
                    neutral.setEnabled(false);
                    unsatisfied.setEnabled(false);
                    veryunsatisfied.setEnabled(false);
                }
                if(sati.equals("Very Unsatisfied")){
                    veryunsatisfied.setChecked(true);
                    verysatisfied.setEnabled(false);
                    satisfied.setEnabled(false);
                    neutral.setEnabled(false);
                    unsatisfied.setEnabled(false);
                    veryunsatisfied.setEnabled(false);
                }
            }

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
                Intent intent = new Intent(UserGiveFeedback.this,UserDashboard.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                Intent intent1 = new Intent(UserGiveFeedback.this,UserLogin.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}