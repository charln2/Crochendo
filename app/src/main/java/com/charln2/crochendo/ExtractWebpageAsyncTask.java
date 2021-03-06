package com.charln2.crochendo;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class ExtractWebpageAsyncTask extends AsyncTask<String, Void, ArrayList<String>> {
    private static final String TAG = ExtractWebpageAsyncTask.class.getSimpleName();

    public interface AsyncResponse {
        void callback(ArrayList<String> list);
    }

    private Context cxt;
    private AsyncResponse delegate = null;

    ExtractWebpageAsyncTask(AsyncResponse delegate, Context cxt) {
        this.cxt = cxt;
        this.delegate = delegate;
    }

    @Override
    protected ArrayList<String> doInBackground(String... urls) {
        ArrayList<String> list = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(urls[0]).get();
            String title = doc.title();
            Elements elements = doc.select("div:has(p:contains(sl st)) > p");

//            File f = getTempFile(cxt, MainActivity.placeholderWebsite);
            FileOutputStream fOut = cxt.openFileOutput("patterntest", Context.MODE_PRIVATE);
            list.add(title);
            for (Element e : elements) {
                list.add(e.text());
                fOut.write((e.text() + "\n").getBytes());
            }

            fOut.close();

        } catch (IOException e) {
            e.printStackTrace();
            list.add(e.getMessage());
//                    sb.add("Error: ").add(e.getMessage()).add('\n');
        }
        return list;
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        delegate.callback(strings);
    }

    private File getTempFile(Context cxt, String url) {
        File file = null;
        try {
//            String fileName = Uri.parse(url).getLastPathSegment();
            String fileName = "patterntest";
            File patternCache = new File(cxt.getCacheDir(), "patterns");
            patternCache.mkdir();
            file = File.createTempFile(fileName, null, patternCache);
            for (String s : this.cxt.fileList()) {
                Log.d(TAG, "getTempFile: " + s);
            }
        } catch (IOException e) {
            // Error while creating file
            e.printStackTrace();
        }
        return file;
    }
}
