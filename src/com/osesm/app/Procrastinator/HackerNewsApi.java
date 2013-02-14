package com.osesm.app.Procrastinator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HackerNewsApi {

    private static final String APP_ID = "Procrastinator";

    public static enum TopType {
        HOME, BEST, NEWEST, ASK
    }

    public static String getJSON(String url) {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);

        Logger.d("Fetching: " + url);

        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            }

        } catch (ClientProtocolException e) {
            Logger.e("Unable to get URL: " + homePageURL(), e);
        } catch (IOException e) {
            Logger.e("Unable to get URL: " + homePageURL(), e);
        }

        return builder.toString();
    }

    public static String getTypeURL(TopType type) {
        switch(type) {
            case BEST:
                return bestPageURL();
            case NEWEST:
                return newestPageURL();
            case ASK:
                return askPageURL();
            default:
                return homePageURL();
        }

    }

    public static String homePageURL() {
        return "http://hndroidapi.appspot.com/news/format/json/page/?appid=" + APP_ID;
    }
    public static String bestPageURL() {
        return "http://hndroidapi.appspot.com/best/format/json/page/?appid=" + APP_ID;
    }

    public static String newestPageURL() {
        return "http://hndroidapi.appspot.com/newest/format/json/page/?appid=" + APP_ID;
    }

    public static String askPageURL() {
        return "http://hndroidapi.appspot.com/ask/format/json/page/?appid=" + APP_ID;
    }

    public static String commentURL(String id) {
        return "http://hndroidapi.appspot.com/nestedcomments/format/json/id/" + id + "?appid=" + APP_ID;
    }


}
