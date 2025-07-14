package com.example.prm392_shopping_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_shopping_project.adapter.CategoryAdapter;
import com.example.prm392_shopping_project.adapter.DiscountedProductAdapter;
import com.example.prm392_shopping_project.adapter.RecentlyViewedAdapter;
import com.example.prm392_shopping_project.database.AccountDB;
import com.example.prm392_shopping_project.database.AppDatabaseContext;
import com.example.prm392_shopping_project.database.CategoryDB;
import com.example.prm392_shopping_project.database.ProductDB;
import com.example.prm392_shopping_project.model.Account;
import com.example.prm392_shopping_project.model.Cart;
import com.example.prm392_shopping_project.model.Category;
import com.example.prm392_shopping_project.model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    AppDatabaseContext _context;
    AccountDB accountDB;
    ProductDB productDB;
    CategoryDB categoryDB;

    RecyclerView discountRecyclerView, categoryRecyclerView, recentlyViewedRecycler;
    DiscountedProductAdapter discountedProductAdapter;
    CategoryAdapter categoryAdapter;
    RecentlyViewedAdapter recentlyViewedAdapter;

    List<Product> discountedProductsList = new ArrayList<>();
    List<Category> categoryList = new ArrayList<>();
    List<Product> recentlyViewedList = new ArrayList<>();

    TextView allCategory, tv_product;
    ImageView cart, logoutIcon;
    NotificationBadge bage;

    public static List<Cart> cartList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _context = new AppDatabaseContext(this);

        accountDB = new AccountDB(this);
        productDB = new ProductDB(this);
        categoryDB = new CategoryDB(this);

        // Find views
        discountRecyclerView = findViewById(R.id.discountedRecycler);
        categoryRecyclerView = findViewById(R.id.categoryRecycler);
        allCategory = findViewById(R.id.allCategoryImage);
        tv_product = findViewById(R.id.tv_product);
        recentlyViewedRecycler = findViewById(R.id.recently_item);
        cart = findViewById(R.id.cart_main);
        logoutIcon = findViewById(R.id.logout_main);
        bage = findViewById(R.id.badge_main);

        // Bottom Navigation setup
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.nav_category:
                    startActivity(new Intent(this, AllCategory.class));
                    return true;
                case R.id.nav_cart:
                    startActivity(new Intent(this, CartActivity.class));
                    return true;
                case R.id.nav_logout:
                    showLogoutDialog();
                    return true;
            }
            return false;
        });

        // Badge
        bage.setText(String.valueOf(cartList.size()));

        // Click handlers
        tv_product.setOnClickListener(view -> startActivity(new Intent(this, AllProductActivity.class)));
        allCategory.setOnClickListener(view -> startActivity(new Intent(this, AllCategory.class)));
        cart.setOnClickListener(view -> startActivity(new Intent(this, CartActivity.class)));
        logoutIcon.setOnClickListener(view -> showLogoutDialog());

        // Seeding (nếu cần)
        if (accountDB.getAll().isEmpty()) accountDB.seedingData();

        // Load dữ liệu
        discountedProductsList = productDB.getDiscountProduct();
        if (discountedProductsList == null) discountedProductsList = new ArrayList<>();

        categoryList = categoryDB.getAll();
        if (categoryList == null) categoryList = new ArrayList<>();

        recentlyViewedList = productDB.getAll();
        if (recentlyViewedList == null) recentlyViewedList = new ArrayList<>();

        // Recycler setup
        setDiscountedRecycler(discountedProductsList);
        setCategoryRecycler(categoryList);
        setRecentlyViewedRecycler(recentlyViewedList);
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Do you really want to log out?")
                .setIcon(R.drawable.logout)
                .setPositiveButton("Yes", (dialog, which) -> {
                    getSharedPreferences("MyPrefs", MODE_PRIVATE)
                            .edit().clear().apply();
                    Intent intent = new Intent(this, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void setDiscountedRecycler(List<Product> dataList) {
        discountRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        discountedProductAdapter = new DiscountedProductAdapter(this, dataList);
        discountRecyclerView.setAdapter(discountedProductAdapter);
    }

    private void setCategoryRecycler(List<Category> dataList) {
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoryAdapter = new CategoryAdapter(this, dataList);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void setRecentlyViewedRecycler(List<Product> dataList) {
        recentlyViewedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recentlyViewedAdapter = new RecentlyViewedAdapter(this, dataList);
        recentlyViewedRecycler.setAdapter(recentlyViewedAdapter);
    }
}
