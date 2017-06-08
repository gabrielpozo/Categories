package com.androidmedia.gabriel.categoriesmobgen.view_models;

import android.content.Context;

import android.view.View;

import com.androidmedia.gabriel.categoriesmobgen.CategoryListActivity;
import com.androidmedia.gabriel.categoriesmobgen.interface_categories.CallBacksCategory;
import com.androidmedia.gabriel.categoriesmobgen.models.Category;

import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Gabriel on 6/8/2017.
 */
public class CategoryViewModelTest {
    private CallBacksCategory mCallback;
    private Category mCategory;
    private Context context;
    CategoryViewModel mSubject;
    View view ;

    @Before
    public void setUp() throws Exception {

        mCallback = mock(CategoryListActivity.class);
        context = mock(Context.class);
        view = mock(View.class);
        mCategory = new Category();
        mCategory.setTitle("Book");
        mSubject = new CategoryViewModel(mCallback);
        mSubject.setData(mCategory,view);

    }

    @Test
    public void exposesCategoryNameAsTitle(){

        assertThat(mSubject.getTitle(), is(mCategory.getTitle()));

    }

    @Test
    public void exposesImageView(){

        assertThat(mSubject.getImageUrl(), is(mCategory.getThumbnail()));

    }
    @Test
    public void callsOnButtonClicked(){
        mSubject.onCategoryClicked();
        verify(mCallback).onCategorySelected(mCategory,view);
    }

}