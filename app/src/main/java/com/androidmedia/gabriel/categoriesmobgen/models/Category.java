package com.androidmedia.gabriel.categoriesmobgen.models;

import java.util.Comparator;

/**
 * Created by Gabriel on 5/31/2017.
 */

public class Category implements Comparator<Category> {

    private String mId;
    private String mTitle;
    private String mUrl;
    private int thumbnail;


    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }


    @Override
    public String toString() {
        return "Category{" +
                "mId='" + mId + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mUrl='" + mUrl + '\'' +
                ", thumbnail=" + thumbnail +
                '}';
    }


    @Override
    public int compare(Category cat1, Category cat2) {
        return cat1.getTitle().compareToIgnoreCase(cat2.getTitle());
    }
}
