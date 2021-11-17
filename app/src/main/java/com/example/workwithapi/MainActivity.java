package com.example.workwithapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView txt;
    Button btn;
    public String futureJSONString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = findViewById(R.id.txtJoke);
        btn = findViewById(R.id.btnClick);
        btn.setOnClickListener(view -> new JokeLoader().execute());

    }

    private class JokeLoader extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String jsonString = getJson("https://api.chucknorris.io/jokes/random");
            try {
                JSONObject object = new JSONObject(jsonString);
                futureJSONString = object.getString("value");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            futureJSONString = "";
            txt.setText("Loading...");
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            if(!futureJSONString.equals("")){
                txt.setText(futureJSONString);
            }
        }

        private String getJson(String link){
            String data = "";
            try{
                URL url = new URL(link);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(urlConnection.getInputStream(),
                                    "utf-8"));
                    data = reader.readLine();
                    urlConnection.disconnect();
                }
            }catch (Exception e){

            }
            return data;
        }
    }
}


