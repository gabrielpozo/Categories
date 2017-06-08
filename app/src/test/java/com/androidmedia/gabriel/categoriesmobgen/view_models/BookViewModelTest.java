package com.androidmedia.gabriel.categoriesmobgen.view_models;

import com.androidmedia.gabriel.categoriesmobgen.models.Book;
import com.androidmedia.gabriel.categoriesmobgen.models.Category;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Gabriel on 6/8/2017.
 */
public class BookViewModelTest {

    Book mBook;
    BookViewModel mSubject;
    @Before
    public void setUp() throws Exception {
        mBook = new Book();
        mSubject = new BookViewModel();
        List<String> authors = new ArrayList<String>();
        authors.add("Gabriel");
        mBook.setName("Campeon de Europa");
        mBook.setPublisher("BBVA");
        mBook.setIsbn("946-1258974");
        mBook.setAuthors(authors);
        mBook.setNumberOfPages("195");
        mSubject.setBook(mBook);

    }

    @Test
    public void exposesCategoryData(){



        assertThat(mSubject.getName(), is(mBook.getName()));
        assertThat(mSubject.getPublisher(), is(mBook.getPublisher()));
        assertThat(mSubject.getCountry(), is(mBook.getCountry()));
        assertThat(mSubject.getAuthors(), is(mBook.getAuthors().get(0)));
        assertThat(mSubject.getPages(), is(mBook.getNumberOfPages()));



    }





}