package com.example.lab6productmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "productDB.db";
    public static final String TABLE_PRODUCTS = "products";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCTNAME = "productname";
    public static final String COLUMN_SKU = "SKU";

    // Constructor
    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // onCreate method to create the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_PRODUCTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_PRODUCTNAME + " TEXT,"
                + COLUMN_SKU + " INTEGER" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    // onUpgrade method to drop the table if it exists and create a new one
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    // Method to add a new product
    public void addProduct(String productName, int sku) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, productName);
        values.put(COLUMN_SKU, sku);
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    // Method to find a product by name
    public String findProduct(String productName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCTNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{productName});

        if (cursor.moveToFirst()) {
            String productInfo = "ID: " + cursor.getInt(0) + ", SKU: " + cursor.getInt(2);
            cursor.close();
            db.close();
            return productInfo;
        } else {
            cursor.close();
            db.close();
            return "Product not found";
        }
    }

    // Method to delete a product by name
    public boolean deleteProduct(String productName) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_PRODUCTS, COLUMN_PRODUCTNAME + " = ?", new String[]{productName});
        db.close();
        return result > 0;
    }
}
