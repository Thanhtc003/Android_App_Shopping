package com.example.prm392_shopping_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prm392_shopping_project.model.Cart;
import com.example.prm392_shopping_project.model.Product;
import com.nex3z.notificationbadge.NotificationBadge;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ProductDetails extends AppCompatActivity {

    ImageView imgView, back, btn_cart, cart;
    TextView proName, proPrice, proDesc, proQty, proUnit;

    String name, price, desc, qty, unit;
    int id;
    double price_cart;
    byte[] image;
    NotificationBadge bage;
    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Intent i = getIntent();


        Bundle bundle= getIntent().getExtras();
        if(bundle==null) return;
        Product pro= (Product) bundle.get("object_product");
        init();

        byte[] img = (byte[]) pro.getImageUrl();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        imgView.setImageBitmap(bitmap);

        id=pro.getId();
        name= pro.getName();
        price= pro.getPrice() + " $";
        price_cart=pro.getPrice();
        desc=pro.getDescription();
        qty=pro.getQuantity()+" ";
        unit=pro.getUnit();

        proName.setText(name);
        proPrice.setText(price);
        proDesc.setText(desc);
        proQty.setText(qty);
        proUnit.setText(unit);


        if (MainActivity.cartList.size() > 0) {
            bage.setText(String.valueOf(MainActivity.cartList.size()));
        }

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProductDetails.this, CartActivity.class);
                startActivity(i);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProductDetails.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.cartList.size()> 0) {
                    Cart productAdd = new Cart(id, name, price_cart, unit, 1,img);
                    boolean flag = false;
                    for (int i = 0; i < MainActivity.cartList.size(); i++) {
                        if (MainActivity.cartList.get(i).getName().equals(productAdd.getName())) {
                            MainActivity.cartList.get(i).setQuantity(MainActivity.cartList.get(i).getQuantity() + 1);
                            flag = true;
                        }
                    }
                    if (!flag) {
                        MainActivity.cartList.add(productAdd);
                    }
                } else {
                    MainActivity.cartList.add(new Cart(id, name, price_cart, unit, 1,img));
                }
                bage.setText(String.valueOf(MainActivity.cartList.size()));
                Intent i = new Intent(ProductDetails.this, CartActivity.class);
                startActivity(i);
            }
        });
    }

    public void init() {
        proName = findViewById(R.id.productName);
        proDesc = findViewById(R.id.prodDesc);
        proPrice = findViewById(R.id.prodPrice);
        imgView = findViewById(R.id.big_image);
        back = findViewById(R.id.back2);
        proQty = findViewById(R.id.qty);
        proUnit = findViewById(R.id.unit);
        btn_cart = findViewById(R.id.btn_cart);
        cart = findViewById(R.id.cart);
        bage = findViewById(R.id.badge);
    }
    private byte[] imageViewToByte(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(this, "Please grant this permission!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgView.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
