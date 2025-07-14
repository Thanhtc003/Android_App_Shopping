package com.example.prm392_shopping_project.database;

import static com.example.prm392_shopping_project.database.DatabaseConfig.ORDER_DETAIL_TABLE;
import static com.example.prm392_shopping_project.database.DatabaseConfig.ORDER_TABLE;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.prm392_shopping_project.model.OrderDetail;

import java.util.List;

public class OrderDetailDB extends AppDatabaseContext implements IGenericDB<OrderDetail> {


    public OrderDetailDB(@Nullable Context context) {
        super(context);
    }

    @Override
    public long insert(OrderDetail orderDetail) {
        SQLiteDatabase db = super.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("order_id", orderDetail.getOrderId());
        values.put("product_id", orderDetail.getProductId());
        values.put("price", orderDetail.getPrice());
        values.put("quantity", orderDetail.getQuantity());
        values.put("discount", orderDetail.getDiscount());
        long count = db.insert(ORDER_DETAIL_TABLE, null, values);
        return count;
    }

    @Override
    public long update(OrderDetail orderDetail) {
        return 0;
    }

    @Override
    public long delete(int id) {
        return 0;
    }

    @Override
    public OrderDetail getById(int id) {
        return null;
    }

    @Override
    public List<OrderDetail> getAll() {
        return null;
    }

    @Override
    public long seedingData() {
        return 0;
    }
}
