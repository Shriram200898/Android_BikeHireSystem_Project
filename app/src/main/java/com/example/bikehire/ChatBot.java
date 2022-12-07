package com.example.bikehire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bikehire.Adapter.ChatAdapter;
import com.example.bikehire.Api.RetrofitApi;
import com.example.bikehire.Model.ChatModel;
import com.example.bikehire.Model.MsgModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.Request;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatBot extends AppCompatActivity {
    private RecyclerView chatRV;
    private EditText userMsgEdit;
    private FloatingActionButton sendMsg;
    private final String BOT_KEY = "bot";
    private final String USER_KEY = "user";
    private ArrayList<ChatModel> chatModelArrayList;
    private ChatAdapter chatAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chatRV=findViewById(R.id.chat);
        userMsgEdit=findViewById(R.id.txtmsg);
        sendMsg=findViewById(R.id.msgsend);

        chatModelArrayList = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatModelArrayList,this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        chatRV.setLayoutManager(manager);
        chatRV.setAdapter(chatAdapter);





        userMsgEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0){
                    sendMsg.setVisibility(View.INVISIBLE);
                } else {
                    sendMsg.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userMsgEdit.getText().toString().isEmpty()){
                    Toast.makeText(ChatBot.this,"Please enter your message",Toast.LENGTH_SHORT).show();
                    return;
                }
                getResponse(userMsgEdit.getText().toString());
                userMsgEdit.setText("");
            }
        });

    }


        private void getResponse(String message){
        chatModelArrayList.add(new ChatModel(message,USER_KEY));
        chatAdapter.notifyDataSetChanged();
        String url = "http://api.brainshop.ai/get?bid=168498&key=EsiwfjIZSKsKIThL&uid=[uid]&msg="+message;
        String BASE_URL = "http://api.brainshop.ai/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        Call<MsgModel> call=retrofitApi.getMessage(url);
        call.enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {
                if(response.isSuccessful()){
                    MsgModel modal = response.body();
                    chatModelArrayList.add(new ChatModel(modal.getCnt(),BOT_KEY));
                    chatAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MsgModel> call, Throwable t) {
                    chatModelArrayList.add(new ChatModel("Please revert your question",BOT_KEY));
                    chatAdapter.notifyDataSetChanged();
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
                Intent intent = new Intent(ChatBot.this,UserDashboard.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                Intent intent1 = new Intent(ChatBot.this,UserLogin.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}