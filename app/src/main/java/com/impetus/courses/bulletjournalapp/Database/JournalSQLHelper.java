package com.impetus.courses.bulletjournalapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class JournalSQLHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="bujo.db";
    public static final String TABLE_NAME="journal_table";
    private static final int VERSION=3;

    public static final String TITLE="title";
    public static final String CONTENT="content";

    public JournalSQLHelper( Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE " + TABLE_NAME+ " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, "+ TITLE + " TEXT,"+
            CONTENT+ " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean insertJournalData(String title, String content){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(TITLE,title);
        contentValues.put(CONTENT,content);
        long result =db.insert(TABLE_NAME,null,contentValues);
        if(result==-1){
            return false;
        }else {
            return true;
        }
    }

    public Cursor getAllJournalData(){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor res= db.rawQuery("SELECT * FROM "+ TABLE_NAME,null);
        return res;
    }
}
