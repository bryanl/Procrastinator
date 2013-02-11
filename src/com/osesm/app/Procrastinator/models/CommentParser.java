package com.osesm.app.Procrastinator.models;

import android.text.Html;

public class CommentParser {

    private final String string;

    public static String parse(String string) {
        return new CommentParser(string).parse();
    }

    public CommentParser(String string) {
        this.string = string;
    }

    public String parse() {
       return pageBreaks(quotes(html(string)));
    }

    private String pageBreaks(String string) {
        return string.replace("__BR__","\n\n");
    }

    private String quotes(String string) {
        return string.replace("\\\"", "\"");
    }

    private String html(String string) {
        return Html.fromHtml(string).toString();
    }


}
