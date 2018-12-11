package com.prashant.apilib.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RankingProduct extends RankingFields {

    @SerializedName("id")
    @Expose
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RankingProduct{" +
                "id=" + id +
                '}';
    }
}
