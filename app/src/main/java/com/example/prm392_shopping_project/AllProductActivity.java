package com.example.prm392_shopping_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.prm392_shopping_project.adapter.ProductListAdapter;
import com.example.prm392_shopping_project.database.ProductDB;
import com.example.prm392_shopping_project.model.Category;
import com.example.prm392_shopping_project.model.Product;

import java.util.List;

public class AllProductActivity extends AppCompatActivity implements ProductListAdapter.ProductListener {
    ProductDB db;
    RecyclerView recyclerView;
    ProductListAdapter adapter;
    List<Product> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);
        db = new ProductDB(this);
        recyclerView = findViewById(R.id.recyclerProduct);

        Intent i=getIntent();
        int id= i.getIntExtra("id",0);

        if(id!=0) list = db.getByCateId(id);
        else list= db.getAll();

        adapter = new ProductListAdapter(list);
        adapter.setProductListener(this);
//        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onItemClick(View view, int position) {
        Product p = adapter.getProductAt(position);
        Intent i=new Intent(getApplicationContext(), ProductDetails.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("object_product",p);
        i.putExtras(bundle);
        startActivity(i);
    }
}