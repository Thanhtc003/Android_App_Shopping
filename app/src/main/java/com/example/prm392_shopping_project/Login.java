package com.example.prm392_shopping_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_shopping_project.database.AccountDB;

public class Login extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    Button loginButton;
    AccountDB accountDB;
    boolean isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ánh xạ view
        usernameEditText = findViewById(R.id.editTextPhone);
        passwordEditText = findViewById(R.id.editTextNumberPassword);
        loginButton = findViewById(R.id.button_login);
        accountDB = new AccountDB(this);

        // Tự động đăng nhập nếu có lưu trước đó
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String savedUsername = prefs.getString("username", null);
        String savedPassword = prefs.getString("password", null);

        if (savedUsername != null && savedPassword != null) {
            autoLogin(savedUsername, savedPassword);
        }

        // Bắt sự kiện đăng nhập
        loginButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ tài khoản và mật khẩu", Toast.LENGTH_SHORT).show();
                return;
            }

            if (accountDB.checkUserNamePassword(username, password)) {
                isAdmin = accountDB.isAdmin(username);

                // Lưu thông tin tài khoản nếu đăng nhập đúng
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("username", username);
                editor.putString("password", password);
                editor.apply();

                navigateToNextScreen(username, isAdmin);
            } else {
                Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();

                // Nếu sai thì xóa thông tin cũ
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.apply();
            }
        });
    }

    private void autoLogin(String username, String password) {
        if (accountDB.checkUserNamePassword(username, password)) {
            isAdmin = accountDB.isAdmin(username);
            navigateToNextScreen(username, isAdmin);
        } else {
            // Nếu thông tin cũ không hợp lệ thì xóa đi
            SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();
        }
    }

    private void navigateToNextScreen(String username, boolean isAdmin) {
        Intent intent;
        if (isAdmin) {
            Toast.makeText(this, "Welcome Admin", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, HomeActivity.class);
            intent.putExtra("isAdmin", true);
        } else {
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, MainActivity.class);
        }
        intent.putExtra("username", username);
        startActivity(intent);
        finish(); // Đóng LoginActivity
    }
}
