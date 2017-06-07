package com.androidmedia.gabriel.categoriesmobgen.retrofit_media;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gabriel on 5/19/2017.
 */

public class ApiClient {



    static  Gson gson = new GsonBuilder().setLenient().create();

    public static final String BASE_URL = "http://android-test.mobgen.com";
    private static Retrofit retrofit = null;

    public  static Retrofit getClient(){

        if (retrofit == null){

            retrofit = new Retrofit.Builder()
                                    .baseUrl(BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create(gson))
                                    .build();
        }


        return retrofit;

    }


}
