package com.gia.fetchrewardsexcercise;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Collections;
import java.util.Comparator;
import java.util.function.ToIntFunction;

public class ItemsModel {
    private int id;
    private int listId;
    private String name;

    public ItemsModel() {
    }


    public ItemsModel(int id, int listId, String name) {
        this.id = id;
        this.listId = listId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public String toString(){
        return String.format(String.valueOf(id), listId, name);
    }


}

