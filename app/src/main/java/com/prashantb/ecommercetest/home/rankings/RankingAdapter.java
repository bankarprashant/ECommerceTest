package com.prashantb.ecommercetest.home.rankings;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prashant.apilib.models.ProductDetails;
import com.prashantb.ecommercetest.R;
import com.prashantb.ecommercetest.common.IOnItemClicked;
import com.prashantb.ecommercetest.common.RatingEnum;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.MyViewHolder> {
    public static final String TAG = "RankingAdapter";
    private List<ProductDetails> rankingList = new ArrayList<>();
    private IOnItemClicked iOnItemClicked;
    private RatingEnum ratingEnum;
    private Context context;

    public RankingAdapter(Context context, IOnItemClicked iOnItemClicked, RatingEnum ratingEnum) {
        this.iOnItemClicked = iOnItemClicked;
        this.context = context;
        this.ratingEnum = ratingEnum;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ratings, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductDetails productDetails = rankingList.get(position);
        holder.titleTextView.setText(productDetails.getName());
        if (ratingEnum.equals(RatingEnum.MOST_ORDERED)) {
            holder.countTextView.setText(context.getString(R.string.ordered_count, productDetails.getOrderCount()));
        } else if (ratingEnum.equals(RatingEnum.MOST_VIEWED)) {
            holder.countTextView.setText(context.getString(R.string.viewed_count, productDetails.getViewCount()));
        } else if (ratingEnum.equals(RatingEnum.MOST_SHARED)) {
            holder.countTextView.setText(context.getString(R.string.shared_count, productDetails.getShares()));
        }
    }

    @Override
    public int getItemCount() {
        return rankingList.size();
    }

    public List<ProductDetails> getRankingList() {
        return rankingList;
    }

    public void setRankingList(List<ProductDetails> rankingList) {
        this.rankingList = rankingList;
        notifyDataSetChanged();
    }

    public void setiOnItemClicked(IOnItemClicked iOnItemClicked) {
        this.iOnItemClicked = iOnItemClicked;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.titleTextView)
        TextView titleTextView;
        @BindView(R.id.countTextView)
        TextView countTextView;

        MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
