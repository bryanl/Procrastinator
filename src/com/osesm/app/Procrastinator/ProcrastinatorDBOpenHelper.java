package com.osesm.app.Procrastinator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProcrastinatorDBOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "procrastinator.db";
    private static final String ARTICLE_TABLE = "articles";
    private static final int DATABASE_VERSION = 1;

    private static final String KEY_ID = "_id";
    private static final String ARTICLE_ID_COLUMN = "article_id";
    private static final String ARTICLE_LAST_COMMENT_COUNT_COLUMN = "last_comment_count";
    private static final String ARTICLE_LAST_POINT_COUNT_COLUMN = "last_point_count";
    private static final String ARTICLE_COMMENT_COUNT_COLUMN = "comment_count";
    private static final String ARTICLE_POINT_COUNT_COLUMN = "point_count";

    private static final String DATABASE_CREATE = "create table " +
            ARTICLE_TABLE + " (" + KEY_ID +
            " integer primary key autoincrement, " +
            ARTICLE_ID_COLUMN + " text not null, " +
            ARTICLE_LAST_COMMENT_COUNT_COLUMN + " integer default 0" +
            ARTICLE_LAST_POINT_COUNT_COLUMN + " integer default 0" +
            ARTICLE_COMMENT_COUNT_COLUMN + " integer default 0" +
            ARTICLE_POINT_COUNT_COLUMN + " integer default 0" +
            ");";

    public ProcrastinatorDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Logger.w("Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all data");
    }
}
