package com.androidmedia.gabriel.categoriesmobgen.fragments_ui_views;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidmedia.gabriel.categoriesmobgen.singleton.CategoryLab;
import com.androidmedia.gabriel.categoriesmobgen.R;
import com.androidmedia.gabriel.categoriesmobgen.databinding.HouseItemBinding;
import com.androidmedia.gabriel.categoriesmobgen.databinding.ItemCategoryGalleryBinding;
import com.androidmedia.gabriel.categoriesmobgen.gson_media_converter.HouseJson;
import com.androidmedia.gabriel.categoriesmobgen.models.Category;
import com.androidmedia.gabriel.categoriesmobgen.models.House;
import com.androidmedia.gabriel.categoriesmobgen.retrofit_media.ApiClient;
import com.androidmedia.gabriel.categoriesmobgen.retrofit_media.ApiInterface;
import com.androidmedia.gabriel.categoriesmobgen.view_models.CategoryViewModel;
import com.androidmedia.gabriel.categoriesmobgen.view_models.HouseViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Gabriel on 6/6/2017.
 */

public class HouseItemFragment extends Fragment {

    private static final String TAG = "splashActivity";
    private HouseAdapter mAdapter;
    ItemCategoryGalleryBinding binding;
    private Category mCategory;
    private static final String ARG_CATEGORY_ID="categoryId";
    private static final String items_per_page = "10";
    private List<House> mHouses = new ArrayList<House>();
    private LinearLayoutManager mLinearLayoutManager;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private int mCurrentPage = 1;
    private boolean downloadCompleted = false;
    private RecyclerView mHouseRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        String categoryId = (String) getArguments().getSerializable(ARG_CATEGORY_ID);
        mCategory = CategoryLab.getCategoryLab(getActivity()).getCategory(categoryId);

        updateUI();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.item_category_gallery,container,false);
        binding.setViewModel(new CategoryViewModel());

        binding.getViewModel().setCategory(mCategory);
        binding.executePendingBindings();
        binding.itemRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mLinearLayoutManager = (LinearLayoutManager) binding.itemRecyclerView.getLayoutManager();
        mHouseRecyclerView = binding.itemRecyclerView;
        mHouseRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0) //check for scroll down
                {
                    Log.i(TAG, "Esta scrolleando!!!!");

                    visibleItemCount = mLinearLayoutManager.getChildCount();
                    totalItemCount = mLinearLayoutManager.getItemCount();
                    pastVisiblesItems = mLinearLayoutManager.findFirstVisibleItemPosition();

                    if ( downloadCompleted && (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                    {
                         mCurrentPage++;
                            updateUI();
                    }

                }

            }
        });

        setupAdapter();

        return binding.getRoot();
    }


    private void downloadJsonHouseList() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        Log.i(TAG, "Categorias "  + mCategory.getUrl() );

        downloadCompleted = false;
        Call<List<HouseJson>> call = apiService.getHouses("list2",String.valueOf(mCurrentPage),items_per_page);
        call.enqueue(new Callback<List<HouseJson>>() {
            @Override
            public void onResponse(Call<List<HouseJson>> call, Response<List<HouseJson>> response) {
                List<HouseJson> houses = response.body();

                if (houses.size()>0) {
                    updateListBooks(houses);
                    setupAdapter();
                    downloadCompleted = true;
                }

            }

            @Override
            public void onFailure(Call<List<HouseJson>> call, Throwable t) {

                Log.i(TAG, "on Failure " );

            }
        });

    }

    private void updateListBooks(List<HouseJson> housesJson){

        for(HouseJson houseJson: housesJson){


            House house = new House();
            house.setTitle(houseJson.getTitle());
            house.setRegion(houseJson.getRegion());
            house.setName(houseJson.getName());

            mHouses.add(house);


        }

    }

    private void updateUI(){

        if(mHouseRecyclerView != null){
            mHouseRecyclerView.getAdapter().notifyDataSetChanged();
        }

        downloadJsonHouseList();

    }

    private void setupAdapter(){

        if (isAdded())
        {
            mHouseRecyclerView.setAdapter(new HouseAdapter((mHouses)));
            mLinearLayoutManager.scrollToPosition(pastVisiblesItems);

        }
    }




    public static HouseItemFragment newInstance(String categoryId){

        Bundle args = new Bundle();
        args.putSerializable(ARG_CATEGORY_ID, categoryId);
        HouseItemFragment fragment = new HouseItemFragment();
        fragment.setArguments(args);

        return fragment;
    }





    private class HouseAdapter extends RecyclerView.Adapter<HouseHolder>{

        private List<House> mHouses;



        public HouseAdapter(List<House> houses) {
            mHouses = houses;
        }


        @Override
        public HouseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            HouseItemBinding binding = DataBindingUtil.inflate(layoutInflater,R.layout.house_item,parent,false);

            return new HouseHolder(binding);
        }

        @Override
        public void onBindViewHolder(HouseHolder holder, int position) {
            House house =  mHouses.get(position);
            holder.bindHouse(house);

        }

        @Override
        public int getItemCount() {
            return mHouses.size();
        }
    }

    private class HouseHolder extends RecyclerView.ViewHolder{
        HouseItemBinding mBinding;
        private House mHouse;

        public HouseHolder(HouseItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.setViewModel(new HouseViewModel());
        }
        public void bindHouse(House house) {
            mHouse = house;
            mBinding.getViewModel().setHouse(mHouse);
            mBinding.getViewModel();
            mBinding.executePendingBindings();

        }

    }



}
