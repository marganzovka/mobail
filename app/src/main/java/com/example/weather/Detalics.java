package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Detalics extends AppCompatActivity {



    ImageButton buttonBack;
    TextView temperatureField, nameWeatherField, feelsField, levelField, visibilityField, locationField, sunSetField, sunRiseField;
    Intent mainAct;
    String response, numberIcon, mainWeather, countryName, cityName;
    Long  sunSet, sunRise;
    ImageView img;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalics);


        Intent intent = getIntent();
        response = intent.getStringExtra("jsonData");

        temperatureField = findViewById(R.id.temperature);
        buttonBack = findViewById(R.id.buttonBack);
        img = findViewById(R.id.image);
        nameWeatherField = findViewById(R.id.nameWheather);
        feelsField = findViewById(R.id.feels);
        levelField = findViewById(R.id.level);
        visibilityField = findViewById(R.id.visibility);
        locationField = findViewById(R.id.location);
        sunRiseField = findViewById(R.id.sunRise);
        sunSetField = findViewById(R.id.sunSet);





        Gson gson = new Gson();
        Wheather wheather = gson.fromJson(response, Wheather.class);
        List<Wheather.Info> weatherList = wheather.getWeatherList();

        for (Wheather.Info weather : weatherList) {
            numberIcon = weather.getIcon();
            mainWeather = weather.getMain();
        }
        ChangeIcon(numberIcon);
        nameWeatherField.setText(String.valueOf(mainWeather));

        Wheather.mainInfo mainList = wheather.getMainList();
        int temp = (int) mainList.getTemp();
        temperatureField.setText("Temperature: " + String.valueOf(temp) + "°");

        int feels = (int) mainList.getFeelsLike();
        feelsField.setText("Feels: " + String.valueOf(feels) + "°");

        int level = (int) mainList.getLevel();
        levelField.setText("Level: " + String.valueOf(level) + "hPa");

        double visibility = (wheather.getVisibility()) / 1000;
        visibilityField.setText("Visibility: " + String.valueOf(visibility) + "km");

        Wheather.getSysList sysInfo = wheather.getSysInfo();
        countryName = sysInfo.getCountry();
        cityName = wheather.getNameCity();
        locationField.setText("Location: " + countryName + ", " + cityName);

        sunSet = sysInfo.getSunset();
        sunRise = sysInfo.getSunrise();
        sunSetField.setText("SunRise: " + ConvertTime(sunSet));
        sunRiseField.setText("SunSet: " + ConvertTime(sunRise));




        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

    }



    private void ChangeIcon(String icon){

        if ("01d".equals(icon) || "01n".equals(icon)) img.setImageResource(R.drawable.sun);
        else if ("02d".equals(icon) || "02n".equals(icon)) img.setImageResource(R.drawable.small_clouds);
        else if ("03d".equals(icon) || "03n".equals(icon)) img.setImageResource(R.drawable.clouds);
        else if ("04d".equals(icon) || "04n".equals(icon)) img.setImageResource(R.drawable.clous_split);
        else if ("09d".equals(icon) || "09n".equals(icon)) img.setImageResource(R.drawable.big_rain);
        else if ("10d".equals(icon) || "10n".equals(icon)) img.setImageResource(R.drawable.rain);
        else if ("11d".equals(icon) || "11n".equals(icon)) img.setImageResource(R.drawable.shtorm);
        else if ("13d".equals(icon) || "13n".equals(icon)) img.setImageResource(R.drawable.snow);
        else if ("50d".equals(icon) || "50n".equals(icon)) img.setImageResource(R.drawable.ghost);
        else {
            img.setImageResource(R.drawable.back);
        }
    }

    private String ConvertTime(long unixSeconds){

        // преобразование секунд в миллисекунды
        Date date = new Date(unixSeconds*1000L);
        // формат даты
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss z");
        // дать ссылку на часовой пояс для форматирования
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

}