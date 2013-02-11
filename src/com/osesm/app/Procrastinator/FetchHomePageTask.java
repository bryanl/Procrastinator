package com.osesm.app.Procrastinator;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import com.osesm.app.Procrastinator.models.Article;

import java.util.List;

public class FetchHomePageTask extends AsyncTask<Void, Void, String> {

    private final List<Article> articles;
    private final String url;
    private final ArticleListFragment articleListFragment;

    public FetchHomePageTask(ArticleListFragment articleListFragment, String url, List<Article> titles) {
        this.articleListFragment = articleListFragment;
        this.url = url;
        this.articles = titles;
    }

    @Override
    protected void onPreExecute() {
        getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    protected String doInBackground(Void... params) {
        return HackerNewsApi.getJSON(url);
    }

    @Override
    protected void onPostExecute(String s) {
        articles.clear();
        articles.addAll(Article.fromJSON(s));
        getActivity().setProgressBarIndeterminateVisibility(false);
        articleListFragment.notifyDataSetChanged();
    }

    private FragmentActivity getActivity() {
        return articleListFragment.getActivity();
    }
}