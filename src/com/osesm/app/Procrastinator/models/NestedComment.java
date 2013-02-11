package com.osesm.app.Procrastinator.models;

public class NestedComment {

    private final Comment comment;
    private final int indent;

    public NestedComment(Comment comment, int indent) {
        this.comment = comment;
        this.indent = indent;
    }

    public Comment getComment() {
        return comment;
    }

    public int getIndent() {
        return indent;
    }
}
