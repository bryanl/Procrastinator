package com.osesm.app.Procrastinator.models;

import com.osesm.app.Procrastinator.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Comment {


    public static List<Comment> fromJSON(String json) {

        List<Comment> comments = new ArrayList<Comment>();

        try {
            JSONObject object = new JSONObject(json);
            JSONArray items = object.getJSONArray("items");
            comments.addAll(fromJSONArray(items));
        } catch (JSONException e) {
            Logger.e("Couldn't parse json because: " + e.getMessage() + "\n" + json, e);
        }

        return comments;
    }

    public static List<Comment> fromJSONArray(JSONArray array) {
        List<Comment> comments = new ArrayList<Comment>();
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject item = array.getJSONObject(i);
                String username = item.getString("username");
                String commentText = CommentParser.parse(item.getString("comment"));
                String id = item.getString("id");
                double grayedOutPercent = item.getDouble("grayedOutPercent");
                String replyId = item.getString("reply_id");
                String time = item.getString("time");

                Comment comment = new Comment(username, commentText, id, grayedOutPercent, replyId, time);
                comments.add(comment);

                comment.getComments().addAll(fromJSONArray(item.getJSONArray("children")));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return comments;

    }

    private final String username;
    private final String commentText;
    private final String id;
    private final double grayedOutPercent;
    private final String replyId;
    private final String time;
    private final List<Comment> comments = new ArrayList<Comment>();

    public Comment(String username, String commentText, String id, double grayedOutPercent, String replyId, String time) {
        this.username = username;
        this.commentText = commentText;
        this.id = id;
        this.grayedOutPercent = grayedOutPercent;
        this.replyId = replyId;
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public String getCommentText() {
        return commentText;
    }

    public String getId() {
        return id;
    }

    public double getGrayedOutPercent() {
        return grayedOutPercent;
    }

    public String getReplyId() {
        return replyId;
    }

    public String getTime() {
        return time;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
