package com.example.prm392_shopping_project.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_shopping_project.R;
import com.example.prm392_shopping_project.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private List<Category> categoryList;
    private CategoriesListener categoriesListener;

    // Constructor
    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = (categoryList != null) ? categoryList : new ArrayList<>();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_row_items, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);

        byte[] imageBytes = category.getImageUrl();
        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.categoryImage.setImageBitmap(bitmap);
        } else {
            // Trường hợp không có ảnh thì dùng ảnh mặc định
            holder.categoryImage.setImageResource(R.drawable.b2); // bạn cần có ảnh này trong res/drawable
        }
    }

    @Override
    public int getItemCount() {
        return (categoryList != null) ? categoryList.size() : 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView categoryImage;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (categoriesListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                categoriesListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    // Public setter cho listener (nếu bạn dùng từ Activity hoặc Fragment)
    public void setCategoriesListener(CategoriesListener listener) {
        this.categoriesListener = listener;
    }

    // Đổi access modifier để có thể dùng bên ngoài
    public interface CategoriesListener {
        void onItemClick(View view, int position);
    }
}
