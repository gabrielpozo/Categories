package com.androidmedia.gabriel.categoriesmobgen.view_models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.androidmedia.gabriel.categoriesmobgen.models.Book;

/**
 * Created by Gabriel on 6/3/2017.
 */

public class BookViewModel extends BaseObservable {
    private Book book;

    @Bindable
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
        notifyChange();
    }

    @Bindable
    public String getAuthors(){
        return book.getAuthors().get(0).toString();
    }
    @Bindable
    public String getCountry(){
        return book.getCountry();
    }

    @Bindable
    public String getPublisher(){
        return book.getPublisher();
    }

    @Bindable
    public String getPages(){
        return book.getNumberOfPages();
    }

    @Bindable
    public String getIsbn(){
        return book.getIsbn();
    }
    @Bindable
    public String getName(){
        return book.getName();
    }



}
