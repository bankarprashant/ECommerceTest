package com.prashant.apilib.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RankingFields implements Serializable {

    @SerializedName("view_count")
    @Expose
    private Integer viewCount;
    @SerializedName("order_count")
    @Expose
    private Integer orderCount;
    @SerializedName("shares")
    @Expose
    private Integer shares;

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Integer getShares() {
        return shares;
    }

    public void setShares(Integer shares) {
        this.shares = shares;
    }

    @Override
    public String toString() {
        return "RankingFields{" +
                "viewCount=" + viewCount +
                ", orderCount=" + orderCount +
                ", shares=" + shares +
                '}';
    }
}
