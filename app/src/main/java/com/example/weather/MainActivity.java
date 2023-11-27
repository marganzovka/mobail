package com.example.weather;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText cityField;
    String nameCity, response;
    Button buttonSend, buttonDetalic, buttonSevenDays;
    TextView titleField, nameWheatherField, tempField, humidityField, speedWindField, cloudField;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Добавление UI
        buttonSend = findViewById(R.id.buttonSend);
        buttonDetalic = findViewById(R.id.buttonDetalic);
        cityField = findViewById(R.id.cityField);
        titleField = findViewById(R.id.nameCity);
        nameWheatherField = findViewById(R.id.nameWheather);
        tempField = findViewById(R.id.temperature);
        humidityField = findViewById(R.id.humidity);
        speedWindField = findViewById(R.id.speedWind);
        cloudField = findViewById(R.id.cloud);
        buttonSevenDays = findViewById(R.id.sevenDays);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получаем название города из EditText
                nameCity = cityField.getText().toString();
                titleField.setText("Загрузка...");


                // Выполняем сетевые операции в фоновом потоке
                new FetchWeatherTask().execute(nameCity);
            }
        });


        buttonDetalic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detalicAct = new Intent(getApplicationContext(),Detalics.class);
                detalicAct.putExtra("jsonData", response);
                startActivity(detalicAct);

            }
        });

        buttonSevenDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sevenDayAct = new Intent(getApplicationContext(), sevenDays.class);
                sevenDayAct.putExtra("nameCity", nameCity);
                startActivity(sevenDayAct);
            }
        });



    }

    // AsyncTask для выполнения сетевых операций в фоновом потоке
    private class FetchWeatherTask extends AsyncTask<String, Void, String> {

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
                titleField.setText(String.valueOf(nameCity));
                response = result;

                Gson gson = new Gson();
                Wheather wheather = gson.fromJson(result, Wheather.class);
                List<Wheather.Info> weatherList = wheather.getWeatherList();

                for (Wheather.Info weather : weatherList) {
                    nameWheatherField.setText(String.valueOf(weather.getDescription()));
                }

                Wheather.mainInfo mainList = wheather.getMainList();
                int temperature = (int) mainList.getTemp();
                tempField.setText(String.valueOf(temperature) + "°");

                int humidity = (int) mainList.getHumidity();
                humidityField.setText(String.valueOf(humidity + "%"));

                Wheather.windInfo windInfo = wheather.getWindList();
                double wind =  windInfo.getWind();
                speedWindField.setText(String.valueOf(wind) + "m/s");

                Wheather.cloudsInfo cloudsInfo = wheather.getcloudsInfo();
                double cloud = cloudsInfo.getCloud();
                cloudField.setText(String.valueOf(cloud) + "%");





            } else {
                // Обработка ошибки, если не удалось выполнить сетевой запрос
                titleField.setText(String.valueOf("Error 404"));
            }
        }
    }





}
//                        nameApp.setText(String.valueOf(base));
// https://api.openweathermap.org/data/2.5/weather?q=Brest&appid=7cede650cf502fdfb397184a7367a08d&units=metric&lang=ru

