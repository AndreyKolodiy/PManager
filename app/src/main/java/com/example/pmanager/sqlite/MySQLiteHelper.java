package com.example.pmanager.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.pmanager.model.Account;

import java.util.LinkedList;
import java.util.List;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Accounts";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ACCOUNT_TABLE = "CREATE TABLE accounts( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "login TEXT, " +
                "password TEXT  )";
        db.execSQL(CREATE_ACCOUNT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS accounts");
        this.onCreate(db);
    }

    private static final String TABLE_ACCOUNT = "accounts";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LOGIN = "login";
    private static final String KEY_PASSWORD = "password";

    private static final String[] COLUMNS = {KEY_ID, KEY_NAME, KEY_LOGIN, KEY_PASSWORD};

    public void addAccount(Account account) {
        Log.d("addAccount", account.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, account.getName());
        values.put(KEY_LOGIN, account.getLogin());
        values.put(KEY_PASSWORD, account.getPassword());
        db.insert(TABLE_ACCOUNT, null, values);
        db.close();
    }

    public Account getAccount(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ACCOUNT, COLUMNS,
                " id = ?", new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Account account = new Account();
        account.setId(Integer.parseInt(cursor.getString(0)));
        account.setName(cursor.getString(1));
        account.setLogin(cursor.getString(2));
        account.setPassword(cursor.getString(3));
        Log.d("getAccount(" + id + ")", account.toString());
        return account;
    }

    public List<Account> getAllAccount() {
        List<Account> accounts = new LinkedList<Account>();
        String query = "SELECT  * FROM " + TABLE_ACCOUNT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Account account = null;
        if (cursor.moveToFirst()) {
            do {
                account = new Account();
                account.setId(Integer.parseInt(cursor.getString(0)));
                account.setName(cursor.getString(1));
                account.setLogin(cursor.getString(2));
                account.setPassword(cursor.getString(3));
                accounts.add(account);
            } while (cursor.moveToNext());
        }
        Log.d("getAllAccount()", accounts.toString());
        return accounts;
    }

    public int updateAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", account.getName());
        values.put("login", account.getLogin());
        values.put("password", account.getPassword());
        int i = db.update(TABLE_ACCOUNT, values,
                KEY_ID + " = ?",
                new String[]{String.valueOf(account.getId())});
        db.close();
        return i;
    }

    public void deleteAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ACCOUNT, KEY_ID + " = ?",
                new String[]{String.valueOf(account.getId())});
        db.close();
        Log.d("deleteAccount", account.toString());
    }
}
