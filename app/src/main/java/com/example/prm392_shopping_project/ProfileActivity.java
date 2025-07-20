package com.example.prm392_shopping_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_shopping_project.database.AccountDB;
import com.example.prm392_shopping_project.model.Account;

public class ProfileActivity extends AppCompatActivity {

    private TextView fullNameTextView, joinDateTextView, adminStatusTextView;
    private Button logoutButton;
    private ImageButton backButton;
    private AccountDB accountDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Ánh xạ các view từ activity_profile.xml
        fullNameTextView = findViewById(R.id.fullNameTextView);
        joinDateTextView = findViewById(R.id.joinDateTextView);
        adminStatusTextView = findViewById(R.id.adminStatusTextView);
        logoutButton = findViewById(R.id.logoutButton);
        backButton = findViewById(R.id.backButton);
        accountDB = new AccountDB(this);

        // Lấy username từ Intent hoặc SharedPreferences
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        if (username == null) {
            SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            username = prefs.getString("username", null);
        }

        // Hiển thị thông tin tài khoản
        if (username != null) {
            Account account = accountDB.getByEmail(username);
            if (account != null) {
                fullNameTextView.setText("Email: " + account.getEmail());
                joinDateTextView.setText("Join Date: " + account.getCreatedAt().toString());
                adminStatusTextView.setText("Admin: " + (account.isAdmin() ? "Yes" : "No"));
            } else {
                Toast.makeText(this, "Không tìm thấy thông tin tài khoản", Toast.LENGTH_SHORT).show();
                navigateToLogin();
            }
        } else {
            Toast.makeText(this, "Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
            navigateToLogin();
        }

        // Xử lý nút Back
        backButton.setOnClickListener(v -> finish());

        // Xử lý nút Logout
        logoutButton.setOnClickListener(v -> {
            // Xóa SharedPreferences
            SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();

            // Chuyển hướng về LoginActivity
            Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
            navigateToLogin();
        });
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}