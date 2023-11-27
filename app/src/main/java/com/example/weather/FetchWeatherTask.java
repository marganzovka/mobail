package com.example.weather;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
}
