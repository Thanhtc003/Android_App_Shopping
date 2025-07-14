package com.example.prm392_shopping_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_shopping_project.AllProductActivity;
import com.example.prm392_shopping_project.R;
import com.example.prm392_shopping_project.model.Category;

import java.util.ArrayList;
import java.util.List;

public class AllCategoryAdapter extends RecyclerView.Adapter<AllCategoryAdapter.AllCategoryViewHolder> {

    private Context context;
    private List<Category> categoryList;
    private AllCategoriesListener allCategoriesListener;

    public AllCategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = (categoryList != null) ? categoryList : new ArrayList<>();
    }

    public AllCategoriesListener getAllCategoriesListener() {
        return allCategoriesListener;
    }

    public void setAllCategoriesListener(AllCategoriesListener allCategoriesListener) {
        this.allCategoriesListener = allCategoriesListener;
    }

    public Category getCategoryAt(int position) {
        return categoryList.get(position);
    }

    @NonNull
    @Override
    public AllCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_category_row_items, parent, false);
        return new AllCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        byte[] imageBytes = category.getImageUrl();

        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.categoryImage.setImageBitmap(bitmap);
        } else {
            holder.categoryImage.setImageResource(R.drawable.b2); // ảnh mặc định nếu không có ảnh
        }

        holder.itemView.setOnClickListener(view -> {
            Intent i = new Intent(context, AllProductActivity.class);
            i.putExtra("id", category.getId());
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return (categoryList != null) ? categoryList.size() : 0;
    }

    public class AllCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView categoryImage;

        public AllCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (allCategoriesListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                allCategoriesListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface AllCategoriesListener {
        void onItemClick(View view, int position);
    }
}
