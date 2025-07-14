package com.example.prm392_shopping_project.adapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_shopping_project.ProductDetails;
import com.example.prm392_shopping_project.R;
import com.example.prm392_shopping_project.model.Product;

import java.util.List;

public class RecentlyViewedAdapter extends RecyclerView.Adapter<RecentlyViewedAdapter.RecentlyViewedViewHolder>{
    Context context;
    List<Product> recentlyViewedList;
    private RecentlyViewedListener RecentlyViewedListener;

    public RecentlyViewedAdapter(Context context, List<Product> recentlyViewedList) {
        this.context = context;
        this.recentlyViewedList = recentlyViewedList;
    }
    public void setList(List<Product> list) {
        this.recentlyViewedList= list;
        notifyDataSetChanged(); // refesh
    }

    public RecentlyViewedAdapter(RecentlyViewedAdapter.RecentlyViewedListener recentlyViewedListener) {
        RecentlyViewedListener = recentlyViewedListener;
    }

    public RecentlyViewedAdapter.RecentlyViewedListener getRecentlyViewedListener() {
        return RecentlyViewedListener;
    }

    public void setRecentlyViewedListener(RecentlyViewedAdapter.RecentlyViewedListener recentlyViewedListener) {
        RecentlyViewedListener = recentlyViewedListener;
    }

    public Product getrecentlyViewedAt (int position) {
        return recentlyViewedList.get(position);
    }


    @NonNull
    @Override
    public RecentlyViewedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recently_viewed_items, parent, false);

        return new RecentlyViewedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentlyViewedViewHolder holder, int position) {

        Product product = recentlyViewedList.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(product.getPrice() + "$/");
        holder.unit.setText(product.getUnit());
        holder.discount.setText(String.valueOf(product.getDiscount()));
        byte[] Image = product.getImageUrl();
        Bitmap bitmap = BitmapFactory.decodeByteArray(Image, 0, Image.length);
        holder.img.setImageBitmap(bitmap);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(context, ProductDetails.class);
                Product product = recentlyViewedList.get(position);
                Bundle bundle=new Bundle();
                bundle.putSerializable("object_product",product);
                i.putExtras(bundle);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recentlyViewedList.size();
    }

    public class RecentlyViewedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name, price, discount, unit;
        ImageView img;
        CardView layoutItemRV;

        public RecentlyViewedViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_nameRV);
            unit = itemView.findViewById(R.id.tv_unitRV);
            price = itemView.findViewById(R.id.tv_priceRV);
            discount=itemView.findViewById(R.id.tv_discountRV);
            img = itemView.findViewById(R.id.imv_imgRV);
            layoutItemRV=itemView.findViewById(R.id.layout_itemRV);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (RecentlyViewedListener != null) {
                RecentlyViewedListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
    public interface RecentlyViewedListener {
        void onItemClick(View view, int position);

    }
}
