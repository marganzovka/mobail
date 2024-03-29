package com.example.weather;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchWeatherTask extends AsyncTask<String, Void, String> {

    private OnTaskCompleted listener;

    public interface OnTaskCompleted {
        void onTaskCompleted(String result);
    }

    public FetchWeatherTask(OnTaskCompleted listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String urlString = params[0];

        try {
            URL obj = new URL(urlString);
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
        if (listener != null) {
            listener.onTaskCompleted(result);
        }
    }
}

