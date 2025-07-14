package com.example.prm392_shopping_project.database;

import static com.example.prm392_shopping_project.database.DatabaseConfig.CUSTOMER_TABLE;
import static com.example.prm392_shopping_project.database.DatabaseConfig.PRODUCT_TABLE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.prm392_shopping_project.model.Customer;
import com.example.prm392_shopping_project.model.Product;

import java.util.List;

public class CustomerDB extends AppDatabaseContext implements IGenericDB<Customer>{
    public CustomerDB(@Nullable Context context) {
        super(context);
    }

    @Override
    public long insert(Customer customer) {
        SQLiteDatabase db = super.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("full_name", customer.getCustomerName());
        values.put("email", customer.getEmail());
        values.put("phone", customer.getPhone());
        values.put("address", customer.getAddress());
        long count = db.insert(CUSTOMER_TABLE, null, values);
        return count;
    }

    public int getMaxId(){
        SQLiteDatabase db = super.getReadableDatabase();
        String sql = "SELECT * FROM "+CUSTOMER_TABLE+" ORDER BY id DESC LIMIT 1";
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor.moveToFirst()) {
            try {
                int a = cursor.getInt(0);
                return a;
            }
            catch (Exception ex){
                throw new RuntimeException();
            }
        }
        db.close();
        return 0;

    }


    @Override
    public long update(Customer customer) {
        return 0;
    }

    @Override
    public long delete(int id) {
        return 0;
    }

    @Override
    public Customer getById(int id) {
        return null;
    }

    @Override
    public List<Customer> getAll() {
        return null;
    }

    @Override
    public long seedingData() {
        return 0;
    }
}
