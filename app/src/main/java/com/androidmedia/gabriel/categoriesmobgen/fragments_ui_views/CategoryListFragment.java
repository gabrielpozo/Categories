package com.androidmedia.gabriel.categoriesmobgen.fragments_ui_views;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidmedia.gabriel.categoriesmobgen.singleton.CategoryLab;
import com.androidmedia.gabriel.categoriesmobgen.R;
import com.androidmedia.gabriel.categoriesmobgen.databinding.CardViewHolderBinding;
import com.androidmedia.gabriel.categoriesmobgen.databinding.FragmentCategoryListBinding;
import com.androidmedia.gabriel.categoriesmobgen.interface_categories.CallBacksCategory;
import com.androidmedia.gabriel.categoriesmobgen.models.Category;
import com.androidmedia.gabriel.categoriesmobgen.view_models.CategoryViewModel;
import java.util.List;

/**
 * Created by Gabriel on 5/31/2017.
 */
public class CategoryListFragment extends Fragment {

    private CategoryAdapter mAdapter;
    FragmentCategoryListBinding binding;
    private static final String TAG = "splashActivity";
    private CallBacksCategory mCallbacks;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        mCallbacks = (CallBacksCategory) activity;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category_list,container,false);

        binding.categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return binding.getRoot();
    }


    public void updateUI() {

        CategoryLab categoryLab = CategoryLab.getCategoryLab(getActivity());
        List<Category> categories = categoryLab.getCategories();

            mAdapter = new CategoryAdapter(categories);
            binding.categoryRecyclerView.setAdapter(mAdapter);


    }

    @Override
    public void onDetach(){
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onResume(){
        super.onResume();

    }


    private class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {
        private List<Category> mCategories;

        public CategoryAdapter(List<Category> categories) {
            mCategories = categories;
        }

        @Override
        public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            CardViewHolderBinding binding = DataBindingUtil.inflate(layoutInflater,R.layout.card_view_holder,parent,false);
            return new CategoryHolder(binding);
        }

        @Override
        public void onBindViewHolder(CategoryHolder holder, int position) {
            Category category = mCategories.get(position);
            holder.bindCategory(category);
        }
        @Override
        public int getItemCount() {
            return mCategories.size();
        }
    }

    private class CategoryHolder extends RecyclerView.ViewHolder  {
        private CardViewHolderBinding mBinding;
        private Category mCategory;

        public CategoryHolder(CardViewHolderBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.setViewModel(new CategoryViewModel(mCallbacks));
        }

        public void bindCategory(Category category) {

            mCategory = category;
            mBinding.getViewModel().setData(mCategory,mBinding.getRoot());
            mBinding.executePendingBindings();

        }

    }

}
