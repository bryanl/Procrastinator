package com.osesm.app.Procrastinator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.osesm.app.Procrastinator.models.Article;

import java.util.List;

public class ArticleArrayAdapter extends ArrayAdapter<Article> {
    public ArticleArrayAdapter(Context context, List<Article> articles) {
        super(context, android.R.layout.simple_list_item_1, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater= (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.article_list_item, parent, false);
        }

        Article article = getItem(position);

        TextView title = (TextView) row.findViewById(R.id.title);
        title.setText(article.getTitle());

        TextView description = (TextView) row.findViewById(R.id.description);
        description.setText(article.getDescription());

        return row;
    }
}
