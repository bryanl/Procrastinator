package com.osesm.app.Procrastinator;

import android.os.AsyncTask;
import com.osesm.app.Procrastinator.models.Comment;
import com.osesm.app.Procrastinator.models.NestedComment;

import java.util.List;

public class FetchCommentsTask extends AsyncTask<Void, Void, String> {

    private final CommentsFragment commentsFragment;
    private final String url;
    private final List<NestedComment> comments;

    public FetchCommentsTask(CommentsFragment commentsFragment, String url, List<NestedComment> comments) {
        this.commentsFragment = commentsFragment;
        this.url = url;
        this.comments = comments;
    }


    @Override
    protected String doInBackground(Void... params) {
        return HackerNewsApi.getJSON(url);
    }

    @Override
    protected void onPostExecute(String s) {
        Logger.d("Fetched comments: " + s);
        comments.clear();

        addComments(Comment.fromJSON(s), 0);

        commentsFragment.notifyDataSetChanged();
    }

    private void addComments(List<Comment> tree, int indent) {
        for (Comment comment : tree) {
            NestedComment nestedComment = new NestedComment(comment, indent);
            comments.add(nestedComment);
            if (!comment.getComments().isEmpty()) {
                addComments(comment.getComments(), indent + 1);
            }
        }

    }

}
