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
import com.example.prm392_shopping_project.model.Category;

import java.util.List;

public class CategoryCRUDAdapter extends RecyclerView.Adapter<CategoryCRUDAdapter.CategoryViewHolder> {

    private List<Category> list;
    private CategoryListener categoryListener;

    public CategoryCRUDAdapter(List<Category> list) {
        this.list = list;
    }

    public Category getCategoryAt(int position) {
        return list.get(position);
    }

    public void setCategoryListener(CategoryListener categoryListener) {
        this.categoryListener = categoryListener;
    }

    public void setList(List<Category> list) {
        this.list = list;
        notifyDataSetChanged(); // Refresh the list
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_crud, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = list.get(position);
        byte[] imageBytes = category.getImageUrl();

        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.img.setImageBitmap(bitmap);
        } else {
            // Set a default image if imageBytes is null
            holder.img.setImageResource(R.drawable.b2); // Replace with your default drawable
        }

        holder.name.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name;
        private ImageView img;

        public CategoryViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.tvnameCategory);
            img = view.findViewById(R.id.imgCategory);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (categoryListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    categoryListener.onItemClick(view, position);
                }
            }
        }
    }

    public interface CategoryListener {
        void onItemClick(View view, int position);
    }
}
