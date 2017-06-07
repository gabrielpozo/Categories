package com.androidmedia.gabriel.categoriesmobgen.gson_media_converter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Gabriel on 6/6/2017.
 */

public class HouseJson {



    @SerializedName("title")
    private String title;
    @SerializedName("region")
    private String region;
    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private String id;

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
