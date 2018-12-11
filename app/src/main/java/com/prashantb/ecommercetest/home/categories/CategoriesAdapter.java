package com.prashantb.ecommercetest.home.categories;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prashant.apilib.models.Category;
import com.prashantb.ecommercetest.R;
import com.prashantb.ecommercetest.common.IOnItemClicked;
import com.prashantb.ecommercetest.common.ProductTypeEnum;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {
    public static final String TAG = "CategoriesAdapter";
    private ArrayList<Category> categoriesList = new ArrayList<>();
    private IOnItemClicked iOnItemClicked;

    public CategoriesAdapter(IOnItemClicked iOnItemClicked) {
        this.iOnItemClicked = iOnItemClicked;
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
        Category category = categoriesList.get(position);

        holder.countTextView.setVisibility(View.GONE);
        holder.titleTextView.setText(category.getName());

        holder.container.setTag(holder);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyViewHolder holder1 = (MyViewHolder) view.getTag();
                if (iOnItemClicked != null) {
                    iOnItemClicked.itemClicked(ProductTypeEnum.CATEGORIES, holder1.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public ArrayList<Category> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(ArrayList<Category> categoriesList) {
        this.categoriesList = categoriesList;
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
