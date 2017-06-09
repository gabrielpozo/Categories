package com.androidmedia.gabriel.categoriesmobgen.view_models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.androidmedia.gabriel.categoriesmobgen.models.House;

/**
 * Created by Gabriel on 6/5/2017.
 */

public class HouseViewModel extends BaseObservable{

    private House house;

    @Bindable
    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
        notifyChange();
    }

    @Bindable
    public String getTitle(){
        return house.getTitle().equalsIgnoreCase("")?"Title not available":house.getTitle();
    }
    @Bindable
    public String getName(){
        return house.getName();
    }

    @Bindable
    public String getRegion(){
        return house.getRegion();
    }


}
