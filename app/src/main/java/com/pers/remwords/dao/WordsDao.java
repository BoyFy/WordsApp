package com.pers.remwords.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.pers.remwords.database.Dbhelper;
import com.pers.remwords.database.UsersDbHelper;
import com.pers.remwords.entity.Users;
import com.pers.remwords.entity.Words;

import java.util.ArrayList;
import java.util.List;

public class WordsDao {

    private Context context;
    private Dbhelper dbhelper;
    private UsersDbHelper usersDbHelper;
    private SQLiteDatabase db;
    private List<Words> wordsList = new ArrayList<>();
    private List<Users> usersList = new ArrayList<>();


    public WordsDao(Context context) {
        this.context = context;

    }

    public void insertDb(Words words, String tablename) {
        dbhelper = new Dbhelper(context, tablename + "Words.db", null, 1);
        db = dbhelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("word", words.getWord());
            contentValues.put("means", words.getMeans());
            db.insert("Words", "id", contentValues);
            contentValues.clear();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
    }

    //往背诵表里插入数据
    public void insertReDb(Words words, String tablename) {
        dbhelper = new Dbhelper(context, tablename + "Words.db", null, 1);
        db = dbhelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("word", words.getWord());
            contentValues.put("means", words.getMeans());
            db.insert("ReWords", "id", contentValues);
            contentValues.clear();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
    }

    public void insertUSersDb(Users users) {
        usersDbHelper = new UsersDbHelper(context, "Users.db", null, 1);
        db = usersDbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("uname", users.getUname());
            contentValues.put("psd", users.getPsd());
            db.insert("Users", null, contentValues);
            contentValues.clear();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
    }

    public void delectDb(Words words, String tablename) {
        dbhelper = new Dbhelper(context, tablename + "Words.db", null, 1);
        db = dbhelper.getWritableDatabase();
        db.beginTransaction();
        try {
            Log.d("TAG", "delectDb: " + words.getMeans());
            db.delete("Words", "word= ? and means= ?", new String[]{words.getWord(), words.getMeans()});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
    }

    public List<Words> selectDb(String tablename) {
        dbhelper = new Dbhelper(context, tablename + "Words.db", null, 1);
        db = dbhelper.getWritableDatabase();
        Cursor cursor = db.query("Words", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String word = cursor.getString(cursor.getColumnIndex("word"));
                String means = cursor.getString(cursor.getColumnIndex("means"));
                Words words = new Words(word, means);
                wordsList.add(words);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return wordsList;
    }
    //从背诵表里查询数据
    public List<Words> selectReDb(String tablename) {
        dbhelper = new Dbhelper(context, tablename + "Words.db", null, 1);
        db = dbhelper.getWritableDatabase();
        Cursor cursor = db.query("ReWords", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String word = cursor.getString(cursor.getColumnIndex("word"));
                String means = cursor.getString(cursor.getColumnIndex("means"));
                Words words = new Words(word, means);
                wordsList.add(words);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return wordsList;
    }

    public void changeDb(Words words, String tablename) {
        dbhelper = new Dbhelper(context, tablename + "Words.db", null, 1);
        db = dbhelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("word", words.getWord());
            contentValues.put("means", words.getMeans());
            db.update("Words", contentValues, "word=?", new String[]{words.getWord()});
            contentValues.clear();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
    }


    public Boolean selectUsersDb(String username, String psd) {
        usersDbHelper = new UsersDbHelper(context, "Users.db", null, 1);
        db = usersDbHelper.getWritableDatabase();
        Cursor cursor = db.query("Users", new String[]{"uname,psd"}, "uname= ? and psd=?", new String[]{username, psd}, null, null, null);
        if (cursor.getCount() == 0) {
            cursor.close();
            return false;
        } else {
            cursor.close();
            return true;
        }
    }

    public Boolean selectUsesUname(String username) {
        UsersDbHelper usersDbHelper = new UsersDbHelper(context, "Users.db", null, 1);
        SQLiteDatabase db = usersDbHelper.getWritableDatabase();
        Cursor cursor = db.query("Users", new String[]{"uname"}, "uname=?", new String[]{username}, null, null, null);
        if (cursor.getCount() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
