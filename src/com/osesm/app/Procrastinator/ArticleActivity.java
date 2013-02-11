package com.osesm.app.Procrastinator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class ArticleActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_article);

        addListFragment();
        addCommentFragment();
    }

    private void addCommentFragment() {
        Fragment commentsFragment = new CommentsFragment();
        commentsFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.comment_container, commentsFragment).commit();
    }

    private void addListFragment() {
        Fragment articleListFragment = new ArticleListFragment();
        articleListFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.list_container, articleListFragment).commit();
    }
}
