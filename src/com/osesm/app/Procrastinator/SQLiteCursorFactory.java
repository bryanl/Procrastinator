package com.osesm.app.Procrastinator;

import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;

public class SQLiteCursorFactory implements SQLiteDatabase.CursorFactory {

    private boolean debugQueries = false;

    public SQLiteCursorFactory() {
        this.debugQueries = false;
    }

    public SQLiteCursorFactory(boolean debugQueries) {
        this.debugQueries = debugQueries;
    }

    @Override
    public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery,
                            String editTable, SQLiteQuery query) {
        if (debugQueries) {
            Logger.d("SQL: " + query.toString());
        }
        return new SQLiteCursor(db, masterQuery, editTable, query);
    }
}