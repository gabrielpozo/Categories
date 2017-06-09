package com.androidmedia.gabriel.categoriesmobgen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;

import com.androidmedia.gabriel.categoriesmobgen.fragments_ui_views.CategoryListFragment;
import com.androidmedia.gabriel.categoriesmobgen.interface_categories.CallBacksCategory;
import com.androidmedia.gabriel.categoriesmobgen.models.Category;

/**
 * Created by Gabriel on 5/31/2017.
 */

public class CategoryListActivity extends SingleFragmentActivity implements CallBacksCategory {
    public static final String TRANSITION_PIC = "pic_transaction";
    public static final String TRANSITION_TITLE = "title_transaction";
    public static final String TRANSITION_SCREEN = "fab_transition";

    @Override
    protected Fragment createFragment() {
        return new CategoryListFragment();
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }


    @Override
    public void onCategorySelected(Category category, View v) {

        Pair<View, String> pair = Pair.create(v.findViewById(R.id.linear), TRANSITION_SCREEN);
        Pair<View, String> p1 = Pair.create(v.findViewById(R.id.titleCategory), TRANSITION_TITLE);
        Pair<View, String> p2 = Pair.create(v.findViewById(R.id.initial_thumbnail), TRANSITION_PIC);

        ActivityOptionsCompat options;
        Activity act = this;
        options = ActivityOptionsCompat.makeSceneTransitionAnimation(act,pair,p1,p2);

        Intent transitionIntent = CategoryItemActivity.newIntent(this,category.getId());
        act.startActivity(transitionIntent,options.toBundle());

    }



    public static Intent newIntent(Context packageContext){

        Intent intent = new Intent(packageContext,CategoryListActivity.class);

        return  intent;

    }
}
