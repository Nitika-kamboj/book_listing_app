package com.example.nitikakamboj.booklistingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Nitika Kamboj on 04-07-2017.
 */

public class BooksLoader extends AsyncTaskLoader<List<Books>> {

 public static final String LOG_TAG=BooksLoader.class.getName();
 private String mUrl;

 public BooksLoader(Context context,String url)
 {
 super(context);
 mUrl=url;
 }
 public void onStartLoading()
 {
 forceLoad();
 }
 public List<Books> loadInBackground()
 {
 if(mUrl==null)
 {
  return null;
 }
 List<Books> getbooks=BooksUtils.fetchData(mUrl);
  return getbooks;
 }
}
