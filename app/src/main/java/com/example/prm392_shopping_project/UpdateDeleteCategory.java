package com.example.prm392_shopping_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prm392_shopping_project.database.CategoryDB;
import com.example.prm392_shopping_project.database.ProductDB;
import com.example.prm392_shopping_project.model.Category;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class UpdateDeleteCategory extends AppCompatActivity {
    TextView tv_id;
    EditText edt_name;
    ImageView imgView;
    Button btn_update, btn_dele, btn_uploadUpdate;
    CategoryDB db;
    ProductDB pdb;
    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_category);

        // Initialize DB
        db = new CategoryDB(this);
        pdb = new ProductDB(this);

        // Bind views
        tv_id = findViewById(R.id.tv_id);
        edt_name = findViewById(R.id.edt_nameupdate);
        imgView = findViewById(R.id.imgViewupdate);
        btn_update = findViewById(R.id.btn_update);
        btn_dele = findViewById(R.id.btn_dele);
        btn_uploadUpdate = findViewById(R.id.btn_uploadUpdate);

        // Get data from intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int id = intent.getIntExtra("id", -1);
        byte[] img = intent.getByteArrayExtra("image");

        if (img != null && img.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            imgView.setImageBitmap(bitmap);
        } else {
            imgView.setImageResource(R.drawable.b2); // Default image
        }

        tv_id.setText(String.valueOf(id));
        edt_name.setText(name);

        btn_uploadUpdate.setOnClickListener(view ->
                ActivityCompat.requestPermissions(UpdateDeleteCategory.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY));

        btn_update.setOnClickListener(view -> {
            int categoryId = Integer.parseInt(tv_id.getText().toString());
            String categoryName = edt_name.getText().toString().trim();
            if (categoryName.isEmpty()) {
                Toast.makeText(this, "Category name cannot be empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            Category category = new Category(categoryId, categoryName, imageViewToByte(imgView));
            db.update(category);
            Toast.makeText(this, "Category updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        });

        btn_dele.setOnClickListener(view -> {
            int categoryId = Integer.parseInt(tv_id.getText().toString());
            if (pdb.getProductByCategoryId(categoryId) == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Notification")
                        .setMessage("Are you sure you want to remove this category?")
                        .setIcon(R.drawable.ic_delete)
                        .setPositiveButton("Yes", (dialogInterface, i) -> {
                            db.delete(categoryId);
                            Toast.makeText(this, "Category deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .setNegativeButton("No", null)
                        .create().show();
            } else {
                Toast.makeText(this, "This category still has products!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private byte[] imageViewToByte(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(this, "Please grant this permission!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try (InputStream inputStream = getContentResolver().openInputStream(uri)) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}