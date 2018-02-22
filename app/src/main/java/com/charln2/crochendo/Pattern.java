package com.charln2.crochendo;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by charl on 2/21/2018.
 */

public class Pattern {
    Stitch head, tail;
    ArrayList<Stitch> rows;

    private Pattern(){}

    public Pattern(ArrayList<String> rawInstructions) {
        parsePattern(rawInstructions);
    }

    void parsePattern(ArrayList<String> rawInstructions) {
        int i = 0;
        if (rawInstructions.size() == 0) {
            Log.d(TAG, "parsePattern: ! rawInstructions empty!");
            return;
        }
        while (!rawInstructions.get(i).toLowerCase().startsWith("direction")) {
            i++;
        }
        Log.d(TAG, "parsePattern: " + rawInstructions.get(i));
    }

    void append(String st) {

    }

    void append(String st, Stitch x) {

    }

    private class Stitch {
        String name;
        Stitch prev, next;
        ArrayList<Stitch> anchors;
        String note;
        private Stitch() {}
        public Stitch(String name) {
            this.name = name;
        }
    }
}
