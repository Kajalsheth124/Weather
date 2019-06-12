package com.example.weather;

import android.app.ProgressDialog;
import android.icu.text.SelectFormat;
import android.icu.text.TimeZoneFormat;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeFormatException;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.*;

public class MainActivity extends AppCompatActivity {
    String TAG = "Main Activity";
    Date d = new Date();
    Spinner spin;
    String url = "http://api.openweathermap.org/data/2.5/weather?q=Pune&APPID=ea574594b9d36ab688642d5fbeab847e";
    String[] city = {"Pune", "Mumbai", "Indore", "Ahemdabad", "Delhi","Vadadora",
    "Nandurbar","Nashik","Lonavala","Solapur"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Weather");
        spin = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,city);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String City=city[position];

                url = "http://api.openweathermap.org/data/2.5/weather?q="+ City +"&APPID=ea574594b9d36ab688642d5fbeab847e";


                new YourAsyncTask(MainActivity.this).execute("Felix IT");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private class YourAsyncTask extends AsyncTask<String, Void, String> {
        private ProgressDialog dialog;

        public YourAsyncTask(MainActivity activity) {
            dialog = new ProgressDialog(activity);
        }


        protected String doInBackground(String... args) {


            String jsonStr = "";
            try {
                // Making a request to url and getting response

                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(url));
                HttpResponse response = client.execute(request);
                jsonStr = EntityUtils.toString(response.getEntity());

            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }


//            String str = args[0];
//            // do background work here
//            try {
//                Thread.sleep(4000);
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }

            return jsonStr;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                Weather weather = new Weather();
                JSONObject mainObj = new JSONObject(s);

                JSONObject jsonObject = mainObj.getJSONObject("main");
                weather.setTemp(jsonObject.getDouble("temp"));
                weather.setPressure(jsonObject.getDouble("pressure"));
                weather.setHumidity(jsonObject.getDouble("humidity"));
                weather.setTemp_max(jsonObject.getDouble("temp_max"));
                weather.setTemp_min(jsonObject.getDouble("temp_min"));

                JSONObject mainObject1 = mainObj.getJSONObject("wind");
                weather.setSpeed(mainObject1.getDouble("speed"));


                JSONObject mainObject2 = mainObj.getJSONObject("sys");
                weather.setSunrise(mainObject2.getDouble("sunrise"));
                weather.setSunset(mainObject2.getDouble("sunset"));

                TextView textViewDate = (TextView) findViewById(R.id.day);
                TextView textViewTime = (TextView) findViewById(R.id.time);
                TextView textViewTemp = (TextView) findViewById(R.id.temp);
                TextView textViewPressure = (TextView) findViewById(R.id.Pressure);
                TextView textViewHumidity = (TextView) findViewById(R.id.Humidity);
                TextView textViewTemp_max = (TextView) findViewById(R.id.Maxtemp);
                TextView textViewTemp_min = (TextView) findViewById(R.id.Mintemp);
                TextView textViewSpeed = (TextView) findViewById(R.id.Wind);
                TextView textViewSunrise = (TextView) findViewById(R.id.Sunrise);
                TextView textViewSunset = (TextView) findViewById(R.id.Sunset);

                String currentDateString = DateFormat.getDateInstance().format(new Date());
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMM,yyyy" + "       EEEE");
                String currentTimeString = sdf.format(d);
                String dayOfTheWeek = sdf1.format(d);
                currentDateString = sdf1.format(d);
                textViewDate.setText(currentDateString);
                textViewDate.setText(dayOfTheWeek);
                textViewTime.setText(currentTimeString);


                textViewTemp.setText(String.valueOf(jsonObject.getDouble("temp") - 273.15 + " \u2103"));
                textViewPressure.setText(String.valueOf(jsonObject.getDouble("pressure")));
                textViewHumidity.setText(String.valueOf(jsonObject.getDouble("humidity")));
                textViewTemp_max.setText(String.valueOf(jsonObject.getDouble("temp_max") - 273.15 + " \u2103"));
                textViewTemp_min.setText(String.valueOf(jsonObject.getDouble("temp_min") - 273.15 + " \u2103"));
                textViewSpeed.setText(String.valueOf(mainObject1.getDouble("speed")));
                textViewSunrise.setText(String.valueOf(mainObject2.getDouble("sunrise")));
                textViewSunset.setText(String.valueOf(mainObject2.getDouble("sunset")));

                //Toast.makeText(MainActivity.this, name+" "+number, Toast.LENGTH_SHORT).show();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
