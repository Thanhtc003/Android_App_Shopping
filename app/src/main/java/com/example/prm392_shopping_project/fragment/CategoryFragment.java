package com.example.prm392_shopping_project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_shopping_project.AddCategory;
import com.example.prm392_shopping_project.R;
import com.example.prm392_shopping_project.UpdateDeleteCategory;
import com.example.prm392_shopping_project.adapter.CategoryCRUDAdapter;
import com.example.prm392_shopping_project.database.CategoryDB;
import com.example.prm392_shopping_project.model.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment implements CategoryCRUDAdapter.CategoryListener{
    RecyclerView recyclerView;
    CategoryCRUDAdapter adapter;
    CategoryDB db;
    List<Category> list;
    FloatingActionButton fab;
    SearchView searchView;

    public CategoryFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new CategoryDB(getContext());
        fab = view.findViewById(R.id.fab);
        recyclerView = view.findViewById(R.id.recycleViewCategory);
        searchView = view.findViewById(R.id.searchView);

        list = new ArrayList<>();
        list = db.getAll();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddCategory.class);
                startActivity(intent);
            }
        });
        adapter = new CategoryCRUDAdapter(list);
        adapter.setCategoryListener(CategoryFragment.this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Category> list = db.getByName(s);
                adapter.setList(list);
                return true;
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Category category = adapter.getCategoryAt(position);
        Intent intent = new Intent(getActivity(), UpdateDeleteCategory.class);
        intent.putExtra("id", category.getId());
        intent.putExtra("name", category.getName());
        intent.putExtra("image", category.getImageUrl());
        startActivity(intent);

    }


}
