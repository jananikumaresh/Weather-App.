package com.example.weatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    EditText editCity;
    Button btnSearch;
    TextView txtResult;

    String apiKey = "4af5e886795bd4aee443acc0d1462b6b";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editCity = findViewById(R.id.editCity);
        btnSearch = findViewById(R.id.btnSearch);
        txtResult = findViewById(R.id.txtResult);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String city = editCity.getText().toString();

                String url =
                        "https://api.openweathermap.org/data/2.5/weather?q="
                                + city +
                                "&appid=" + apiKey +
                                "&units=metric";

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                JsonObjectRequest request =
                        new JsonObjectRequest(
                                Request.Method.GET,
                                url,
                                null,

                                response -> {
                                    try {

                                        String temp = response
                                                .getJSONObject("main")
                                                .getString("temp");

                                        String weather = response
                                                .getJSONArray("weather")
                                                .getJSONObject(0)
                                                .getString("main");

                                        txtResult.setText(
                                                "Temperature: " + temp + "°C\n" + "Weather: " + weather
                                        );

                                    } catch (Exception e) {
                                        txtResult.setText("Error");
                                    }
                                },

                                error -> txtResult.setText("City not found")
                        );

                queue.add(request);
            }
        });
    }
}