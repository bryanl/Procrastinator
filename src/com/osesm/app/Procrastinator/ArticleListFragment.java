package com.osesm.app.Procrastinator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.osesm.app.Procrastinator.models.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleListFragment extends ListFragment {

    private ArticleArrayAdapter arrayAdapter;

    public static enum PageTypes {
        HOME, ASK, NEW, BEST
    }

    public static final String PAGE_TYPE = "com.osesm.apps.Procrastinator.PAGE_TYPE";
    public static final String ARTICLE_BUNDLE_KEY = "com.osesm.apps.Procrastinator.ARTICLE";

    List<Article> articles = new ArrayList<Article>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrayAdapter = new ArticleArrayAdapter(getActivity(), articles);
        setListAdapter(arrayAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        new FetchHomePageTask(this, url(), articles).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.article_list, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Article article = arrayAdapter.getItem(position);

        Logger.d("Clicked: " + article);

        Intent intent = new Intent(getActivity(), ArticleActivity.class);
        intent.putExtra(PAGE_TYPE, getCurrentPageType());
        intent.putExtra(ARTICLE_BUNDLE_KEY, article);
        intent.putExtra(CommentsFragment.COMMENTS_URL, article.getUrl());

        startActivity(intent);
    }

    public void notifyDataSetChanged() {
        arrayAdapter.notifyDataSetChanged();
    }


    private String url() {
        switch (PageTypes.valueOf(getCurrentPageType())) {
            case ASK:
                return HackerNewsApi.askPageURL();
            case NEW:
                return HackerNewsApi.newestPageURL();
            case BEST:
                return HackerNewsApi.bestPageURL();
            default:
                return HackerNewsApi.homePageURL();
        }
    }

    private String getCurrentPageType() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            return bundle.getString(PAGE_TYPE);
        } else {
            return String.valueOf(PageTypes.HOME);

        }
    }
}
