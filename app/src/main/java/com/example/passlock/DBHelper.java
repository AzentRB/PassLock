package com.example.passlock;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(@Nullable Context context) {
        super(context, "Pass.db", null,  1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT)");
        MyDB.execSQL("create Table pass(user_name TEXT , entity_name TEXT ,password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("Drop Table if exists users");
        MyDB.execSQL("Drop Table if exists pass");

    }
    public Boolean insertData(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = MyDB.insert("users", null,contentValues);
        return result != -1;
    }
    public Boolean insert_data(String entity_name, String user_name, String Pass)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_name", user_name);
        contentValues.put("entity_name", entity_name);
        contentValues.put("password", Pass);
        long result = MyDB.insert("pass", null, contentValues);
        return result != -1;

    }
    public boolean checkusername(String username)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
         Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
        return cursor.getCount() > 0;
    }
    public boolean chechusernamepassword(String username, String password){
    SQLiteDatabase MyDB = this.getWritableDatabase();
   Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[]{username,password});
        return cursor.getCount() > 0;
    }

    public Cursor viewData(String usn){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        return MyDB.rawQuery("Select entity_name from pass where user_name = ?", new String[]{usn});
    }
    public Cursor get_decrypted(String usn, String ename)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        return MyDB.rawQuery("Select * from pass where user_name = ? and entity_name = ?", new String[]{usn, ename});
    }
}
