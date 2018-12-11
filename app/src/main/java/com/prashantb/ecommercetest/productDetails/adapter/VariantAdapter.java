package com.prashantb.ecommercetest.productDetails.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prashant.apilib.models.Variant;
import com.prashantb.ecommercetest.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VariantAdapter extends RecyclerView.Adapter<VariantAdapter.MyViewHolder> {
    private static final String TAG = "VariantAdapter";
    private List<Variant> variantsList;
    private Context context;

    public VariantAdapter(Context context, List<Variant> variantsList) {
        this.variantsList = variantsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_variant, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Variant variant = variantsList.get(position);

        holder.productIdTextView.setText(context.getString(R.string.product_id, variant.getId()));
        holder.priceTextView.setText(context.getString(R.string.price, variant.getPrice()));

        if (variant.getSize() != null) {
            holder.sizeTextView.setText(context.getString(R.string.size, String.valueOf(variant.getSize())));
        } else {
            holder.sizeTextView.setText(context.getString(R.string.size, context.getString(R.string.na)));
        }
        
        holder.colorTextView.setText(context.getString(R.string.color, variant.getColor()));
    }

    @Override
    public int getItemCount() {
        return variantsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.productIdTextView)
        TextView productIdTextView;
        @BindView(R.id.sizeTextView)
        TextView sizeTextView;
        @BindView(R.id.priceTextView)
        TextView priceTextView;
        @BindView(R.id.colorTextView)
        TextView colorTextView;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
