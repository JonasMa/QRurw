package com.urw.qr.qr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jonny on 05.07.2015.
 */
public class Database {

    // Parameters for the database
    private static final String DATABASE_NAME = "recentShit.db";
    private static final int DATABASE_VERSION = 1;

    // Name of database table
    private static final String DATABASE_TABLE = "shititems";
    // Set the names for database columns in table
    public static final String KEY_ID = "_id";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_QRID = "id";

    // Stor index of task-column
    public static final int COLUMN_CONTENT_INDEX = 1;
    public static final int COLUMN_QRID_INDEX = 2;

    public static final String TAG = "Database";

    private QRDBOpenHelper dbHelper;
    private SQLiteDatabase db;

    public Database(Context context) {
        dbHelper = new QRDBOpenHelper(context, DATABASE_NAME, null,
                DATABASE_VERSION);

    }

    private class QRDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " ("
                + KEY_ID + " integer primary key autoincrement, "
                + KEY_CONTENT + " text not null, "
                + KEY_QRID + " text);";


        public QRDBOpenHelper(Context c, String dbname,
                                SQLiteDatabase.CursorFactory factory, int version) {
            super(c, dbname, factory, version);
            Log.d(TAG, DATABASE_CREATE);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public void open() throws SQLException {
        try {
            Log.d(TAG, "try: getWritableDatabase()");
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            Log.d(TAG, "catch: getReadableDatabase()");
            db = dbHelper.getReadableDatabase();
        }
        Log.d(TAG, "...succeeded");
    }

    public void close() {
        db.close();
    }


    public long insertQRCode(QRCode item) {
        ContentValues newQRValues = new ContentValues();

        newQRValues.put(KEY_CONTENT, item.getContent());
        newQRValues.put(KEY_QRID, item.getID());
        Log.d(TAG, newQRValues.toString());

        return db.insert(DATABASE_TABLE, null, newQRValues);
    }

    public void removeQRCode(QRCode item) {
        String whereClause = KEY_CONTENT + " = '" + item.getContent() + "' AND " +
                KEY_QRID + " = '" + item.getID() + "'";

        db.delete(DATABASE_TABLE, whereClause, null);
    }

    public Cursor getAllShitItems() {
        return db.query(DATABASE_TABLE, new String[] {KEY_ID, KEY_CONTENT, KEY_QRID},
                null, null, null, null, null);
    }

}
