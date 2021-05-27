package com.lywow.collector.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBAdapter {
    private static final String DB_NAME = "collector.db";
    private static final String DB_TABLE = "motion";
    private static final int DB_VERSION = 1;

    public static final String KEY_ID = "id";
    public static final String KEY_VIEW = "view";
    public static final String KEY_ACTIVITY = "activity";
    public static final String KEY_PATH = "path";
    public static final String KEY_TIME = "time";

    private SQLiteDatabase db;
    private final Context context;
    private DBOpenHelper dbOpenHelper;

    private static class DBOpenHelper extends SQLiteOpenHelper {
        public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        private static final String DB_CREATE = "create table " +
                DB_TABLE + " (" + KEY_ID + " text primary key, " +
                KEY_VIEW + " text , " + KEY_ACTIVITY + " text," +
                KEY_PATH + " text ," + KEY_TIME + " integer);";

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
        } catch (SQLiteException ex) {
            db = dbOpenHelper.getReadableDatabase();
        }
    }

    public void close() {
        if (db != null) {
            db.close();
            db = null;
        }
    }

    public long insert(Motion motion) {
        ContentValues newValues = new ContentValues();

        newValues.put(KEY_ID, motion.getID());
        newValues.put(KEY_VIEW, motion.view);
        newValues.put(KEY_ACTIVITY, motion.activity);
        newValues.put(KEY_PATH, motion.path);
        newValues.put(KEY_TIME, motion.time);

        return db.insert(DB_TABLE, null, newValues);
    }

    public long deleteAllData() {
        return db.delete(DB_TABLE, null, null);
    }

    public long deleteOneData(int id) {
        return db.delete(DB_TABLE, KEY_ID + "=" + id, null);
    }

    public List<Motion> queryAllData() {
        Cursor results = db.query(DB_TABLE, new String[]{KEY_ID, KEY_VIEW, KEY_ACTIVITY, KEY_PATH, KEY_TIME}, null, null, null, null, null);
        return ConvertToContact(results);
    }

    public List<Motion> queryOneData(String id) {
        Cursor results = db.query(DB_TABLE, new String[]{KEY_ID, KEY_VIEW, KEY_ACTIVITY, KEY_PATH, KEY_TIME}, KEY_ID + "='" + id + "'", null, null, null, null);
        return ConvertToContact(results);
    }
//    public long updateOneData(Motion motion){
//        ContentValues updateValues = new ContentValues();
//
//        updateValues.put(KEY_ID, motion.getEnglish());
//        updateValues.put(KEY_CHINESE, motion.getChinese());
//        updateValues.put(KEY_PHONETIC, motion.getPhonetic());
//        updateValues.put(KEY_FREQUENCY, motion.getFrequency());
//        updateValues.put(KEY_TIME, motion.getTime());
//
//        return db.update(DB_TABLE, updateValues,  KEY_ID + "='" + motion.getEnglish()+"'", null);
//    }

    private List<Motion> ConvertToContact(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        List<Motion> motions = new ArrayList<>();
        for (int i = 0; i < resultCounts; i++) {
            Motion motion = new Motion();
            motion.view = cursor.getString(cursor.getColumnIndex(KEY_VIEW));
            motion.activity = cursor.getString(cursor.getColumnIndex(KEY_ACTIVITY));
            motion.path = cursor.getString(cursor.getColumnIndex(KEY_PATH));
            motion.time = cursor.getLong(cursor.getColumnIndex(KEY_TIME));
            motions.add(motion);
            cursor.moveToNext();
        }
        return motions;
    }

    public void deleteData() {
        db.execSQL("delete from " + DB_TABLE);
    }
}
