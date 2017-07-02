package com.example.abhus.booklistingapp;

/**
 * Created by ${AAKASH} on 6/30/2017.
 */

public class Book {
    private String mTitle;
    private String mPublisher;
    private String mUrl;

    public Book(String mTitle,String mPublisher,String mUrl){
        this.mTitle=mTitle;
        this.mPublisher=mPublisher;
        this.mUrl=mUrl;
    }

    public String getmTitle(){
        return mTitle;
    }
    public String getmPublisher(){
        return mPublisher;
    }
    public String getmUrl(){
        return mUrl;
    }
}
