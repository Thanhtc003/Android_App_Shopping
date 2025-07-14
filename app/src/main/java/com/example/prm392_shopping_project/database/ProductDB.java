package com.example.prm392_shopping_project.database;

import static com.example.prm392_shopping_project.database.DatabaseConfig.PRODUCT_TABLE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.prm392_shopping_project.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDB extends AppDatabaseContext implements IGenericDB<Product>{

    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
    }

    public ProductDB(@Nullable Context context) {
        super(context);
    }

    @Override
    public long insert(Product product) {
        SQLiteDatabase db = super.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("price", product.getPrice());
        values.put("quantity", product.getQuantity());
        values.put("unit", product.getUnit());
        values.put("category_id", product.getCategoryId());
        values.put("discount", product.getDiscount());
        values.put("imageUrl", product.getImageUrl());
//        values.put("bigImageUrl", product.getBigImageUrl());
        long count = db.insert(PRODUCT_TABLE, null, values);
        return count;
    }

    @Override
    public long update(Product product) {
        Product oldProduct = this.getById(product.getId());
        if (oldProduct == null) {
            return -1;
        }
        SQLiteDatabase db = super.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("price", product.getPrice());
        values.put("quantity", product.getQuantity());
        values.put("unit", product.getUnit());
        values.put("discount", product.getDiscount());
        values.put("imageUrl", product.getImageUrl());
//        values.put("bigImageUrl", product.getBigImageUrl());
        long count = db.update(PRODUCT_TABLE, values, "id=?", new String[]{String.valueOf(product.getId())});
        return count;
    }

    @Override
    public long delete(int id) {
        SQLiteDatabase db = super.getWritableDatabase();
        long count = db.delete(PRODUCT_TABLE, "id =?", new String[]{String.valueOf(id)});
        return count;
    }

    @Override
    public Product getById(int id) {
        SQLiteDatabase db = super.getReadableDatabase();
        String query = "SELECT * FROM " + databaseConfig.PRODUCT_TABLE + " WHERE id = " + id;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            try {
                Product product = new Product(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getString(5),
                        cursor.getInt(6),
                        cursor.getInt(7),
                        cursor.getBlob(8),
                        cursor.getBlob(9));
                return product;
            }
            catch (Exception ex){
                throw new RuntimeException();
            }
        }
        db.close();
        return null;
    }

    @Override
    public List<Product> getAll(){
        List<Product> list = new ArrayList<>();
        String query = "SELECT * FROM " + PRODUCT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            int id= cursor.getInt(0);
            String name= cursor.getString(1);
            String description= cursor.getString(2);
            float price= cursor.getFloat(3);
            int quantity= cursor.getInt(4);
            String unit= cursor.getString(5);
            int category_id= cursor.getInt(6);
            int discount=cursor.getInt(7);
            byte[] img= cursor.getBlob(8);
//            byte[] imgBig= cursor.getBlob(9);

            Product product = new Product(id,name,description,price,quantity,unit,category_id,discount,img,img);
            list.add(product);
        }
        return list;
    }

    public List<Product> getDiscountProduct(){
        List<Product> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(PRODUCT_TABLE, null, null , null, null, null, "discount desc");

        while (cursor.moveToNext()) {
            int id= cursor.getInt(0);
            String name= cursor.getString(1);
            String description= cursor.getString(2);
            float price= cursor.getFloat(3);
            int quantity= cursor.getInt(4);
            String unit= cursor.getString(5);
            int category_id= cursor.getInt(6);
            int discount=cursor.getInt(7);
            byte[] img= cursor.getBlob(8);
//            byte[] imgBig= cursor.getBlob(9);

            Product product = new Product(id,name,description,price,quantity,unit,category_id,discount,img,img);
            list.add(product);
        }
        return list;
    }

    @Override
    public long seedingData() {
        long count = 0;
        List<Product> list = new ArrayList<>();
//        list.add(new Product("Dưa hấu", "Dưa hấu có hàm lượng nước cao và cũng cung cấp một số chất xơ.", 10.5, 100, "1kg", 1, 10, String.valueOf(card4), String.valueOf(b4)));
//        list.add(new Product("Đu đủ", "Đu đủ là loại trái cây có hàm lượng dinh dưỡng cao và ít calo.", 10, 100,"1kg",2, 30, String.valueOf(card3), String.valueOf(b3)));
//        list.add(new Product("Dâu", "Dâu tây là một loại trái cây có giá trị dinh dưỡng cao, chứa nhiều vitamin C.", 10, 100,"1kg",3, 20, String.valueOf(card2), String.valueOf(b2)));
//        list.add(new Product("Kiwi", "Chứa đầy đủ các chất dinh dưỡng như vitamin C, vitamin K, vitamin E, folate và kali.", 10, 100,"1kg",4, 10, String.valueOf(card1), String.valueOf(b1)));
        SQLiteDatabase db = super.getWritableDatabase();
        for (int i = 0; i < list.size(); i++) {
            Product product = list.get(i);
            ContentValues values = new ContentValues();
            values.put("name", product.getName());
            values.put("description", product.getDescription());
            values.put("price", product.getPrice());
            values.put("quantity", product.getQuantity());
            values.put("unit", product.getUnit());
            values.put("category_id", product.getCategoryId());
            values.put("discount", product.getDiscount());
            values.put("imageUrl", product.getImageUrl());
            values.put("bigImageUrl", product.getBigImageUrl());
            count = db.insert(PRODUCT_TABLE, null, values);
        }
        return count;
    }


    public List<Product> getByName(String s) {
        List<Product> list = new ArrayList<>();
        String query = "SELECT * FROM " + PRODUCT_TABLE + " where name like '%" + s+"%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            Product product = new Product(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getInt(7),
                    cursor.getBlob(8),
                    cursor.getBlob(9)
            );
            list.add(product);
        }
        return list;
    }
    public List<Product> getByCateId(int id) {
        List<Product> list = new ArrayList<>();
        String query = "SELECT * FROM " + PRODUCT_TABLE + " where category_id = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            Product product = new Product(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getInt(7),
                    cursor.getBlob(8),
                    cursor.getBlob(9)
            );
            list.add(product);
        }
        return list;
    }

    public Product getProductByCategoryId(int id) {
        SQLiteDatabase db = super.getReadableDatabase();
        String query = "SELECT * FROM " + databaseConfig.PRODUCT_TABLE + " WHERE category_id = " + id;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            try {
                Product product = new Product(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getString(5),
                        cursor.getInt(6),
                        cursor.getInt(7),
                        cursor.getBlob(8),
                        cursor.getBlob(9));
                return product;
            }
            catch (Exception ex){
                throw new RuntimeException();
            }
        }
        db.close();
        return null;
    }
}
