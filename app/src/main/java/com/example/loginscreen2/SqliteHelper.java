package com.example.loginscreen2;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SqliteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "loopwiki.com";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_USERS = "users";
    public static final String KEY_ID = "id";
    public static final String KEY_USER_NAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USERS
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_USER_NAME + " TEXT, "
            + KEY_EMAIL + " TEXT, "
            + KEY_PASSWORD + " TEXT"
            + " ) ";
    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS);
    }

    public void addUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_USER_NAME, user.username);
        cv.put(KEY_EMAIL, user.email);
        cv.put(KEY_PASSWORD, user.password);

        long insertid = db.insert(TABLE_USERS, null, cv);
    }

    public User Authenticator(User user)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD},
                KEY_EMAIL + "=?",
                new String[]{user.email},
                null, null, null);
        if(cursor!=null && cursor.moveToFirst() && cursor.getCount()>0)
        {
            User user1 = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            if(user1.password.equalsIgnoreCase(user1.password))
            {
                return user1;
            }
        }
        return null;
    }
    public  boolean isEmailExists(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD},
                KEY_EMAIL + "=?",
                new String[]{email},
                null, null, null);
        if(cursor!=null && cursor.moveToFirst() && cursor.getCount()>0)
        {
            return true;
        }
        return false;
    }
    public boolean isPasswordMatch(String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD},
                KEY_PASSWORD + "=?",
                new String[]{password},
                null, null, null);
        if(cursor!=null && cursor.moveToFirst() && cursor.getCount()>0)
        {
            return true;
        }
        return false;
    }
}
