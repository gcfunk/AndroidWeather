package com.example.gregfunk.androidweather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            /*String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader r = new BufferedReader(reader);
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line);
                }
                return total.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;*/

            String result = "{\"coord\":{\"lon\":-0.13,\"lat\":51.51},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"base\":\"cmc stations\",\"main\":{\"temp\":282.908,\"pressure\":1015.25,\"humidity\":100,\"temp_min\":282.908,\"temp_max\":282.908,\"sea_level\":1025.44,\"grnd_level\":1015.25},\"wind\":{\"speed\":2.37,\"deg\":290.5},\"rain\":{\"3h\":2.83},\"clouds\":{\"all\":92},\"dt\":1447939868,\"sys\":{\"message\":0.0045,\"country\":\"GB\",\"sunrise\":1447917935,\"sunset\":1447949147},\"id\":2643743,\"name\":\"London\",\"cod\":200}";
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(result);
                String weatherInfo = jsonObject.getString("weather");
                JSONArray arr = new JSONArray(weatherInfo);
                String message = "";
                for( int i=-0; i< arr.length(); i++) {
                    JSONObject jsonPart = arr.getJSONObject(i);

                    String main = jsonPart.getString("main");
                    String description = jsonPart.getString("description");
                    if (main != "" && description != "") {
                        message += main + " : " + description + "\r\n";
                    }
                }
                weatherTextView.setText(message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    EditText cityName;
    TextView weatherTextView;

    public void findWeather(View view) {
        DownloadTask task = new DownloadTask();
        String result = null;
        try {
            result = task.execute("http://api.openweathermap.org/data/2.5/weather?q=" + cityName.getText().toString()).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName = (EditText) findViewById(R.id.cityName);
        weatherTextView = (TextView) findViewById(R.id.weatherTextView);
    }
}
