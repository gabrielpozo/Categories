package com.androidmedia.gabriel.categoriesmobgen;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.androidmedia.gabriel.categoriesmobgen.databinding.ActivityCategoryPagerBinding;
import com.androidmedia.gabriel.categoriesmobgen.fragments_ui_views.BookItemFragment;
import com.androidmedia.gabriel.categoriesmobgen.fragments_ui_views.CharacterItemFragment;
import com.androidmedia.gabriel.categoriesmobgen.fragments_ui_views.HouseItemFragment;
import com.androidmedia.gabriel.categoriesmobgen.models.Category;
import com.androidmedia.gabriel.categoriesmobgen.singleton.CategoryLab;

import java.util.List;



/**
 * Created by Gabriel on 6/2/2017.
 */

public class CategoryItemActivity extends AppCompatActivity {

    private static final String EXTRA_CATEGORY_ID = "gabriel.categories.mobgen.category_id";
    ActivityCategoryPagerBinding mBinding;
    private List<Category> mCategories;

    public static Intent newIntent(Context packageContext, String categoryID){

        Intent intent = new Intent(packageContext,CategoryItemActivity.class);
        intent.putExtra(EXTRA_CATEGORY_ID,categoryID);

        return  intent;

    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_category_pager);
        String categoryId = (String) getIntent().getSerializableExtra(EXTRA_CATEGORY_ID);

        PagerAdapter pagerAdapter = new FixedTabsPagerAdapter(getSupportFragmentManager());

        mCategories = CategoryLab.getCategoryLab(this).getCategories();
        ViewPager viewPager = mBinding.activityCategoryPagerViewPager;
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = mBinding.tabLayout;
        tabLayout.setupWithViewPager(viewPager);
        for(int i = 0; i < mCategories.size();i++){

            if(mCategories.get(i).getId().equals(categoryId)){

                viewPager.setCurrentItem(i);
                break;
            }

        }

    }


    private class FixedTabsPagerAdapter extends FragmentPagerAdapter{

        public FixedTabsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //Notice tha we are assuming tha always is going to be these 3 categories
        @Override
        public Fragment getItem(int position) {
            switch (position) {

                case 0:
                    return BookItemFragment.newInstance(mCategories.get(position).getId());
                case 1:
                    return CharacterItemFragment.newInstance(mCategories.get(position).getId());
                case 2:
                    return HouseItemFragment.newInstance(mCategories.get(position).getId());
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return mCategories.size();
        }

        @Override
        public CharSequence getPageTitle(int position){

            switch (position){

                case 0:
                    return mCategories.get(0).getTitle();
                case 1:
                    return mCategories.get(1).getTitle();
                case 2:
                    return mCategories.get(2).getTitle();
                default:
                    return  null;

            }

        }


    }

}
