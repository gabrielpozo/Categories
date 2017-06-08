package com.androidmedia.gabriel.categoriesmobgen.view_models;

import com.androidmedia.gabriel.categoriesmobgen.models.Book;
import com.androidmedia.gabriel.categoriesmobgen.models.House;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Gabriel on 6/8/2017.
 */
public class HouseViewModelTest {
    House mHouse;
    HouseViewModel mSubject;

    @Before
    public void setUp() throws Exception {

        mHouse = new House();
        mSubject = new HouseViewModel();
        mHouse.setName("Casa de Elron");
        mHouse.setTitle("Javastraat");
        mHouse.setRegion("Amsterdam");

        mSubject.setHouse(mHouse);

    }


    @Test
    public void exposesCategoryData(){


        assertThat(mSubject.getName(), is(mHouse.getName()));
        assertThat(mSubject.getTitle(), is(mHouse.getTitle()));
        assertThat(mSubject.getRegion(), is(mHouse.getRegion()));




    }


}