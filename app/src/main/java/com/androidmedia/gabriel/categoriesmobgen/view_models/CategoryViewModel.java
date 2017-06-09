package com.androidmedia.gabriel.categoriesmobgen.view_models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.androidmedia.gabriel.categoriesmobgen.interface_categories.CallBacksCategory;
import com.androidmedia.gabriel.categoriesmobgen.models.Category;
import com.bumptech.glide.Glide;

/**
 * Created by Gabriel on 6/1/2017.
 */

public class CategoryViewModel extends BaseObservable {
    private Category mCategory;
    private CallBacksCategory mCallback;
    private View mView;


    public CategoryViewModel(CallBacksCategory callback){
        mCallback = callback;
    }

    public CategoryViewModel(){

    }


    @Bindable
    public String getTitle(){
        return mCategory.getTitle();
    }

    public int getImageUrl() {
        return mCategory.getThumbnail();
    }

    @BindingAdapter({"bind:imageUrl"})
    public static  void loadImage(ImageView view, int thumbnail) {
        Glide.with(view.getContext()).load(thumbnail).into(view);
    }

    public Category getCategory() {
        return mCategory;
    }

    public void setData(Category category, View view) {
        this.mCategory = category;
        this.mView = view ;
        notifyChange();
    }

    public void setCategory(Category category) {
        this.mCategory = category;
        notifyChange();
    }



    public void onCategoryClicked(){
      mCallback.onCategorySelected(mCategory, mView);
    }

}
