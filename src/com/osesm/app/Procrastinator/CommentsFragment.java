package com.osesm.app.Procrastinator;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.osesm.app.Procrastinator.models.Article;
import com.osesm.app.Procrastinator.models.Comment;
import com.osesm.app.Procrastinator.models.NestedComment;

import java.util.ArrayList;
import java.util.List;

public class CommentsFragment extends ListFragment {

    public static final String COMMENTS_URL = "com.osesm.apps.Procrastinator.COMMENTS_URL";
    private CommentsArrayAdapter arrayAdapter;

    List<NestedComment> comments = new ArrayList<NestedComment>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrayAdapter = new CommentsArrayAdapter(getActivity(), comments);
        setListAdapter(arrayAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new FetchCommentsTask(this, url(), comments).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.comment_list, container, false);
    }

    public void notifyDataSetChanged() {
        arrayAdapter.notifyDataSetChanged();
    }

    private String url() {
        Bundle bundle = getArguments();
        Article article = (Article) bundle.getSerializable(ArticleListFragment.ARTICLE_BUNDLE_KEY);
        return HackerNewsApi.commentURL(article.getItemId());
    }
}
