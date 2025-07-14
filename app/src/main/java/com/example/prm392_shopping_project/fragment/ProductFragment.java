package com.example.prm392_shopping_project.fragment;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.prm392_shopping_project.Add_Product;
import com.example.prm392_shopping_project.R;
import com.example.prm392_shopping_project.UDProductActivity;
import com.example.prm392_shopping_project.UpdateDeleteCategory;
import com.example.prm392_shopping_project.adapter.CategoryCRUDAdapter;
import com.example.prm392_shopping_project.adapter.ProductAdapter;
import com.example.prm392_shopping_project.database.CategoryDB;
import com.example.prm392_shopping_project.database.ProductDB;
import com.example.prm392_shopping_project.model.Category;
import com.example.prm392_shopping_project.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends Fragment implements ProductAdapter.ProductListener{
    RecyclerView recyclerView;
    ProductAdapter adapter;
    ProductDB db;
    List<Product> list;
    FloatingActionButton fab;
    SearchView searchView;
    public ProductFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db= new ProductDB((getContext()));
        fab = view.findViewById(R.id.fab_Product);
        recyclerView = view.findViewById(R.id.recycleViewProduct);
        searchView = view.findViewById(R.id.sv_product);

        list = new ArrayList<>();
        list = db.getAll();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Add_Product.class);
                startActivity(intent);
            }
        });
        adapter = new ProductAdapter(list);
        adapter.setProductListener(ProductFragment.this);
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
                List<Product> list = db.getByName(s);
                adapter.setList(list);
                return true;
            }
        });
    }
    public void onItemClick(View view, int position) {
        Product product = adapter.getProductAt(position);
        Intent intent= new Intent(getContext(), UDProductActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("object_product",product);
        intent.putExtras(bundle);
        getContext().startActivity(intent);

    }
}
