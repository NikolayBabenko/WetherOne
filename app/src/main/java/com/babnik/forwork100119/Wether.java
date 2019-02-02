package com.babnik.forwork100119;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;


public class Wether extends AppCompatActivity {

 private   final String TAG = "mlog";
    private TextView cityText;
    private TextView condDescr;
    private TextView temp;
    private TextView press;
    private TextView windSpeed;
    private TextView windDeg;




    private EditText cityName;
    private Button findWeather;

    private TextView hum;
    private ImageView imgView;
    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_wether);
        Log.d(TAG,"Load on Create");

        Log.d(TAG,"String city");

        cityText = (TextView) findViewById(R.id.cityText);
        condDescr = (TextView) findViewById(R.id.condDescr);
        temp = (TextView) findViewById(R.id.temp);
        hum = (TextView) findViewById(R.id.hum);
        press = (TextView) findViewById(R.id.press);
        windSpeed = (TextView) findViewById(R.id.windSpeed);
        windDeg = (TextView) findViewById(R.id.windDeg);
        imgView = (ImageView) findViewById(R.id.condIcon);
        Log.d(TAG,"Find all view");


        cityName = (EditText) findViewById(R.id.cityName);
        findWeather = (Button) findViewById(R.id.btnStart) ;


        city = "Kiev";

        JSONWeatherTask task = new JSONWeatherTask();
        Log.d(TAG,"Create async ta  QAsk");
        Log.d(TAG,cityName.getText().toString());
        task.execute(new String[]{city});






        }

    public void onClick(View view) {
       if(cityName.getText().toString().length() >0 ) {
           city = cityName.getText().toString();
           Log.d(TAG, cityName.getText().toString());
           JSONWeatherTask task = new JSONWeatherTask();
           task.execute(city);
       }
 
       else {
            Toast.makeText(this,"Enter city name",Toast.LENGTH_LONG).show();
        }


        Log.d(TAG,"Pres button find city");
    }

   class JSONWeatherTask extends AsyncTask<String,Void,WetherOne>{

       @Override
       protected WetherOne doInBackground(String... strings) {
           WetherOne wether = new WetherOne();
           String data = new WeatherHttpClient().getWetherData(strings[0]);
           Log.d(TAG,data);

           try{
               wether = JSONWeatherParser.getWether(data);

               Log.d(TAG,wether.toString()+ "Get object weather from JSON parser");

           }

           catch (JSONException e) {
               Log.d(TAG,"Json exeption");
               e.printStackTrace();
           }
           return wether;
       }

       @Override
       protected void onPostExecute(WetherOne wether) {
           super.onPostExecute(wether);

           Log.d(TAG,"On post execute");

           cityText.setText(city);
           temp.setText("Temp" + Math.round((wether.temperature.getTemp() - 273.15)) + " \u2103");
           hum.setText("" + wether.currentCondition.getHumidity() + "%");
           press.setText("" + wether.currentCondition.getPressure() + " hPa");
           windSpeed.setText("" + wether.wind.getSpeed() + " mps");
           windDeg.setText("" + wether.wind.getDeg() + "\u00B0");
       }
   }


}
