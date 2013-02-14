package com.osesm.app.Procrastinator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import com.osesm.app.Procrastinator.models.Article;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseAdapter {
    private static final int DATABASE_VERSION = 1;

    public static final String KEY_ID = "_id";

    public static final String ARTICLE_FIRST_SEEN_COLUMN = "first_seen";
    public static final String ARTICLE_ID_COLUMN = "article_id";
    public static final String ARTICLE_URL_COLUMN = "url";
    public static final String ARTICLE_TITLE_COLUMN = "title";
    public static final String ARTICLE_LAST_COMMENT_COUNT_COLUMN = "last_comment_count";
    public static final String ARTICLE_LAST_POINT_COUNT_COLUMN = "last_score";
    public static final String ARTICLE_COMMENT_COUNT_COLUMN = "comment_count";
    public static final String ARTICLE_SCORE_COLUMN = "score";
    public static final String ARTICLE_USER_COLUMN = "user";
    public static final String ARTICLE_DESCRIPTION_COLUMN = "description";

    public static final String TOP_ID_COLUMN = "article_id";
    public static final String TOP_RANK_COLUMN = "rank";
    public static final String TOP_TYPE_COLUMN = "type";


    private final Context context;
    private SQLiteDatabase db;
    private DatabaseHelper helper;

    public DatabaseAdapter(Context context) {
        this.context = context;
    }

    public DatabaseAdapter open() {
        SQLiteDatabase.CursorFactory cursorFactory = new SQLiteCursorFactory(true);
        helper = new DatabaseHelper(context, cursorFactory);
        db = helper.getReadableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    public Cursor findArticle(String articleId) {

        String[] projection = {
                ARTICLE_ID_COLUMN,
                ARTICLE_COMMENT_COUNT_COLUMN,
                ARTICLE_SCORE_COLUMN
        };

        String selection = ARTICLE_ID_COLUMN + "=?";
        String[] selectionArgs = {articleId};

        return db.query(ARTICLE_TABLE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }

    public boolean updateArticle(Article article) {
        Logger.d("Updating article: " + article);
        ContentValues values = new ContentValues();
        values.put(ARTICLE_ID_COLUMN, article.getItemId());
        values.put(ARTICLE_TITLE_COLUMN, article.getTitle());
        values.put(ARTICLE_SCORE_COLUMN, article.getScore());
        values.put(ARTICLE_COMMENT_COUNT_COLUMN, article.getComments());

        String selection = ARTICLE_ID_COLUMN + " = ?";
        String[] selectionArgs = {article.getItemId()};

        int count = db.update(ARTICLE_TABLE, values, selection, selectionArgs);

        return count == 1;
    }

    public boolean createArticle(Article article) {
        Logger.d("Saving article: " + article);
        ContentValues values = new ContentValues();
        values.put(ARTICLE_FIRST_SEEN_COLUMN, System.currentTimeMillis() / 1000);
        values.put(ARTICLE_ID_COLUMN, article.getItemId());
        values.put(ARTICLE_TITLE_COLUMN, article.getTitle());
        values.put(ARTICLE_SCORE_COLUMN, article.getScore());
        values.put(ARTICLE_COMMENT_COUNT_COLUMN, article.getComments());
        values.put(ARTICLE_DESCRIPTION_COLUMN, article.getDescription());
        values.put(ARTICLE_USER_COLUMN, article.getUser());
        values.put(ARTICLE_URL_COLUMN, article.getUrl());

        long id = db.insert(ARTICLE_TABLE, null, values);

        return id != -1;
    }

    public boolean updateRank(Article article, HackerNewsApi.TopType type, int rank) {
        Logger.d("Updating rank for " + article.getItemId() + " to " + rank + " in " + type);

        ContentValues values = new ContentValues();
        values.put(TOP_ID_COLUMN, article.getItemId());
        values.put(TOP_TYPE_COLUMN, convertTypeToInt(type));
        values.put(TOP_RANK_COLUMN, rank);

        long id = db.replace(TOP_TABLE, null, values);

        return id != -1;
    }

    public List<Article> getTopArticles(HackerNewsApi.TopType type) {
        List<Article> articles = new ArrayList<Article>();

        String projection[] = {ARTICLE_TABLE + "." + ARTICLE_ID_COLUMN, ARTICLE_URL_COLUMN, ARTICLE_TITLE_COLUMN,
                ARTICLE_DESCRIPTION_COLUMN, ARTICLE_LAST_COMMENT_COUNT_COLUMN,
                ARTICLE_LAST_POINT_COUNT_COLUMN, ARTICLE_USER_COLUMN};

        String selection = TOP_TABLE + "." + TOP_TYPE_COLUMN + " = ?";
        String selectionArgs[] = {String.valueOf(convertTypeToInt(type))};
        String order = TOP_TABLE + "." + TOP_RANK_COLUMN + " asc";

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(ARTICLE_TABLE + " left outer join " + TOP_TABLE +
                " on " + ARTICLE_TABLE + "." + ARTICLE_ID_COLUMN + " = " +
                TOP_TABLE + "." + TOP_ID_COLUMN);
        Logger.d(queryBuilder.toString());
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, order);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("item_id", cursor.getString(0));
            map.put("url", cursor.getString(1));
            map.put("title", cursor.getString(2));
            map.put("description", cursor.getString(3));
            map.put("comments", cursor.getString(4));
            map.put("score", cursor.getString(5));
            map.put("user", cursor.getString(6));
            map.put("time", "0");
            articles.add(new Article(map));

            cursor.moveToNext();
        }

        return articles;
    }

    private int convertTypeToInt(HackerNewsApi.TopType type) {
        switch (type) {
            case HOME:
                return 0;
            case BEST:
                return 1;
            case ASK:
                return 2;
            case NEWEST:
                return 3;
            default:
                return 0;
        }
    }


    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, SQLiteDatabase.CursorFactory cursorFactory) {
            super(context, DATABASE_NAME, cursorFactory, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(ARTICLE_DATABASE_CREATE);
            db.execSQL(TOP_DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Logger.w("Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all data");
        }
    }

    private static final String DATABASE_NAME = "procrastinator.db";
    private static final String ARTICLE_TABLE = "articles";
    private static final String TOP_TABLE = "tops";

    private static final String ARTICLE_DATABASE_CREATE = "create table " +
            ARTICLE_TABLE + " (" + KEY_ID +
            " integer primary key autoincrement, " +
            ARTICLE_FIRST_SEEN_COLUMN + " integer not null, " +
            ARTICLE_ID_COLUMN + " text not null, " +
            ARTICLE_URL_COLUMN + " text not null, " +
            ARTICLE_DESCRIPTION_COLUMN + " text not null, " +
            ARTICLE_USER_COLUMN + " text not null, " +
            ARTICLE_TITLE_COLUMN + " text not null, " +
            ARTICLE_LAST_COMMENT_COUNT_COLUMN + " integer default 0, " +
            ARTICLE_LAST_POINT_COUNT_COLUMN + " integer default 0, " +
            ARTICLE_COMMENT_COUNT_COLUMN + " integer default 0," +
            ARTICLE_SCORE_COLUMN + " integer default 0," +
            " unique(" + ARTICLE_ID_COLUMN + "));";

    private static final String TOP_DATABASE_CREATE = "create table " +
            TOP_TABLE + " (" + KEY_ID +
            " integer primary key autoincrement, " +
            TOP_ID_COLUMN + " integer not null, " +
            TOP_RANK_COLUMN + " integer not null," +
            TOP_TYPE_COLUMN + " integer not null," +
            " unique(" + TOP_RANK_COLUMN + ", " + TOP_TYPE_COLUMN + "));";


}
