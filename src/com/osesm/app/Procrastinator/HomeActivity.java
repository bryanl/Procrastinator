package com.osesm.app.Procrastinator;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends FragmentActivity {
    private TabsAdapter tabsAdapter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        ViewPager viewPager = new ViewPager(this);
        viewPager.setId(R.id.view_pager);
        setContentView(viewPager);

        final ActionBar bar = getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        tabsAdapter = new TabsAdapter(this, viewPager);

        addTab("Home", ArticleListFragment.PageTypes.HOME);
        addTab("Best", ArticleListFragment.PageTypes.BEST);
        addTab("Ask HN", ArticleListFragment.PageTypes.ASK);
        addTab("New", ArticleListFragment.PageTypes.NEW);

        if (savedInstanceState != null) {
            bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }

        Intent intent = new Intent(this, HackerNewsService.class);
        startService(intent);
    }

    private void addTab(String title, ArticleListFragment.PageTypes pageType) {
        final ActionBar bar = getActionBar();
        Bundle bundle = new Bundle();
        bundle.putString(ArticleListFragment.PAGE_TYPE, String.valueOf(pageType));
        tabsAdapter.addTab(bar.newTab().setText(title), ArticleListFragment.class, bundle);
    }

    public static class TabsAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener, ActionBar.TabListener {

        private final List<TabInfo> tabs = new ArrayList<TabInfo>();
        private final Context context;
        private final ActionBar actionBar;
        private final ViewPager viewPager;

        static final class TabInfo {
            private final Class<?> klass;
            private final Bundle args;

            TabInfo(Class<?> _class, Bundle _args) {
                klass = _class;
                args = _args;
            }
        }

        public TabsAdapter(FragmentActivity activity, ViewPager pager) {
            super(activity.getSupportFragmentManager());
            context = activity;
            actionBar = activity.getActionBar();
            viewPager = pager;
            viewPager.setAdapter(this);
            viewPager.setOnPageChangeListener(this);
        }

        public void addTab(ActionBar.Tab tab, Class<?> klass, Bundle args) {
            TabInfo info = new TabInfo(klass, args);
            tab.setTag(info);
            tab.setTabListener(this);
            tabs.add(info);
            actionBar.addTab(tab);
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            TabInfo info = tabs.get(position);
            return Fragment.instantiate(context, info.klass.getName(), info.args);
        }

        @Override
        public void onPageScrolled(int i, float v, int i2) {
        }

        @Override
        public void onPageSelected(int position) {
            actionBar.setSelectedNavigationItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }

        @Override
        public int getCount() {
            return tabs.size();
        }


        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            Object tag = tab.getTag();
            for (int i = 0; i < tabs.size(); i++) {
                if (tabs.get(i) == tag) {
                    viewPager.setCurrentItem(i);
                }
            }
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        }

    }
}
