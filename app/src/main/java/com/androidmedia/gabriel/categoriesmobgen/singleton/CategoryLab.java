package com.androidmedia.gabriel.categoriesmobgen.singleton;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.androidmedia.gabriel.categoriesmobgen.R;
import com.androidmedia.gabriel.categoriesmobgen.database.CategoryBaseHelper;
import com.androidmedia.gabriel.categoriesmobgen.database.CategoryCursorWrapper;
import com.androidmedia.gabriel.categoriesmobgen.database.CategoryDbSchema;
import com.androidmedia.gabriel.categoriesmobgen.models.Category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Gabriel on 5/31/2017.
 */

public class CategoryLab {


    private static CategoryLab sCategoryLab;
    private List<Category> mCategories;

    private Context mContext;
    private SQLiteDatabase mDatabase;


    public static CategoryLab getCategoryLab(Context context) {

        if(sCategoryLab==null){
           sCategoryLab = new CategoryLab(context);
        }

        return sCategoryLab;
    }


    private CategoryLab(Context context) {

        mContext = context.getApplicationContext();
        mDatabase = new CategoryBaseHelper(mContext).getWritableDatabase();

    }


    public List<Category> getCategories() {
        mCategories = new ArrayList<Category>();
        Category category = new Category();

        //Assuming that always is going to be 3 categories. set customize images for each category
        int[] covers = new int[]{
                R.drawable.books1,
                R.drawable.houses3,
                R.drawable.charactes2
        };
        /****/

        CategoryCursorWrapper cursor = queryCategories(null,null);
        try {
            cursor.moveToFirst();
            int i =0;
            while (!cursor.isAfterLast()) {
                category = cursor.getCategory();
                category.setThumbnail(covers[i]);
                mCategories.add(category);
                cursor.moveToNext();
                i++;
            }
        }finally{
            cursor.close();
        }

        Collections.sort(mCategories,category);

        return mCategories;
    }


    public void addCategory(Category category){


        ContentValues values =  getContentValues(category);

        mDatabase.insert(CategoryDbSchema.CategoryTable.NAME, null , values);

    }

    public Category getCategory(String id){
        for (Category category: mCategories) {

            if (category.getId().equals(id)){

                return category;
            }

        }

        return null;

    }



    private static ContentValues getContentValues(Category category){

        ContentValues values  = new ContentValues();

        values.put(CategoryDbSchema.CategoryTable.Cols.ID, category.getId());
        values.put(CategoryDbSchema.CategoryTable.Cols.TITLE, category.getTitle());
        values.put(CategoryDbSchema.CategoryTable.Cols.URL, category.getUrl());

        return values;

    }



    private CategoryCursorWrapper queryCategories(String whereClause, String[] whereArgs) {

        Cursor cursor = mDatabase.query(

                CategoryDbSchema.CategoryTable.NAME,
                null,// Columns - null selextes all columns
                whereClause,
                whereArgs,
                null,//groupBy
                null,//having
                null//orderBy
        );


        return new CategoryCursorWrapper(cursor);

    }



}
