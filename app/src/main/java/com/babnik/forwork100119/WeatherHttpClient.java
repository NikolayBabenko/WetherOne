package com.babnik.forwork100119;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class WeatherHttpClient {

   public static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";


   public static String APPID = "58182118c2bb31464bb8b7f09cf9a68c";

    public String getWetherData(String location){
        HttpURLConnection con = null;
        InputStream is = null;

        try{
            con = (HttpURLConnection)(new URL(BASE_URL + location + "&appid=" + APPID)).openConnection();
            con.setRequestMethod("GET");
            con.getDoInput();
            con.getDoOutput();
            con.connect();

            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line=br.readLine()) !=null){
                buffer.append(line + "\r\n");
            }

            is.close();
            con.disconnect();

            return buffer.toString();



        }  catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                is.close();
            }
            catch (Throwable t){

            }
            try {
                con.disconnect();
            }
            catch (Throwable t){

            }
        }
        return null;
    }

}

