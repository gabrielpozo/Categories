package com.androidmedia.gabriel.categoriesmobgen;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.androidmedia.gabriel.categoriesmobgen.gson_media_converter.CategoryJson;
import com.androidmedia.gabriel.categoriesmobgen.models.Category;
import com.androidmedia.gabriel.categoriesmobgen.retrofit_media.ApiClient;
import com.androidmedia.gabriel.categoriesmobgen.retrofit_media.ApiInterface;
import com.androidmedia.gabriel.categoriesmobgen.singleton.CategoryLab;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Gabriel on 5/31/2017.
 */

public class SplashActivity extends AppCompatActivity{
    private static final String TAG = "splashActivity";
    private static final String PREF_IS_FIRST_RUN = "isFirstRun";
    private boolean firstRun=false;
    /** Called when the activity is first created. */
    Thread splashTread;
    private boolean downloadCompleted = true;


    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        firstRun = isFirstRun();
        if (firstRun) {
            Log.i(TAG, "La primera vez que se ejecuta: " );
            loadCategories();
        }else{
            startAnimations();
        }

    }


    private  boolean isFirstRun(){

        return !PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean(PREF_IS_FIRST_RUN,false);

    }

    private void setFirstRunValue(){

        PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .edit()
                .putBoolean(PREF_IS_FIRST_RUN,true)
                .apply();
    }

    private void loadCategories() {

        downloadCompleted = false;
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<CategoryJson>> call = apiService.getCategories();
        call.enqueue(new Callback<List<CategoryJson>>() {
            @Override
            public void onResponse(Call<List<CategoryJson>> call, Response<List<CategoryJson>> response) {

                List<CategoryJson> categories = response.body();
                updateDatabase(categories);
                setFirstRunValue();
                startAnimations();

            }

            @Override
            public void onFailure(Call<List<CategoryJson>> call, Throwable t) {

            }
        });

    }


    private void updateDatabase(List<CategoryJson> categories){

        for(CategoryJson categoryJson: categories){

            Category category = new Category();

            category.setId(String.valueOf(categoryJson.getId()));
            category.setTitle(categoryJson.getTitle());
            category.setUrl(categoryJson.getHref());

            CategoryLab.getCategoryLab(getApplicationContext()).addCategory(category);

        }
    }

    public void launchActity(){

        Intent intent = CategoryListActivity.newIntent(this);
        startActivity(intent);
        SplashActivity.this.finish();
    }


    private void startAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(anim);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 2500 ) {
                        sleep(100);
                        waited += 100;
                    }
                    //Log.i(TAG, "Ahora lanza la activity, ya que no es la primera vez qu se ejecuta la actividad "  );
                    launchActity();



                } catch (InterruptedException e) {
                    // do nothing
                }

            }
        };
        splashTread.start();

    }

}
