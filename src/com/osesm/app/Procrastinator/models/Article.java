package com.osesm.app.Procrastinator.models;

import com.osesm.app.Procrastinator.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Article implements Serializable{

    public static List<Article> fromJSON(String json) {

        List<Article> articles = new ArrayList<Article>();

        try {
            JSONObject page = new JSONObject(json);
            JSONArray items = page.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                String item_id = item.optString("item_id");
                String url = item.getString("url");
                String title = item.getString("title");
                String description = item.getString("description");
                String comments = item.getString("comments");
                String score = item.getString("score");

                if (item_id.matches("^\\d")) {
                    articles.add(new Article(item_id, url, title, description, comments, score));
                }
            }

        } catch (JSONException e) {
            Logger.e("Couldn't parse json because: " + e.getMessage() + "\n" + json, e);
        }

        return articles;
    }

    private final String item_id;
    private final String url;
    private final String title;
    private final String description;
    private final int score;
    private final int comments;



    private Article(String item_id, String url, String title, String description, String score, String comments) {
        this.item_id = item_id;
        this.url = url;
        this.title = title;
        this.description = description;

        this.score = new Scanner(score).useDelimiter("[^0-9]+").nextInt();
        this.comments = new Scanner(comments).useDelimiter("[^0-9]+").nextInt();
    }

    public String getItemId() {
        return item_id;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getScore() {
        return score;
    }

    public int getComments() {
        return comments;
    }

    @Override
    public String toString() {
        return "Article{" +
                "item_id='" + item_id + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", score='" + score + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
