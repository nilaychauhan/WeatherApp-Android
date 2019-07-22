package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText editText;
    TextView textView,result;

    //https://api.openweathermap.org/data/2.5/weather?q=Paris&appid=c126e857e7352e28f3a69006575b0f8a
    String BASEURL = "https://api.openweathermap.org/data/2.5/weather?q=";
    String API = "&appid=c126e857e7352e28f3a69006575b0f8a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        result = findViewById(R.id.result);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String MYURL = BASEURL + editText.getText().toString() + API;
//                if(editText.getText().toString() == null){
//
                final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, MYURL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String info = response.getString("weather");
                            JSONArray array =new JSONArray(info);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject parOBJ = array.getJSONObject(i);
                                String myWeather = parOBJ.getString("main");
                                result.setText(myWeather);

                            }

                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                WeatherSingleton.getInstance(MainActivity.this).addToRequestQue(jsonObjectRequest);
            }
        });



    }
}
