package com.prashant.apilib.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ranking {

    @SerializedName("ranking")
    @Expose
    private String ranking;
    @SerializedName("products")
    @Expose
    private List<RankingProduct> products = null;

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public List<RankingProduct> getProducts() {
        return products;
    }

    public void setProducts(List<RankingProduct> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Ranking{" +
                "ranking='" + ranking + '\'' +
                ", products=" + products +
                '}';
    }
}
