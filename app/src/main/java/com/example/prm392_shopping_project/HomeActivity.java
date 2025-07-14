package com.example.prm392_shopping_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView imageView;
    NavigationView navigationView;
    NavController navController;
    TextView tv, tvUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Khởi tạo view
        drawerLayout = findViewById(R.id.drawable);
        imageView = findViewById(R.id.imageMenu);
        navigationView = findViewById(R.id.navigationView);
        tv = findViewById(R.id.tv_Menu);

        // Mở Navigation Drawer khi bấm menu icon
        imageView.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));

        // Hiển thị màu icon gốc
        navigationView.setItemIconTintList(null);

        // Setup Navigation Controller
        navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Lấy username từ Intent
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        // Gán username vào header menu
        View headerView = navigationView.getHeaderView(0);
        tvUsername = headerView.findViewById(R.id.tv_username);
        tvUsername.setText(username);

        // Hiển thị tiêu đề Fragment
        navController.addOnDestinationChangedListener((@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) ->
                tv.setText(destination.getLabel())
        );

        // Bắt sự kiện chọn menu item
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.menuLogOut) {
                // Xử lý Logout
                logout();
                return true;
            }

            // Mặc định điều hướng tới các Fragment
            boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
            if (handled) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            return handled;
        });
    }

    private void logout() {
        // Xoá SharedPreferences (tự động đăng nhập)
        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();

        // Quay về màn Login và xoá stack
        Intent intent = new Intent(HomeActivity.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish(); // Đảm bảo không quay lại màn Home
    }
}
