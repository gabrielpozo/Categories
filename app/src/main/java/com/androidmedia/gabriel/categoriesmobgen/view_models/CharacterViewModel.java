package com.androidmedia.gabriel.categoriesmobgen.view_models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.androidmedia.gabriel.categoriesmobgen.models.Character;

/**
 * Created by Gabriel on 6/7/2017.
 */

public class CharacterViewModel extends BaseObservable {


    private Character character;

    public void setCharacter(Character character) {
        this.character = character;
        notifyChange();
    }

    @Bindable
    public String getName(){
        return character.getName();
    }
    @Bindable
    public String getGender(){
        return character.getGender();
    }

    @Bindable
    public String getAliases(){
        return character.getAliases().size()>0?character.getAliases().get(0):"Aliase unknown";
    }

    public String getPlayedBy(){
        return character.getPlayedBy().size()>0?character.getPlayedBy().get(0):"unknown";
    }

}
