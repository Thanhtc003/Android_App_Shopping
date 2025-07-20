package com.example.prm392_shopping_project.database;

import static com.example.prm392_shopping_project.database.DatabaseConfig.ACCOUNT_TABLE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.prm392_shopping_project.model.Account;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class AccountDB extends AppDatabaseContext implements IGenericDB<Account>{
    public AccountDB(@Nullable Context context) {
        super(context);
    }

    @Override
    public long insert(Account account) {
        SQLiteDatabase db = super.getWritableDatabase();
        String isAdmin;
        ContentValues values = new ContentValues();
        values.put("email", account.getEmail());
        values.put("password", account.getPassword());
        values.put("date_created", account.getCreatedAt()+"");
        if (account.isAdmin() == false) {
            isAdmin = "0";
        } else {
            isAdmin = "1";
        }
        values.put("is_admin", isAdmin);
        long count = db.insert(ACCOUNT_TABLE, null, values);
        return count;
    }

    @Override
    public long update(Account account) {
        return 0;
    }

    @Override
    public long delete(int id) {
        return 0;
    }

    @Override
    public Account getById(int id) {
        return null;
    }

    public boolean isAdmin(String email) {
        String query = "SELECT * FROM Accounts WHERE email = '" + email+"'";
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        boolean isAdmin = false;
        if (cursor.moveToFirst()) {
            int isAdmin_temp = cursor.getInt(3);
            if (isAdmin_temp == 0) {
                isAdmin  = false;
            } else {
                isAdmin = true;
            }
        }
        return isAdmin;
    }

    @Override
    public List<Account> getAll() {
        List<Account> list = new ArrayList<>();
        String query = "SELECT * FROM Accounts";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {

           String email =  cursor.getString(0);
           int pass =  cursor.getInt(1);
            Date c = new Date(cursor.getLong(2));
            int isAdmin_temp = cursor.getInt(3);
            boolean isAdmin;
            if (isAdmin_temp == 0) {
                isAdmin = false;
            } else {
                isAdmin = true;
            }
            Account account = new Account(email, pass, c, isAdmin);
            list.add(account);
        }
        return list;
    }

    @Override
    public long seedingData() {
        long millis = System.currentTimeMillis();
        Date today = new Date(millis);
        long count = 0;
        Account a = new Account("0123", 1234, today, false);
        Account b = new Account("012345", 1234, today, false);
        Account c = new Account("01234", 1234, today, true);
        count = insert(a);
        count = insert(b);
        count = insert(c);

        return count;
    }

    public Boolean checkUserName(String username) {
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +ACCOUNT_TABLE + " " +
                "where email=?", new String[]{username});
        if (cursor.getCount() > 0){
            return true;
        } else{
            return false;
        }
    }

    public Boolean checkUserNamePassword(String username, String password) {
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +ACCOUNT_TABLE + " " +
                "where email=? and password = ?", new String[]{username, password});
        if (cursor.getCount() > 0){
            return true;
        } else{
            return false;
        }
    }

    public Account getByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ACCOUNT_TABLE + " WHERE email = ?", new String[]{email});
        Account account = null;
        if (cursor.moveToFirst()) {
            String accountEmail = cursor.getString(0);
            int password = cursor.getInt(1);
            Date createdAt = Date.valueOf(cursor.getString(2));
            boolean isAdmin = cursor.getInt(3) == 1;
            account = new Account(accountEmail, password, createdAt, isAdmin);
        }
        cursor.close();
        db.close();
        return account;
    }
}
