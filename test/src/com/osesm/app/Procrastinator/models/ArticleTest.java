package com.osesm.app.Procrastinator.models;

import junit.framework.TestCase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ArticleTest extends TestCase {

    public void testReturnsEmptyListIfCantParseJSON() {
        List<Article> articles = Article.fromJSON("");
        assertEquals(0, articles.size());
    }

    public void testConvertsJSONToListOfArticles() throws Exception {
        List<Article> articles = Article.fromJSON(generateResponse(2).toString());
        assertEquals(2, articles.size());
    }

    public void testConvertsJSONToArticle() throws Exception {
        List<Article> articles = Article.fromJSON(generateResponse(1).toString());
        Article article = articles.get(0);

        assertEquals("item 0", article.getTitle());
        assertEquals("http://example.com/0", article.getUrl());
        assertEquals(0, article.getScore());
        assertEquals("user0", article.getUser());
        assertEquals(0, article.getComments());
        assertEquals("0 hours ago", article.getTime());
        assertEquals("0", article.getItemId());
        assertEquals("0 points by user0 0 hours ago  | 0 comments", article.getDescription());
    }

    private JSONObject articleJSON(int id) throws JSONException {
        JSONObject object = new JSONObject();

        String user = "user" + id;
        String score = id + " points";
        String time = id + " hours ago";
        String comments = id + " comments";


        object.put("title", "item " + id);
        object.put("url", "http://example.com/" + id);
        object.put("score", score);
        object.put("user", user);
        object.put("comments", comments);
        object.put("time", time);
        object.put("item_id", id);
        object.put("description", score + " by " + user + " " + time + "  | " + comments);

        return object;
    }

    private JSONObject generateResponse(int itemCount) throws JSONException {
        JSONObject object = new JSONObject();

        JSONArray items = new JSONArray();
        for (int i = 0; i < itemCount; i++) {
            items.put(articleJSON(i));
        }

        object.put("items", items);

        return object;
    }

}
