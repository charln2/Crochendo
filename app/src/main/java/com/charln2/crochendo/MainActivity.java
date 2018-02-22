package com.charln2.crochendo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ExtractWebpageAsyncTask.AsyncResponse{
    private static final String placeholderWebsite = "https://hooked-on-crafting.com/2013/09/10/bellflower-infinity-scarf-free-pattern/";
    private static final String TAG = "MainActivity";
    TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = (TextView) findViewById(R.id.result_text_view);
        ExtractWebpageAsyncTask task = new ExtractWebpageAsyncTask(this);
        task.execute(placeholderWebsite);
    }

    @Override
    public void callback(ArrayList<String> list) {
        StringBuilder sb = new StringBuilder();
        for(String s : list) {
            Log.i(TAG, s);
            sb.append(s).append("\n---\n");
        }
        tvResult.setText(sb.toString());

        Pattern p = new Pattern(list);
    }
}
