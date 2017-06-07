package com.androidmedia.gabriel.categoriesmobgen.gson_media_converter;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Gabriel on 6/7/2017.
 */

public class CharacterJson {

    @SerializedName("aliases")
    private List<String> aliases;
    @SerializedName("titles")
    private List<String> titles;
    @SerializedName("playedBy")
    private List<String> playedBy;
    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private String id;
    @SerializedName("gender")
    private String gender;

    public List<String> getAliases() {
        return aliases;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public List<String> getPlayedBy() {
        return playedBy;
    }

    public void setPlayedBy(List<String> playedBy) {
        this.playedBy = playedBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
