package com.osesm.app.Procrastinator;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import com.osesm.app.Procrastinator.models.Article;

import java.util.List;

public class HackerNewsService extends IntentService {

    public HackerNewsService() {
        super("HackerNewsService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Logger.d("Starting up HackerNewsService");
        String json = HackerNewsApi.getJSON(HackerNewsApi.homePageURL());
        Logger.d(json);
        List<Article> articles = Article.fromJSON(json);
        Logger.d("Fetched " + articles.size() + " articles");

        DatabaseAdapter databaseAdapter = new DatabaseAdapter(getApplicationContext());
        databaseAdapter.open();

        for (Article article : articles) {
            Cursor c = databaseAdapter.findArticle(article.getItemId());

            if (c.getCount() > 0) {
                databaseAdapter.updateArticle(article);
            } else {
               databaseAdapter.createArticle(article);
            }
        }

        databaseAdapter.close();

        Logger.d("Shutting down HackerNewsService");
    }
}
