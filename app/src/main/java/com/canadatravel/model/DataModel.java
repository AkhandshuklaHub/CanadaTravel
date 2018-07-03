package com.canadatravel.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataModel {


    @SerializedName("title")
    String title;
    @SerializedName("rows")
    List<Rows> lsrows;

    public List<Rows> getLsrows() {
        return lsrows;
    }

    public void setLsrows(List<Rows> lsrows) {
        this.lsrows = lsrows;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
