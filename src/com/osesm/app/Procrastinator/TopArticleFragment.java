package com.osesm.app.Procrastinator;

import android.*;
import android.R;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;
import com.osesm.app.Procrastinator.models.Article;

import java.util.ArrayList;
import java.util.List;

import static com.osesm.app.Procrastinator.HackerNewsApi.*;

public class TopArticleFragment extends ListFragment {

    private SeparatedListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new SeparatedListAdapter(getActivity());

        DatabaseAdapter databaseAdapter = new DatabaseAdapter(getActivity());
        databaseAdapter.open();

        TopType[] types = {TopType.HOME, TopType.BEST, TopType.ASK, TopType.NEWEST};
        for (TopType type : types) {
            List<Article> articles = databaseAdapter.getTopArticles(type);
            ArticleArrayAdapter articleArrayAdapter = new ArticleArrayAdapter(getActivity(), articles);
            adapter.addSection(String.valueOf(type), articleArrayAdapter);
        }

        setListAdapter(adapter);

        databaseAdapter.close();
    }
}
