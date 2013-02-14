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

        HackerNewsApi.TopType endPoints[] = {HackerNewsApi.TopType.HOME,
                HackerNewsApi.TopType.BEST,
                HackerNewsApi.TopType.NEWEST,
                HackerNewsApi.TopType.ASK};

        for (HackerNewsApi.TopType endPoint : endPoints) {
            updateArticles(endPoint);
        }

        Logger.d("Shutting down HackerNewsService");
    }

    private void updateArticles(HackerNewsApi.TopType type) {
        String endPoint = HackerNewsApi.getTypeURL(type);
        String json = HackerNewsApi.getJSON(endPoint);

        List<Article> articles = Article.fromJSON(json);
        Logger.d("Fetched " + articles.size() + " articles from: " + endPoint);

        DatabaseAdapter databaseAdapter = new DatabaseAdapter(getApplicationContext());
        databaseAdapter.open();

        for (int i = 0; i < articles.size(); i++) {
            Article article = articles.get(i);

            if (i < 5) {
                databaseAdapter.updateRank(article, type, i);
            }

            Cursor c = databaseAdapter.findArticle(article.getItemId());

            if (c.getCount() > 0) {
                databaseAdapter.updateArticle(article);
            } else {
                databaseAdapter.createArticle(article);
            }
        }

        databaseAdapter.close();
    }
}
