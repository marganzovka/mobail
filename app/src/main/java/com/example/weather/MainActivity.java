package com.example.weather;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity implements FetchWeatherTask.OnTaskCompleted{
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
                performWeatherTask(nameCity);
            }
        });


        buttonDetalic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (response == null){
                    Toast warning = new Toast(MainActivity.this);
                    warning.makeText(MainActivity.this, "Please fill in the 'City' field and click the 'Today' button. After that, you will be able to proceed to the details.", Toast.LENGTH_LONG).show();

                }else {
                    Intent detalicAct = new Intent(getApplicationContext(),Detalics.class);
                    detalicAct.putExtra("jsonData", response);
                    startActivity(detalicAct);
                }


            }
        });

        buttonSevenDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sevenDayAct = new Intent(getApplicationContext(), Map.class);
                sevenDayAct.putExtra("nameCity", nameCity);
                startActivity(sevenDayAct);
            }
        });



    }

    private void performWeatherTask(String cityName) {
        String key = "7cede650cf502fdfb397184a7367a08d";
        String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + key + "&units=metric&lang=en";

        FetchWeatherTask weatherTask = new FetchWeatherTask(this);
        weatherTask.execute(urlString);
    }

    @Override
    public void onTaskCompleted(String result) {
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






//                        nameApp.setText(String.valueOf(base));
// https://api.openweathermap.org/data/2.5/weather?q=Brest&appid=7cede650cf502fdfb397184a7367a08d&units=metric&lang=ru

