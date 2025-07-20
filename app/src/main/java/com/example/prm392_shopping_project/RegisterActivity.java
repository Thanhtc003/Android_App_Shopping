package com.example.prm392_shopping_project;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast; // Để hiển thị thông báo

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextFullName;
    private EditText editTextPasswordRegister;
    private EditText editTextConfirmPassword;
    private Button buttonRegister;
    private TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Ánh xạ các View từ layout
        editTextFullName = findViewById(R.id.editTextFullName);
        editTextPasswordRegister = findViewById(R.id.editTextPasswordRegister);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonRegister = findViewById(R.id.button_register);
        textViewLogin = findViewById(R.id.textViewLogin);

        // Xử lý sự kiện nhấp chuột cho liên kết "Login"
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển về LoginActivity
                Intent intent = new Intent(RegisterActivity.this, Login.class);
                startActivity(intent);
                finish(); // Đóng RegisterActivity để người dùng không thể quay lại bằng nút back
            }
        });

        // Xử lý sự kiện nhấp chuột cho nút "Register"
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String fullName = editTextFullName.getText().toString().trim();
        String password = editTextPasswordRegister.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        // 1. Xác thực đầu vào
        if (fullName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
            return;
        }



        Toast.makeText(this, "Registration successful! (Demo)", Toast.LENGTH_LONG).show();
        // Sau khi đăng ký thành công, bạn có thể chuyển người dùng đến màn hình đăng nhập hoặc màn hình chính
        Intent intent = new Intent(RegisterActivity.this, Login.class);
        startActivity(intent);
        finish();
    }


}