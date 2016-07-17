package com.sample.fragmentexample.bo;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.sample.fragmentexample.adapter.ListPageViewAdapter;


public class Facts {
    private String title;
    private String description;
    private String imageURL;
    private ListPageViewAdapter lispageAdapter;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}
