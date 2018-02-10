package com.charln2.crochendo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String placeholderWebsite = "http://www.fiberfluxblog.com/2012/09/free-crochet-patternpavement-infinity.html";
    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = (TextView) findViewById(R.id.result_text_view);
        fetchWebsite(placeholderWebsite);
    }

    void fetchWebsite(final String url) {
        final StringBuilder sb = new StringBuilder();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(url).get();
                    String title = doc.title();
                    Elements paragraphs = doc.select("p");

                    sb.append(title).append('\n');
                } catch (IOException e) {
                    e.printStackTrace();
                    sb.append("Error: ").append(e.getMessage()).append('\n');
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(sb.toString());
                    }
                });
            }
        }).start();
    }
}
