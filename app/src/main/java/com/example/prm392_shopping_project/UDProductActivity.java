package com.example.prm392_shopping_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prm392_shopping_project.database.ProductDB;
import com.example.prm392_shopping_project.model.Product;

public class UDProductActivity extends AppCompatActivity {
    private TextView tv_name, tv_description, tv_price, tv_unit, tv_quantity;
    private ImageView imgView;
    private ProductDB pdb;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udproduct);

        // Khởi tạo DB
        pdb = new ProductDB(this);

        // Ánh xạ view
        tv_name = findViewById(R.id.tv_nameProduct);
        tv_description = findViewById(R.id.tv_descriptionPro);
        tv_price = findViewById(R.id.tv_pricePro);
        tv_unit = findViewById(R.id.tv_unitPro);
        tv_quantity = findViewById(R.id.tv_quantityPro);
        imgView = findViewById(R.id.img_avatar);

        // Lấy sản phẩm từ Intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            product = (Product) bundle.getSerializable("object_product");
            if (product != null) {
                displayProductDetails(product);
            }
        }
    }

    private void displayProductDetails(Product pro) {
        tv_name.setText(pro.getName());
        tv_description.setText(pro.getDescription());
        tv_price.setText(String.valueOf(pro.getPrice()));
        tv_unit.setText(pro.getUnit());
        tv_quantity.setText(String.valueOf(pro.getQuantity()));

        byte[] img = pro.getImageUrl();
        if (img != null && img.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            imgView.setImageBitmap(bitmap);
        } else {
            imgView.setImageResource(R.drawable.b2); // ảnh mặc định nếu không có ảnh
        }
    }

    public void onUpdateProduct(View view) {
        if (product == null) return;

        Intent intent = new Intent(this, Update_Product.class);
        intent.putExtra("object_product", product);
        startActivity(intent);
    }

    public void onDeleteProduct(View view) {
        if (product == null) return;

        new AlertDialog.Builder(this)
                .setTitle("Notification")
                .setMessage("Are you sure you want to remove this product?")
                .setIcon(R.drawable.ic_delete)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        pdb.delete(product.getId());
                        finish(); // Quay lại màn hình trước
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
