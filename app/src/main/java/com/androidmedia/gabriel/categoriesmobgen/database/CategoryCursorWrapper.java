package com.androidmedia.gabriel.categoriesmobgen.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.androidmedia.gabriel.categoriesmobgen.models.Category;

/**
 * Created by Gabriel on 5/31/2017.
 */

public class CategoryCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CategoryCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Category getCategory(){

        String id = getString(getColumnIndex(CategoryDbSchema.CategoryTable.Cols.ID));
        String title = getString(getColumnIndex(CategoryDbSchema.CategoryTable.Cols.TITLE));
        String url = getString(getColumnIndex(CategoryDbSchema.CategoryTable.Cols.URL));

        Category category = new Category();

        category.setId(id);
        category.setTitle(title);
        category.setUrl(url);


        return category;


    }




}
