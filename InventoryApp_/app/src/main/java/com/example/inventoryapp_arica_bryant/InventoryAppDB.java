package com.example.inventoryapp_arica_bryant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class InventoryAppDB extends SQLiteOpenHelper {

    private final Context context;
    private static final String USERS_TABLE = "USERS_TABLE";
    private static final String COLUMN_USERS_NAME = "USERS_NAME";
    private static final String COLUMN_USERS_PASS = "USERS_PASS";
    private static final String COLUMN_ID = "ID";
    private static final String ITEM_TABLE = "ITEM_TABLE";
    private static final String COLUMN_ITEM_NAME = "ITEM_NAME";
    private static final String COLUMN_ITEM_QUANTITY = "ITEM_QUANTITY";
    private static final String COLUMN_ID_2 = "ID2";

    public InventoryAppDB(@Nullable Context context) {
        super(context, "inventoryApp.db", null, 1);
        this.context = context;
    }

    // Called the first time a database is accessed. This code generates the tables for the database.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + USERS_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USERS_NAME + " TEXT, " + COLUMN_USERS_PASS + " TEXT)";
        String createTable2Statement = "CREATE TABLE " + ITEM_TABLE + " (" + COLUMN_ID_2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ITEM_NAME + " TEXT, " + COLUMN_ITEM_QUANTITY + " INT)";
        db.execSQL(createTableStatement);
        db.execSQL(createTable2Statement);
    }

    // This is called if the version number of database changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ITEM_TABLE);
        onCreate(db);
    }

    // Method for inserting data into the user login table
    void addUser(UserInfo userInfo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues user = new ContentValues();

        user.put(COLUMN_USERS_NAME, userInfo.getUsername());
        user.put(COLUMN_USERS_PASS, userInfo.getPassword());

        long userId = db.insert(USERS_TABLE, null, user);
        if (userId == -1) {
            Toast.makeText(context, "Creation Failed", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Creation Successful", Toast.LENGTH_SHORT).show();
        }
    }

    // Method for inserting data into the item table
    void addItem(ItemInfo itemInfo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues item = new ContentValues();

        item.put(COLUMN_ITEM_NAME, itemInfo.getItemName());
        item.put(COLUMN_ITEM_QUANTITY, itemInfo.getItemQuantity());

        long itemId = db.insert(ITEM_TABLE, null, item);
        if (itemId == -1) {
            Toast.makeText(context, "Creation Failed", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Creation Successful", Toast.LENGTH_SHORT).show();
        }
    }

    // Deletes an item from the inventory table if it is found
    void deleteOne(ItemInfo itemInfo) {
        SQLiteDatabase db = this.getWritableDatabase();

        String queryString = "DELETE FROM " + ITEM_TABLE + " WHERE " + COLUMN_ID_2 + " = " + itemInfo.getItemId();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            Toast.makeText(context, "No item to delete", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Item Deleted Successfully", Toast.LENGTH_SHORT).show();
        }

        // Closes the cursor and database when finished
        cursor.close();
        db.close();
    }

    // Updates an item from the inventory table if it is found
    void updateOne(String row_id, ItemInfo itemInfo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues item = new ContentValues();

        item.put(COLUMN_ITEM_NAME, itemInfo.getItemName());
        item.put(COLUMN_ITEM_QUANTITY, itemInfo.getItemQuantity());

        long result = db.update(ITEM_TABLE, item, "ID2=?", new String[]{row_id});

        if (result == -1) {
            Toast.makeText(context, "There is no data", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Item Updated Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    // Handles getting all items
    public ArrayList<ItemInfo> getAllItems() {
        ArrayList<ItemInfo> returnList = new ArrayList<>();

        // Gets data from the database
        String queryString = "SELECT * FROM " + ITEM_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        // If there are objects in the database
        if (cursor.moveToFirst()) {
            // Loops and create objects until the end of database then return them in an Array list
            // If there are no items in the database, there will be no items displayed
            do {
                int itemId = cursor.getInt(0);
                String itemName = cursor.getString(1);
                int itemQuantity = cursor.getInt(2);

                ItemInfo newItem = new ItemInfo(itemName, itemQuantity, itemId);
                returnList.add(newItem);

            } while (cursor.moveToNext());
        }

        // Closes the cursor and database when finished
        cursor.close();
        db.close();

        return returnList;
    }

    // Handles gettings all users
    public List<UserInfo> getAllUsers() {
        List<UserInfo> returnList = new ArrayList<>();

        // Gets data from the database
        String queryString = "SELECT * FROM " + USERS_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        // If there are objects in the database
        if (cursor.moveToFirst()) {
            // Loops and create objects until the end of database then return them in an Array list
            // If there no items in the database, there will be no items displayed
            do {
                int userId = cursor.getInt(0);
                String userName = cursor.getString(1);
                String userPass = cursor.getString(2);

                UserInfo newUser = new UserInfo(userName, userPass, userId);
                returnList.add(newUser);

            } while (cursor.moveToNext());
        }

        // Closes the cursor and database when finished
        cursor.close();
        db.close();

        return returnList;
    }
}
