package com.example.daandroid.utls;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.security.AccessControlContext;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bookManager.db";
    private static final int DATABASE_VERSION = 1;
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_categories_table = "CREATE TABLE theloai(id INTEGER PRIMARY KEY, tenloai TEXT)";
        String create_books_table = "CREATE TABLE sach(id INTEGER PRIMARY KEY, tensach TEXT, mota TEXT, gia INTEGER, hinh BLOB, maloai INTEGER,FOREIGN KEY(maloai) REFERENCES sach(id))";
        sqLiteDatabase.execSQL(create_categories_table);
        sqLiteDatabase.execSQL(create_books_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String drop_categories_table = "DROP TABLE IF EXISTS loaisach";
        String drop_books_table = "DROP TABLE IF EXISTS sach";
        sqLiteDatabase.execSQL(drop_categories_table);
        sqLiteDatabase.execSQL(drop_books_table);
        onCreate(sqLiteDatabase);
    }

//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String create_students_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT)", TABLE_NAME, KEY_ID, KEY_NAME, KEY_ADDRESS, KEY_PHONE_NUMBER);
//        db.execSQL(create_students_table);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        String drop_students_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
//        db.execSQL(drop_students_table);
//
//        onCreate(db);
//    }
}
