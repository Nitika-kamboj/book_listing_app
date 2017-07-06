package com.example.nitikakamboj.booklistingapp;

/**
 * Created by Nitika Kamboj on 04-07-2017.
 */

public class Books {
  private String mTitle;
  private String mAuthor;
  private String mUrl;

  public Books(String title,String author,String url){
  mTitle=title;
  mAuthor=author;
  mUrl=url;
  }
  public String getmTitle()
  {
  return mTitle;
  }
 public String getmAuthor()
 {
  return mAuthor;
 }
 public String getmUrl()
 {
  return mUrl;
 }
}
