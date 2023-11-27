package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class sevenDays extends AppCompatActivity {

    ImageButton buttonBack;
    String cityName;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seven_days);

        Intent intent = getIntent();
        cityName = intent.getStringExtra("nameCity");

        buttonBack = findViewById(R.id.buttonBack);
        title = findViewById(R.id.nameApp);

        title.setText(cityName);



        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


}

 class FetchWeatherTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        String cityName = params[0];
        String key = "7cede650cf502fdfb397184a7367a08d";
        String urlStringToday = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + key + "&units=metric&lang=en";

        try {
            URL obj = new URL(urlStringToday);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error" + e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            // Обновляем UI на основе результата
            System.out.println("Response: " + result);


            Gson gson = new Gson();
            Wheather wheather = gson.fromJson(result, Wheather.class);
            List<Wheather.Info> weatherList = wheather.getWeatherList();



        } else {
            // Обработка ошибки, если не удалось выполнить сетевой запрос
            System.out.println(String.valueOf("Error 404"));
        }
    }
}