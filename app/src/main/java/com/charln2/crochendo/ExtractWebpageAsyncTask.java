package com.charln2.crochendo;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by charl on 2/21/2018.
 */

public class ExtractWebpageAsyncTask extends AsyncTask<String, Void, ArrayList<String>> {
    public interface AsyncResponse {
        void callback(ArrayList<String> list);
    }

    public AsyncResponse delegate = null;
    public ExtractWebpageAsyncTask(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected ArrayList<String> doInBackground(String... urls) {
        ArrayList<String> list = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(urls[0]).get();
            String title = doc.title();
            Elements elements = doc.select("div:has(p:contains(sl st)) > p");

            list.add(title);
            for (Element e : elements) {
                list.add(e.text());
            }

        } catch (IOException e) {
            e.printStackTrace();
            list.add(e.getMessage());
//                    sb.append("Error: ").append(e.getMessage()).append('\n');
        }


        return list;    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        delegate.callback(strings);
    }
}
