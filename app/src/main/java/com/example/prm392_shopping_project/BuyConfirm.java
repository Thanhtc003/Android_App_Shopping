package com.example.prm392_shopping_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prm392_shopping_project.database.CustomerDB;
import com.example.prm392_shopping_project.database.OrderDB;
import com.example.prm392_shopping_project.database.OrderDetailDB;
import com.example.prm392_shopping_project.model.Customer;
import com.example.prm392_shopping_project.model.Order;
import com.example.prm392_shopping_project.model.OrderDetail;

import java.sql.Date;

public class BuyConfirm extends AppCompatActivity {

    EditText ed_name, ed_phone, ed_email, ed_address;
    TextView confirm;
    CustomerDB customerDB;
    OrderDB orderDB;
    OrderDetailDB orderDetailDB;
    String totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_confirm);
        Intent i = getIntent();

        totalPrice = i.getStringExtra("totalPrice");
        init();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ed_name.getText().toString();
                String phone = ed_phone.getText().toString();
                String email = ed_email.getText().toString();
                String address = ed_address.getText().toString();
                if (name.equals("") || phone.equals("") || email.equals("") || address.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    builder.setTitle("Thông báo");
                    builder.setMessage("Xin vui lòng nhập đầy đủ thông tin.");
                    builder.show();

                } else {
                    long millis = System.currentTimeMillis();
                    Date date = new Date(millis);
                    Customer cus = new Customer(name, email, address, phone);
                    long a = customerDB.insert(cus);
                    int cusId = customerDB.getMaxId();
                    long saveOrder = orderDB.insert(new Order(cusId, date, Double.parseDouble(totalPrice)));
                    int orderId = orderDB.getMaxId();
                    for (int j = 0; j < MainActivity.cartList.size(); j++) {
                        orderDetailDB.insert(new OrderDetail(orderId, MainActivity.cartList.get(j).getId(), MainActivity.cartList.get(j).getPrice(), MainActivity.cartList.get(j).getQuantity()));
                    }
                    Toast.makeText(getApplicationContext(),"Đặt đơn hàng thành công",Toast.LENGTH_SHORT).show();
                    MainActivity.cartList.clear();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }


            }
        });
    }

    public void init() {
        ed_name = findViewById(R.id.ed_ten);
        ed_phone = findViewById(R.id.ed_sodienthoai);
        ed_email = findViewById(R.id.ed_email);
        ed_address = findViewById(R.id.ed_diachi);
        confirm = findViewById(R.id.txt_dathang);
        customerDB = new CustomerDB(this);
        orderDB = new OrderDB(this);
        orderDetailDB = new OrderDetailDB(this);
    }
}