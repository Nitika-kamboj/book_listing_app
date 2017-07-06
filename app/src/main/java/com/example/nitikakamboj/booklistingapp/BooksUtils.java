package com.example.nitikakamboj.booklistingapp;
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

/**
 * Created by Nitika Kamboj on 04-07-2017.
 */

public final class BooksUtils {
 private static String LOG_TAG =BooksUtils.class.getName();

 private BooksUtils(){}

 public static List<Books> fetchData(String requestUrl)
 {
 URL url=createUrl(requestUrl);
 String jsonResponse=null;
  try
  {
  jsonResponse=makeHttpRequest(url);
  }
  catch (IOException e)
  {
      Log.e(LOG_TAG,"Problem making the HTTP request",e);
  }
  List<Books> books=extractBooks(jsonResponse);
  return books;
 }
 private static URL createUrl(String requesturl)
 {
  URL url=null;
  try
  {
   url=new URL(requesturl);
  }
  catch (MalformedURLException e)
  {
  Log.e(LOG_TAG,"Problem building the url",e);
  }
 return url;
 }
 private static String makeHttpRequest(URL url) throws IOException{
 String jsonResponse="";
 if(url==null)
 {
 return jsonResponse;
 }
     HttpURLConnection connection=null;
     InputStream inputstream=null;
  try{
   connection=(HttpURLConnection)url.openConnection();
   connection.setReadTimeout(10000);
   connection.setConnectTimeout(15000);
   connection.setRequestMethod("GET");
   connection.connect();
   if(connection.getResponseCode()==200)
   {
   inputstream=connection.getInputStream();
   jsonResponse=readFromStream(inputstream);
   }
  else
   {
   Log.e(LOG_TAG,"Error Response Code" +connection.getResponseCode());
   }
  }
  catch(IOException e)
  {
  Log.e(LOG_TAG,"Error in request generation",e);
  }
  finally {
  if(connection!=null)
  {
  connection.disconnect();
  }
  if(inputstream!=null)
  {
  inputstream.close();
  }
  }
  return jsonResponse;
 }
 private static String readFromStream(InputStream inputStream)throws IOException
 {
  StringBuilder output=new StringBuilder();
  if(inputStream!=null)
  {
      InputStreamReader inputStreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
      BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
      String line= bufferedReader.readLine();
     while(line!=null)
     {
      output.append(line);
      line=bufferedReader.readLine();
     }
  }
  return output.toString();
 }
 public static List<Books> extractBooks(String json) {
  List<Books> books = new ArrayList<>();
  try {
   JSONObject jsonResponse = new JSONObject(json);
   JSONArray jsonArray = jsonResponse.getJSONArray("items");

   for (int i = 0; i < jsonArray.length(); i++) {
    JSONObject bookObject = jsonArray.getJSONObject(i);
    JSONObject bookInfo = bookObject.getJSONObject("volumeInfo");
    String title = bookInfo.getString("title");
    JSONArray authorsArray = bookInfo.getJSONArray("authors");
    String authors = formatListOfAuthors(authorsArray);
    String url=bookInfo.getString("infoLink");

    Books book = new Books(title,authors,url);
    books.add(book);
   }
  }
   catch(JSONException e)
   {
    Log.e(LOG_TAG,"Error in fetching data",e);
   }
   return books;
  }
 public static String formatListOfAuthors(JSONArray authorsList) throws JSONException {

  String authorsListInString = null;

  if (authorsList.length() == 0) {
   return null;
  }

  for (int i = 0; i < authorsList.length(); i++){
   if (i == 0) {
    authorsListInString = authorsList.getString(0);
   } else {
    authorsListInString += ", " + authorsList.getString(i);
   }
  }

  return authorsListInString;
 }
 }

