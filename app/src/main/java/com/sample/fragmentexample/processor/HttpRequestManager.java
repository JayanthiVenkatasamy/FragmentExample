package com.sample.fragmentexample.processor;

import android.util.Log;

import com.sample.fragmentexample.util.Constants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpRequestManager {

    public static String getResponseFromServer() {
        InputStream inputStream = null;
        String result = "Bad Request";
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(Constants.URL);
            urlConnection = (HttpURLConnection)url.openConnection();
            Log.d("TAG","---Connection OK "+urlConnection.getResponseCode());
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                if (inputStream != null)
                    result = convertInputStreamToString(inputStream);
                else
                    result = "Request Failed";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        StringBuilder result = new StringBuilder();
        try{
            while((line = bufferedReader.readLine()) != null)
                result.append(line);

            inputStream.close();
        }catch(IOException exception){
            exception.printStackTrace();
        }

        return result.toString();
    }


}
