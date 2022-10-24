package com.example.dataapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
// Create database for items
public class HomeDBHelper extends SQLiteOpenHelper {
    // Assign database title
    public static final String DATABASE_NAME = "Userdata.db";

    // Call DB helper
    public HomeDBHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }
    @Override
    // Create DB
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Userdetails(name TEXT primary key, contact TEXT)");
    }
    @Override
    // Don't create DB if it already exists
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("drop Table if exists Userdetails");
    }
    // Insert new user data
    public Boolean insertuserdata(String name, String contact)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("contact", contact);
        long result=DB.insert("Userdetails", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    // Update user items
    public void updateuserdata(String name, String value)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("contact", value);

        DB.update("Userdetails", contentValues, "name=?", new String[]{name});
        DB.close();
    }
    // Delete user items
    public void deletedata (String name)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.delete("Userdetails", "name=?", new String[]{name});
        DB.close();
    }
    // Fetch user items
    public Cursor getdata ()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails", null);
        return cursor;
    }
}