package com.pers.remwords.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.pers.remwords.entity.Words;

import java.util.ArrayList;
import java.util.List;

public class Dbhelper extends SQLiteOpenHelper {

    private Context context;

    private final String CREATE_WORDS="create table Words(id integer primary key autoincrement,word text,means text)";
    private final String CREATE_REWORDS="create table ReWords(id integer primary key autoincrement,word text,means text)";

    public Dbhelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WORDS);
        db.execSQL(CREATE_REWORDS);
        initwords(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("drop table if exists Words");
//        db.execSQL("drop table if exists CREATE_USERS");
//        onCreate(db);

    }

    public void initwords(SQLiteDatabase db){
        List<Words> wordsList=new ArrayList<Words>(){
            {
                add(new Words("Hello","你好"));
                add(new Words("aback","向后地"));
                add(new Words("abacterial","非细菌性的"));
                add(new Words("abandonee","被遗弃者,被委付者"));
                add(new Words("double","两倍"));
                add(new Words("hothouse","温室"));
                add(new Words("hotshot","快车"));
                add(new Words("myth","神话"));
                add(new Words("mythoheroic","歌颂神话英雄事迹的"));
                add(new Words("surcease","停止"));
                add(new Words("sure","确信某事"));
                add(new Words("unscrewed","没有扭紧"));
                add(new Words("whetter","磨刀人"));
                add(new Words("which","哪个"));
            }
        };
        for (Words words:wordsList) {
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
            }
        }
    }
}
