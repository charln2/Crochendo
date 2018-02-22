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
    ArrayList<String> rawInstructions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = (TextView) findViewById(R.id.result_text_view);
        rawInstructions = new ArrayList<>();
//        fetchWebsite(placeholderWebsite, rawInstructions);
        ExtractWebpageAsyncTask task = new ExtractWebpageAsyncTask(this);
        task.execute(placeholderWebsite);
        // callback to parse ArrayList once processed
    }

//    // testing attaching and positioning stitch drawables into views on the screen.
//    void experiment() {
//        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rel);
//        View dc1 = getLayoutInflater().inflate(R.layout.dc, null);
//        View dc2 = getLayoutInflater().inflate(R.layout.dc, null);
//        View dc3 = getLayoutInflater().inflate(R.layout.dc, null);
//        View dc4 = getLayoutInflater().inflate(R.layout.dc, null);
//        View dc5 = getLayoutInflater().inflate(R.layout.dc, null);
//
//        rl.addView(dc1);
//        rl.addView(dc2);
//        rl.addView(dc3);
//        rl.addView(dc4);
//        rl.addView(dc5);
//
////        rl.getChildAt(0).
//        rl.getChildAt(0).setRotation(45);
////        rl.getChildAt(1).setRotation(-35);
////        rl.getChildAt(3).setRotation(35);
////        rl.getChildAt(4).setRotation(70);
//
////        shell.setBackgroundColor(getResources().getColor(R.color.colorAccent));
////        shell.setPivotX();
////        shell.setPivotY(0f);
////        shell.setPivotY(0);
////        shell.setPivotX(0);
////        Toast.makeText(MainActivity.this, String.format("(%f, %f, %d)", shell.getPivotX(), shell.getPivotY(), shell.getWidth()), Toast.LENGTH_SHORT).show();
////        shell.setRotation(70);
//    }

//    void fetchWebsite(final String url, final ArrayList<String> list) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Document doc = Jsoup.connect(url).get();
//                    String title = doc.title();
//                    Elements elements = doc.select("div:has(p:contains(sl st)) > p");
//
//                    list.add(title);
//                    for (Element e : elements) {
//                        list.add(e.text());
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    list.add(e.getMessage());
////                    sb.append("Error: ").append(e.getMessage()).append('\n');
//                }
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        StringBuilder sb = new StringBuilder();
//                        for(String s : list) {
//                            sb.append(s).append("\n---\n");
//                        }
//                        tvResult.setText(sb.toString());
//                        for(String s : list) {
//                            Log.i(TAG, s);
//                        }
//                    }
//                });
//            }
//        }).start();
////        return list;
//    }

    @Override
    public void callback(ArrayList<String> list) {
        StringBuilder sb = new StringBuilder();
        for(String s : list) {
            sb.append(s).append("\n---\n");
        }
        for(String s : list) {
            Log.i(TAG, s);
        }
        tvResult.setText(sb.toString());

        Pattern p = new Pattern(list);
    }
}
