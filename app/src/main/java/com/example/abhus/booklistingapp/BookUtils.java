package com.example.abhus.booklistingapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.example.abhus.booklistingapp.BookActivity.LOG_TAG;

/**
 * Created by ${AAKASH} on 6/30/2017.
 */

public final class BookUtils {


    public static List<Book>fetchBook(String requestUrl){
        URL url= createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        List<Book> books = fetchFromJson(jsonResponse);
        return books;
    }
    private static URL createUrl(String stringUrl){
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(1000);
            urlConnection.setConnectTimeout(1500);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code:" + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving books" + e);
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    private static List<Book>fetchFromJson(String bookJson){
        if (TextUtils.isEmpty(bookJson)){
            return null;
        }
        List<Book>books= new ArrayList<>();
        try{
            JSONObject obj= new JSONObject(bookJson);
            JSONArray bookArray= obj.getJSONArray("items");
            for(int i=0;i<bookArray.length();i++){
                JSONObject currentObject= bookArray.getJSONObject(i);
                JSONObject features= currentObject.getJSONObject("volumeInfo");
                String title= features.optString("title");
                String publisher= features.optString("publisher");
                String url=features.optString("infoLink");
                Book book= new Book(title,publisher,url);
                books.add(book);
            }
        }
        catch(JSONException e){
            Log.e("BookUtils","Problem passing results", e);
        }
        return books;
    }
}


