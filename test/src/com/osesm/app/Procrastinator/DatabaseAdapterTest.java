package com.osesm.app.Procrastinator;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import com.osesm.app.Procrastinator.models.Article;

import java.util.HashMap;
import java.util.Map;

public class DatabaseAdapterTest extends AndroidTestCase {

    private static final String TEST_FILE_PREFIX = "test_";

    private DatabaseAdapter databaseAdapter;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), TEST_FILE_PREFIX);

        databaseAdapter = new DatabaseAdapter(context);
        databaseAdapter.open();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        databaseAdapter.close();
        databaseAdapter = null;
    }

    public void testPreconditions() {
        assertNotNull(databaseAdapter);
    }

    public void testCreateArticle() {
        Article article = getArticle(0);
        assertTrue(databaseAdapter.createArticle(article));
    }

    public void testArticleItemIdShouldBeUnique() {
        Article article1 = getArticle(0);
        Article article2 = getArticle(0);
        databaseAdapter.createArticle(article1);
        assertFalse(databaseAdapter.createArticle(article2));
    }

    public void testUpdateRank() {
        Article article = getArticle(0);
        assertTrue(databaseAdapter.updateRank(article, DatabaseAdapter.TOP_TYPE.BEST, 1));
    }

    public void testUpdateReplaceRank() {
        Article article1 = getArticle(0);
        databaseAdapter.updateRank(article1, DatabaseAdapter.TOP_TYPE.BEST, 1);
        Article article2 = getArticle(1);
        assertTrue(databaseAdapter.updateRank(article2, DatabaseAdapter.TOP_TYPE.BEST, 1));
    }

    private Article getArticle(int id) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("item_id", String.valueOf(id));
        map.put("url", "http://example.com/" + id);
        map.put("title", "article " + id);
        map.put("description", "description " + id);
        map.put("user", "user" + id);
        map.put("time", "4 hours ago");
        map.put("score", "5 points");
        map.put("comments", "5 comments");

        return new Article(map);
    }


}
