package com.example.prm392_shopping_project.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.prm392_shopping_project.model.Product;

import java.util.ArrayList;
import java.util.List;

public class AppDatabaseContext extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PRM392_Project";
    private static final int DATABASE_VERSION = 2;

    public static DatabaseConfig databaseConfig = new DatabaseConfig();

    public AppDatabaseContext(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        InitDatabase(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        onCreate(db);
    }

    private void InitDatabase(SQLiteDatabase db){
        db.execSQL(databaseConfig.Accounts);
        db.execSQL(databaseConfig.Customers);
        db.execSQL(databaseConfig.Products);
        db.execSQL(databaseConfig.Categories);
        db.execSQL(databaseConfig.Orders);
        db.execSQL(databaseConfig.OrderDetails);

    }

}
