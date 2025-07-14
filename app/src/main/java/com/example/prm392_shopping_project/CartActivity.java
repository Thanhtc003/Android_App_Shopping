package com.example.prm392_shopping_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prm392_shopping_project.adapter.CartAdapter;
import com.example.prm392_shopping_project.model.Cart;
import com.example.prm392_shopping_project.model.Event.EventCalculateTotalPrice;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    CartAdapter cartAdapter;
    RecyclerView rcCart;
    TextView total_price;
    Button btnBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        init();

        if (MainActivity.cartList.size() != 0) {
            calTotalPrice();
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            cartAdapter = new CartAdapter(MainActivity.cartList, this);
            rcCart.setLayoutManager(layoutManager);
            rcCart.setAdapter(cartAdapter);
        } else {
            findViewById(R.id.cart_empty).setVisibility(View.VISIBLE);
        }

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.cartList.size() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    builder.setTitle("Thông báo");
                    builder.setMessage("Chưa có sản phẩm, hãy chọn ít nhất một sản phẩm.");
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.show();

                } else {
                    Intent i = new Intent(getApplicationContext(), BuyConfirm.class);
                    i.putExtra("totalPrice",totalPrice()+"");
                    startActivity(i);
                }

            }
        });

    }

    public void init() {
        rcCart = findViewById(R.id.cart_rv);
        total_price = findViewById(R.id.total_price);
        btnBuy = findViewById(R.id.btnmuahang);


    }

    public void calTotalPrice() {
        int total = 0;
        for (int i = 0; i < MainActivity.cartList.size(); i++) {
            total += MainActivity.cartList.get(i).getPrice() * MainActivity.cartList.get(i).getQuantity();
        }
        total_price.setText(String.valueOf(total) + " $");
    }
    public int totalPrice() {
        int total = 0;
        for (int i = 0; i < MainActivity.cartList.size(); i++) {
            total += MainActivity.cartList.get(i).getPrice() * MainActivity.cartList.get(i).getQuantity();
        }
        return total;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventCalTotal(EventCalculateTotalPrice event) {
        if (event != null) {
            calTotalPrice();
        }
    }

    public void onCheckoutClicked(View view) {

        startActivity(new Intent(getApplicationContext(), MainActivity.class));

    }
}