package com.osesm.app.Procrastinator.models;

import com.osesm.app.Procrastinator.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.*;

public class Article implements Serializable {

    public static List<Article> fromJSON(String json) {

        List<Article> articles = new ArrayList<Article>();

        try {
            JSONObject page = new JSONObject(json);
            JSONArray items = page.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {

                Map<String, String> map = new HashMap<String, String>();

                JSONObject item = items.getJSONObject(i);

                String[] keys = {"item_id", "url", "title", "description", "comments", "score", "user", "time"};
                for (String key : keys) {
                    map.put(key, item.optString(key));
                }

                if (map.get("item_id").matches("^\\d")) {
                    articles.add(new Article(map));
                }
            }

        } catch (JSONException e) {
            Logger.e("Couldn't parse article JSON");
        }

        return articles;
    }

    private final String item_id;
    private final String url;
    private final String title;
    private final String description;
    private final String user;
    private final String time;
    private final int score;
    private final int comments;


    private Article(Map<String, String> map) {
        this.item_id = map.get("item_id");
        this.url = map.get("url");
        this.title = map.get("title");
        this.description = map.get("description");
        this.user = map.get("user");
        this.time = map.get("time");
        this.score = extractLeadingDigits(map.get("score"));
        this.comments = extractLeadingDigits(map.get("comments"));
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

    public String getUser() {
        return user;
    }

    public String getTime() {
        return time;
    }

    private int extractLeadingDigits(String string) {
      return new Scanner(string).useDelimiter("[^0-9]+").nextInt();
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
