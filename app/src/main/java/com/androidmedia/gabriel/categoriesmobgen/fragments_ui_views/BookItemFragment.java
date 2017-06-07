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
import com.androidmedia.gabriel.categoriesmobgen.databinding.BookItemBinding;
import com.androidmedia.gabriel.categoriesmobgen.databinding.ItemCategoryGalleryBinding;
import com.androidmedia.gabriel.categoriesmobgen.gson_media_converter.BookJson;
import com.androidmedia.gabriel.categoriesmobgen.interface_categories.CallBacksBook;
import com.androidmedia.gabriel.categoriesmobgen.models.Book;

import com.androidmedia.gabriel.categoriesmobgen.models.Category;
import com.androidmedia.gabriel.categoriesmobgen.retrofit_media.ApiClient;
import com.androidmedia.gabriel.categoriesmobgen.retrofit_media.ApiInterface;
import com.androidmedia.gabriel.categoriesmobgen.view_models.BookViewModel;
import com.androidmedia.gabriel.categoriesmobgen.view_models.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Gabriel on 6/2/2017.
 */

public class BookItemFragment extends Fragment implements CallBacksBook {

    private static final String TAG = "splashActivity";
    private BookAdapter mAdapter;
    ItemCategoryGalleryBinding binding;
    private Category mCategory;
    private static final String ARG_CATEGORY_ID="categoryId";
    private static final String items_per_page = "5";
    private LinearLayoutManager mLinearLayoutManager;
    private List<Book> mBooks = new ArrayList<Book>();
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private int currentPage = 1;
    private boolean downloadCompleted = false;
    private RecyclerView mBookRecyclerView;


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
        mBookRecyclerView  = binding.itemRecyclerView;
        mBookRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        //loading = true;
                        Log.i(TAG, "LAST ITEM WOWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW !!!!!!!");
                        //Do pagination , fetch new data
                        currentPage++;
                        updateUI();
                        // new FetchItemsTask().execute(current_page);
                       // currentPage++;
                    }

                }

            }
        });


        setupAdapter();

        return binding.getRoot();
    }





    private void downloadJsonBookList() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


       Log.i(TAG, "Categorias "  + mCategory.getUrl() );
        downloadCompleted = false;
        Call<List<BookJson>> call = apiService.getBooks("list1",String.valueOf(currentPage),items_per_page);
        call.enqueue(new Callback<List<BookJson>>() {
           @Override
           public void onResponse(Call<List<BookJson>> call, Response<List<BookJson>> response) {

              // Log.i(TAG, "Valor de CurernPAGE "  + currentPage);
               List<BookJson> books = response.body();

               if (books.size()>0) {
                   updateListBooks(books);
                   setupAdapter();
                   Log.i(TAG, "updateUI completada y downloadCompletd a true " );
                   downloadCompleted = true;
               }

           }

           @Override
           public void onFailure(Call<List<BookJson>> call, Throwable t) {

               Log.i(TAG, "on Failure " );

           }
       });

    }

    private void updateListBooks(List<BookJson> booksJson){

        for(BookJson bookJson: booksJson){

            Book book = new Book();
            book.setAuthors(bookJson.getAuthors());
            book.setCountry(bookJson.getCountry());
            book.setIsbn(bookJson.getIsbn());
            book.setNumberOfPages(bookJson.getNumberOfPages());
            book.setName(bookJson.getName());
            book.setPublisher(bookJson.getPublisher());
            book.setReleased(bookJson.getReleased());

            mBooks.add(book);

        }

    }

    private void updateUI(){

        if(mBookRecyclerView != null){
            //mHouses.clear();
            mBookRecyclerView.getAdapter().notifyDataSetChanged();

        }

        downloadJsonBookList();

    }

    private void setupAdapter(){


        if (isAdded())
        {
            mBookRecyclerView.setAdapter(new BookAdapter((mBooks)));
            mLinearLayoutManager.scrollToPosition(pastVisiblesItems);

        }
    }

    public static BookItemFragment newInstance(String categoryId){


        Bundle args = new Bundle();

        args.putSerializable(ARG_CATEGORY_ID, categoryId);
        BookItemFragment fragment = new BookItemFragment();
        fragment.setArguments(args);

        return fragment;
    }



    @Override
    public void onRequestNextPage(RecyclerView rV, int rx, int dy) {

        Log.i(TAG, "on RequestNextPage, valor de rx: "+ "y valor de dy: "+ dy );

    }


    private class BookAdapter extends RecyclerView.Adapter<BookHolder>{

        private List<Book> mBooks;



        public BookAdapter(List<Book> books) {
            mBooks = books;
        }


        @Override
        public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            BookItemBinding binding = DataBindingUtil.inflate(layoutInflater,R.layout.book_item,parent,false);

            return new BookHolder(binding);
        }

        @Override
        public void onBindViewHolder(BookHolder holder, int position) {
             Book book =  mBooks.get(position);
            holder.bindBook(book);

        }

        @Override
        public int getItemCount() {
            return mBooks.size();
        }
    }

    private class BookHolder extends RecyclerView.ViewHolder{
        BookItemBinding mBinding;
        private Book mBook;

        public BookHolder(BookItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.setViewModel(new BookViewModel());
        }
        public void bindBook(Book book) {

            mBook = book;
            Log.i(TAG, "on VAuel name: "+ book.getName() );
          // mBinding.setViewModel(new BookViewModel());
            mBinding.getViewModel().setBook(mBook);
          //  mBinding.getViewModel().setCallbacks(null);

            mBinding.executePendingBindings();

        }

    }









}
