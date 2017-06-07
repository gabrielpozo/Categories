package com.androidmedia.gabriel.categoriesmobgen.gson_media_converter;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Gabriel on 6/2/2017.
 */

public class BookJson {


    @SerializedName("authors")
    private List<String> authors;
    @SerializedName("isbn")
    private String isbn;
    @SerializedName("country")
    private String country;
    @SerializedName("name")
    private String name;
    @SerializedName("numberOfPages")
    private String numberOfPages;
    @SerializedName("publisher")
    private String publisher;
    @SerializedName("released")
    private String released;


    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumberOfPages(String numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setDate(String released) {
        this.released = released;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getName() {
        return name;
    }

    public String getNumberOfPages() {
        return numberOfPages;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getReleased() {
        return released;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
