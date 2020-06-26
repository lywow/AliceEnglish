package com.alice.aliceenglish.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.alice.aliceenglish.entity.Word;

import java.util.ArrayList;
import java.util.List;

public class DBAdapter {
    private static final String DB_NAME = "contact.db";
    private static final String DB_TABLE = "contactinfo";
    private static final int DB_VERSION = 1;

    public static final String KEY_ID = "english";
    public static final String KEY_CHINESE = "chinese";
    public static final String KEY_PHONETIC = "phonetic";
    public static final String KEY_FREQUENCY = "frequency";
    public static final String KEY_TIME = "time";

    private String english;
    private String chinese;
    private String phonetic;
    private int frequency;
    private long time;

    private SQLiteDatabase db;
    private final Context context;
    private DBOpenHelper dbOpenHelper;

    private static class DBOpenHelper extends SQLiteOpenHelper {
        public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context, name, factory, version);
        }

        private static final String DB_CREATE = "create table " +
                DB_TABLE + " (" + KEY_ID + " text primary key, " +
                KEY_CHINESE + " text , " + KEY_PHONETIC + " text," + KEY_FREQUENCY + " integer ," + KEY_TIME + " text);";

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
            _db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(_db);
        }

    }

    public DBAdapter(Context _context) {
        context = _context;
    }

    public void open() throws SQLiteException {
        dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, DB_VERSION);
        try {
            db = dbOpenHelper.getWritableDatabase();
        }catch (SQLiteException ex) {
            db = dbOpenHelper.getReadableDatabase();
        }
    }

    public void close() {
        if (db != null){
            db.close();
            db = null;
        }
    }

    public long insert(Word word) {
        ContentValues newValues = new ContentValues();

        newValues.put(KEY_ID, word.getEnglish());
        newValues.put(KEY_CHINESE, word.getChinese());
        newValues.put(KEY_PHONETIC, word.getPhonetic());
        newValues.put(KEY_FREQUENCY, word.getFrequency());
        newValues.put(KEY_TIME, word.getTime());

        return db.insert(DB_TABLE, null, newValues);

    }
    public long deleteAllData() {
        return db.delete(DB_TABLE, null, null);
    }
    public long deleteOneData(int id) {
        return db.delete(DB_TABLE,  KEY_ID + "=" + id, null);
    }
    public List<Word> queryAllData() {
        Cursor results = db.query(DB_TABLE, new String[] { KEY_ID, KEY_CHINESE, KEY_PHONETIC, KEY_FREQUENCY, KEY_TIME}, null, null, null, null, null);
        return ConvertToContact(results);
    }
    public List<Word> queryMemoryData() {
        Cursor results = db.query(DB_TABLE, new String[] { KEY_ID, KEY_CHINESE, KEY_PHONETIC, KEY_FREQUENCY, KEY_TIME}, KEY_FREQUENCY+ ">" +0, null, null, null, null);
        return ConvertToContact(results);
    }
    public List<Word> queryOneData(String id) {
        Cursor results =  db.query(DB_TABLE, new String[] { KEY_ID, KEY_CHINESE, KEY_PHONETIC, KEY_FREQUENCY, KEY_TIME}, KEY_ID + "='" + id+"'", null, null, null, null);
        return ConvertToContact(results);
    }
    public long updateOneData(Word word){
        ContentValues updateValues = new ContentValues();

        updateValues.put(KEY_ID, word.getEnglish());
        updateValues.put(KEY_CHINESE, word.getChinese());
        updateValues.put(KEY_PHONETIC, word.getPhonetic());
        updateValues.put(KEY_FREQUENCY, word.getFrequency());
        updateValues.put(KEY_TIME, word.getTime());

        return db.update(DB_TABLE, updateValues,  KEY_ID + "='" + word.getEnglish()+"'", null);
    }

    private List<Word> ConvertToContact(Cursor cursor){
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()){
            return null;
        }
        List<Word> words=new ArrayList<>();
        for (int i = 0 ; i<resultCounts; i++){
            Word word = new Word();
            word.setEnglish(cursor.getString(cursor.getColumnIndex(KEY_ID)));
            word.setChinese(cursor.getString(cursor.getColumnIndex(KEY_CHINESE)));
            word.setPhonetic(cursor.getString(cursor.getColumnIndex(KEY_PHONETIC)));
            word.setFrequency(cursor.getInt(cursor.getColumnIndex(KEY_FREQUENCY)));
            word.setTime(cursor.getString(cursor.getColumnIndex(KEY_TIME)));
            words.add(word);
            cursor.moveToNext();
        }
        return words;
    }

    public void deleteData(){
        db.execSQL("delete from "+DB_TABLE);
    }
}

