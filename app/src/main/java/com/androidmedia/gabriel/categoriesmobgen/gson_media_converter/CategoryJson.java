package com.androidmedia.gabriel.categoriesmobgen.gson_media_converter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Gabriel on 5/31/2017.
 */

public class CategoryJson {

    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;

    @SerializedName("href")
    private String href;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String url) {
        this.href = url;
    }

    @Override
    public String toString() {
        return "CategoryJson{" +
                "mId=" + id +
                ", mTitle='" + title + '\'' +
                ", mUrl='" + href + '\'' +
                '}';
    }
}
