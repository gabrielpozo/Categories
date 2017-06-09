package com.androidmedia.gabriel.categoriesmobgen.retrofit_media;

import com.androidmedia.gabriel.categoriesmobgen.gson_media_converter.BookJson;
import com.androidmedia.gabriel.categoriesmobgen.gson_media_converter.CategoryJson;
import com.androidmedia.gabriel.categoriesmobgen.gson_media_converter.CharacterJson;
import com.androidmedia.gabriel.categoriesmobgen.gson_media_converter.HouseJson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Gabriel on 5/19/2017.
 */

public interface ApiInterface<T> {

    @GET("/api1/index")
    Call<List<CategoryJson>> getCategories();

    @GET("/api1/{list}")
    Call<List<BookJson>> getBooks(@Path("list") String list, @Query("_page") String page, @Query("_limit") String limit );

    @GET("/api1/{list}")
    Call<List<HouseJson>> getHouses(@Path("list") String list, @Query("_page") String page, @Query("_limit") String limit );

    @GET("/api1/{list}")
    Call<List<CharacterJson>> getCharacters(@Path("list") String list, @Query("_page") String page, @Query("_limit") String limit );

}
