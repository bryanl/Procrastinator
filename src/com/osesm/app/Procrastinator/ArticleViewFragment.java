package com.osesm.app.Procrastinator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.webkit.WebView;
import com.osesm.app.Procrastinator.models.Article;

public class ArticleViewFragment extends Fragment {

    private WebView webview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        webview = new WebView(getActivity());
        return webview;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        final Bundle bundle = getArguments();
        if (bundle.containsKey(ArticleListFragment.ARTICLE_BUNDLE_KEY)) {
            Article article = (Article) bundle.getSerializable(ArticleListFragment.ARTICLE_BUNDLE_KEY);
            webview.loadUrl(article.getUrl());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {


    }
}
