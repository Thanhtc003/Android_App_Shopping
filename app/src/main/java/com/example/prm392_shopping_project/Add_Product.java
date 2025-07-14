package com.example.prm392_shopping_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.prm392_shopping_project.database.CategoryDB;
import com.example.prm392_shopping_project.database.ProductDB;
import com.example.prm392_shopping_project.fragment.CategoryFragment;
import com.example.prm392_shopping_project.fragment.ProductFragment;
import com.example.prm392_shopping_project.model.Category;
import com.example.prm392_shopping_project.model.Product;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Add_Product extends AppCompatActivity {
    ProductDB productDB;
    CategoryDB categoryDB;
    EditText edt_name,edt_description,edt_price,edt_unit,edt_quantity,edt_discount;
    ImageView img;
    Spinner spinner_category;
    int id_cate;
    Button btn_upload, btn_cancel,btn_add;
    final int REQUEST_CODE_GALLERY = 999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        productDB = new ProductDB(this);
        edt_name=findViewById(R.id.edt_addname);
        edt_description=findViewById(R.id.edt_adddescription);
        edt_price=findViewById(R.id.edt_addprice);
        edt_unit=findViewById(R.id.edt_addunit);
        edt_quantity=findViewById(R.id.edt_addquantity);
        edt_discount=findViewById(R.id.edt_adddiscount);
        spinner_category=findViewById(R.id.spinner_category);
        img=findViewById(R.id.imv_addImg);
        btn_upload = findViewById(R.id.btn_addImg);
        btn_cancel = findViewById(R.id.btn_cancelAddProduct);
        btn_add= findViewById(R.id.btn_addProduct);
//
        categoryDB = new CategoryDB(this);
        List<String> listNameCategory= categoryDB.getNameCategory();
        ArrayAdapter arrayAdapter= new ArrayAdapter(this, android.R.layout.simple_spinner_item,listNameCategory);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_category.setAdapter(arrayAdapter);
        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
             id_cate= categoryDB.getIdbyName(listNameCategory.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(Add_Product.this,"You need select Category",Toast.LENGTH_LONG).show();
            }
        } );
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(Add_Product.this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Add_Product.this, ProductFragment.class);
//                startActivity(intent);
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String name = edt_name.getText().toString();
                    String description=edt_description.getText().toString();
                    Double price =Double.valueOf(edt_price.getText().toString()) ;
                    int quantity = Integer.parseInt(edt_quantity.getText().toString());
                    String unit = edt_unit.getText().toString();
                    int discount = Integer.parseInt(edt_discount.getText().toString());
                    byte[] imgUrl= imageViewToByte(img);
                    byte[] bigImgUrl=imageViewToByte(img);
                    Product p= new Product(name,description,price,quantity,unit,id_cate,discount,imgUrl,bigImgUrl);
                    productDB.insert(p);
                    Toast.makeText(Add_Product.this, "Add thanh cong", Toast.LENGTH_SHORT).show();
                    edt_name.setText("");
                    edt_description.setText("");
                    edt_price.setText("");
                    edt_unit.setText("");
                    edt_quantity.setText("");
                    edt_discount.setText("");
                    img.setImageResource(R.drawable.profile_picture);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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
                Toast.makeText(this, "Ban chua mo quyen", Toast.LENGTH_SHORT).show();
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
                img.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}