package com.osesm.app.Procrastinator;

import android.content.Context;
import android.content.res.Resources;
import android.text.util.Linkify;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.osesm.app.Procrastinator.models.Comment;
import com.osesm.app.Procrastinator.models.NestedComment;

import java.util.List;

public class CommentsArrayAdapter extends ArrayAdapter<NestedComment> {

    private static final int NESTING_OFFSET = 48;


    public CommentsArrayAdapter(Context context, List<NestedComment> comments) {
        super(context, android.R.layout.simple_list_item_1, comments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.comment_list_item, parent, false);
        }

        NestedComment nestedComment = getItem(position);
        Comment comment = nestedComment.getComment();

        TextView commentText = (TextView) row.findViewById(R.id.comment_text);
        commentText.setText(comment.getCommentText());
        Linkify.addLinks(commentText, Linkify.ALL);

        TextView username = (TextView) row.findViewById(R.id.username);
        username.setText(comment.getUsername());

        TextView time = (TextView) row.findViewById(R.id.time);
        time.setText(comment.getTime());

        updateIndenting(row, nestedComment.getIndent());

        return row;
    }

    private void updateIndenting(View row, int indent) {
        int paddingLeft = (int) (convertDpToPixel(NESTING_OFFSET * indent, getContext()) + convertDpToPixel(16, getContext()));
        row.setPadding(paddingLeft, row.getPaddingTop(), row.getPaddingRight(), row.getPaddingBottom());

    }

    private float convertDpToPixel(float dp,Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * (metrics.densityDpi/160f);
    }


}
