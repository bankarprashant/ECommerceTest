package com.prashantb.ecommercetest.home.rankings;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prashant.apilib.models.ProductDetails;
import com.prashantb.ecommercetest.R;
import com.prashantb.ecommercetest.common.IOnItemClicked;
import com.prashantb.ecommercetest.common.ProductTypeEnum;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {
    public static final String TAG = "ProductListAdapter";
    private List<ProductDetails> rankingList = new ArrayList<>();
    private IOnItemClicked iOnItemClicked;
    private ProductTypeEnum productTypeEnum;
    private Context context;

    public ProductListAdapter(Context context, IOnItemClicked iOnItemClicked, ProductTypeEnum productTypeEnum) {
        this.iOnItemClicked = iOnItemClicked;
        this.context = context;
        this.productTypeEnum = productTypeEnum;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_common, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductDetails productDetails = rankingList.get(position);
        holder.titleTextView.setText(productDetails.getName());

        if (productTypeEnum.equals(ProductTypeEnum.MOST_ORDERED)) {
            holder.countTextView.setText(context.getString(R.string.ordered_count, productDetails.getOrderCount()));
        } else if (productTypeEnum.equals(ProductTypeEnum.MOST_VIEWED)) {
            holder.countTextView.setText(context.getString(R.string.viewed_count, productDetails.getViewCount()));
        } else if (productTypeEnum.equals(ProductTypeEnum.MOST_SHARED)) {
            holder.countTextView.setText(context.getString(R.string.shared_count, productDetails.getShares()));
        } else {
            holder.countTextView.setVisibility(View.GONE);
        }

        holder.container.setTag(holder);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyViewHolder holder1 = (MyViewHolder) view.getTag();

                if (iOnItemClicked != null) {
                    iOnItemClicked.itemClicked(productTypeEnum, holder1.getAdapterPosition());
                }
            }
        });
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

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.titleTextView)
        TextView titleTextView;
        @BindView(R.id.countTextView)
        TextView countTextView;
        @BindView(R.id.container)
        RelativeLayout container;

        MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
