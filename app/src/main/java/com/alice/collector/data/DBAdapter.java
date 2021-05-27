package com.alice.collector.data;

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
    public static final String KEY_TYPE = "type";
    public static final String KEY_NAME = "name";
    public static final String KEY_INFO = "info";
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
                KEY_TYPE + " integer , " + KEY_NAME + " text," +
                KEY_INFO + " text ," + KEY_TIME + " integer);";

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

        newValues.put(KEY_ID, motion.getId());
        newValues.put(KEY_TYPE, motion.type);
        newValues.put(KEY_NAME, motion.name);
        newValues.put(KEY_INFO, motion.info);
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
        Cursor results = db.query(DB_TABLE, new String[]{KEY_ID, KEY_TYPE, KEY_NAME, KEY_INFO, KEY_TIME}, null, null, null, null, null);
        return ConvertToContact(results);
    }

    public List<Motion> queryFinalTime(long time) {
        Cursor results = db.query(DB_TABLE, new String[]{KEY_ID, KEY_TYPE, KEY_NAME, KEY_INFO, KEY_TIME}, KEY_TIME + " > " + time, null, null, null, KEY_TIME);
        return ConvertToContact(results);
    }

    private List<Motion> ConvertToContact(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        List<Motion> motions = new ArrayList<>();
        for (int i = 0; i < resultCounts; i++) {
            Motion motion = new Motion();
            motion.id = cursor.getString(cursor.getColumnIndex(KEY_ID));
            motion.type = cursor.getInt(cursor.getColumnIndex(KEY_TYPE));
            motion.name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            motion.info = cursor.getString(cursor.getColumnIndex(KEY_INFO));
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
