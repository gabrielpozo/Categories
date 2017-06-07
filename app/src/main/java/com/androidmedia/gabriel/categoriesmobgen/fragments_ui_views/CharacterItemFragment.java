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

import com.androidmedia.gabriel.categoriesmobgen.singleton.CategoryLab;
import com.androidmedia.gabriel.categoriesmobgen.R;
import com.androidmedia.gabriel.categoriesmobgen.databinding.CharacterItemBinding;
import com.androidmedia.gabriel.categoriesmobgen.databinding.ItemCategoryGalleryBinding;
import com.androidmedia.gabriel.categoriesmobgen.gson_media_converter.CharacterJson;
import com.androidmedia.gabriel.categoriesmobgen.models.Category;
import com.androidmedia.gabriel.categoriesmobgen.models.Character;
import com.androidmedia.gabriel.categoriesmobgen.retrofit_media.ApiClient;
import com.androidmedia.gabriel.categoriesmobgen.retrofit_media.ApiInterface;
import com.androidmedia.gabriel.categoriesmobgen.view_models.CategoryViewModel;
import com.androidmedia.gabriel.categoriesmobgen.view_models.CharacterViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Gabriel on 6/7/2017.
 */

public class CharacterItemFragment extends Fragment {

    private static final String TAG = "splashActivity";
    ItemCategoryGalleryBinding binding;
    private Category mCategory;
    private static final String ARG_CATEGORY_ID="categoryId";
    private static final String items_per_page = "5";
    private List<Character> mCharacters = new ArrayList<Character>();
    private LinearLayoutManager mLinearLayoutManager;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private int mCurrentPage = 1;
    private RecyclerView mCharacterRecyclerView;
    private boolean downloadCompleted = false;

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

        binding.getViewModel().setData(mCategory);
        binding.executePendingBindings();
        binding.itemRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mLinearLayoutManager = (LinearLayoutManager) binding.itemRecyclerView.getLayoutManager();
        mCharacterRecyclerView = binding.itemRecyclerView;

        mCharacterRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0) //check for scroll down
                {

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





    private void downloadJsonCharacterList() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<CharacterJson>> call = apiService.getCharacters("list3",String.valueOf(mCurrentPage),items_per_page);
        call.enqueue(new Callback<List<CharacterJson>>() {
            @Override
            public void onResponse(Call<List<CharacterJson>> call, Response<List<CharacterJson>> response) {

                //Log.i(TAG, "A ver "  + response.body().get(0).getCountry());
                List<CharacterJson> characters = response.body();

                if (characters.size()>0) {
                    updateListCharacters(characters);
                    setupAdapter();
                    downloadCompleted = true;
                }

            }

            @Override
            public void onFailure(Call<List<CharacterJson>> call, Throwable t) {

                Log.i(TAG, "on Failure " );

            }
        });

    }

    private void updateListCharacters(List<CharacterJson> charactersJson){

        for(CharacterJson characterJson: charactersJson){

            Character character = new Character();
            character.setAliases(characterJson.getAliases());
            character.setPlayedBy(characterJson.getPlayedBy());
            character.setName(characterJson.getName());
            character.setGender(characterJson.getGender());
            character.setId(characterJson.getId());

            mCharacters.add(character);

        }

    }

    private void updateUI(){

        if(mCharacterRecyclerView != null){
            //mHouses.clear();
            mCharacterRecyclerView.getAdapter().notifyDataSetChanged();

        }

        downloadJsonCharacterList();

    }

    private void setupAdapter(){


        if (isAdded())
        {
            mCharacterRecyclerView.setAdapter(new CharacterAdapter(mCharacters));
            mLinearLayoutManager.scrollToPosition(pastVisiblesItems);

        }
    }

    public static CharacterItemFragment newInstance(String categoryId){


        Bundle args = new Bundle();

        args.putSerializable(ARG_CATEGORY_ID, categoryId);
        CharacterItemFragment fragment = new CharacterItemFragment();
        fragment.setArguments(args);

        return fragment;
    }




    private class CharacterAdapter extends RecyclerView.Adapter<CharacterHolder>{

        private List<Character> mCharacters;



        public CharacterAdapter(List<Character> characters) {
            mCharacters = characters;
        }


        @Override
        public CharacterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            CharacterItemBinding binding = DataBindingUtil.inflate(layoutInflater,R.layout.character_item,parent,false);

            return new CharacterHolder(binding);
        }

        @Override
        public void onBindViewHolder(CharacterHolder holder, int position) {
            Character character =  mCharacters.get(position);
            holder.bindHouse(character);

        }

        @Override
        public int getItemCount() {
            return mCharacters.size();
        }
    }

    private class CharacterHolder extends RecyclerView.ViewHolder{
        CharacterItemBinding mBinding;
        private Character mCharacter;

        public CharacterHolder(CharacterItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.setViewModel(new CharacterViewModel());
        }
        public void bindHouse(Character character) {
            mCharacter = character;
            mBinding.getViewModel().setCharacter(mCharacter);
            mBinding.getViewModel();
            mBinding.executePendingBindings();

        }

    }


}
