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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.prm392_shopping_project.database.ProductDB;
import com.example.prm392_shopping_project.model.Product;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Update_Product extends AppCompatActivity {
    EditText edt_name,edt_description,edt_price,edt_unit,edt_quantity,edt_discount;
    ImageView img_imv;
    SQLiteDatabase db;
    ProductDB productDB;
    Button btn_upload;
    final int REQUEST_CODE_GALLERY = 999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        Bundle bundle= getIntent().getExtras();
        if(bundle==null) return;
        Product pro= (Product) bundle.get("object_product");
        edt_name= findViewById(R.id.edt_newname);
        edt_description= findViewById(R.id.edt_newdescription);
        edt_price= findViewById(R.id.edt_newprice);
        edt_unit= findViewById(R.id.edt_newunit);
        edt_quantity= findViewById(R.id.edt_newquantity);
        edt_discount=findViewById(R.id.edt_newdiscount);
        img_imv= findViewById(R.id.imv_newimg);
        btn_upload=findViewById(R.id.btn_uploadImg);

        edt_name.setText(pro.getName());
        edt_description.setText(pro.getDescription());
        edt_price.setText(String.valueOf(pro.getPrice()));
        edt_unit.setText(pro.getUnit());
        edt_quantity.setText(String.valueOf(pro.getQuantity()));
        edt_discount.setText(String.valueOf(pro.getDiscount()));
        byte[] img = (byte[]) pro.getImageUrl();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        img_imv.setImageBitmap(bitmap);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(Update_Product.this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);
            }
        });

    }

    public void onUpdate(View view) {
        Bundle bundle= getIntent().getExtras();
        if(bundle==null) return;
        Product pro= (Product) bundle.get("object_product");

        pro.setName(edt_name.getText().toString());
        pro.setDescription(edt_description.getText().toString());
        pro.setPrice(Double.valueOf(edt_price.getText().toString()));
        pro.setUnit(edt_unit.getText().toString());
        pro.setQuantity(Integer.parseInt(edt_quantity.getText().toString()));
        pro.setDiscount(Integer.parseInt(edt_discount.getText().toString()));

        byte[] imgUrl= imageViewToByte(img_imv);
        byte[] bigImgUrl=imageViewToByte(img_imv);
        pro.setImageUrl(imgUrl);

        productDB= new ProductDB(this);
        productDB.update(pro);
        Toast.makeText(Update_Product.this,"Update thanh cong",Toast.LENGTH_LONG).show();
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
                img_imv.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}