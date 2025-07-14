package com.example.prm392_shopping_project.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_shopping_project.R;
import com.example.prm392_shopping_project.model.Product;

import java.util.List;


public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {

    List<Product> list;
    ProductListener productListener;

    public ProductListAdapter(List<Product> list) {
        this.list = list;
    }

    public Product getProductAt(int position) {
        return list.get(position);
    }

    public void setProductListener(ProductListener productListener) {
        this.productListener = productListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recently_viewed_items, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product p = list.get(position);
        holder.name.setText(p.getName());
        holder.price.setText(p.getPrice()+"");
        holder.unit.setText(p.getUnit());
        holder.discount.setText(p.getDiscount()+"");
        byte[] Image = p.getImageUrl();
        Bitmap bitmap = BitmapFactory.decodeByteArray(Image, 0, Image.length);
        holder.productImage.setImageBitmap(bitmap);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView productImage;
        TextView name, price, unit, discount;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.imv_imgRV);
            name  = itemView.findViewById(R.id.tv_nameRV);
            price = itemView.findViewById(R.id.tv_priceRV);
            unit = itemView.findViewById(R.id.tv_unitRV);
            discount = itemView.findViewById(R.id.tv_discountRV);
            itemView.setOnClickListener(this);

//
        }

        @Override
        public void onClick(View view) {
            if (productListener != null) {
                productListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface ProductListener {
        void onItemClick(View view, int position);

    }
}