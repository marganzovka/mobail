package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

public class Map extends AppCompatActivity {

    ImageButton buttonBack;
    String cityName, responce;
    TextView title;
    WebView map;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seven_days);

        Intent intent = getIntent();
        cityName = intent.getStringExtra("nameCity");

        buttonBack = findViewById(R.id.buttonBack);
        map = findViewById(R.id.map);
        WebSettings webSettings = map.getSettings();
        webSettings.setJavaScriptEnabled(true);
        map.loadUrl("https://openweathermap.org/weathermap?basemap=map&cities=false&layer=temperature&lat=30&lon=-20&zoom=3");

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       // http://api.openweathermap.org/geo/1.0/direct?q=brest&appid=7cede650cf502fdfb397184a7367a08d
    }






}

