package com.charln2.crochendo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

/**
 * Created by charl on 2/21/2018.
 */

public class Pattern {
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

    Stitch head, tail;
    ArrayList<Stitch> rows;

    public Pattern(FileInputStream rawInstructions) {
        parsePattern(rawInstructions);
    }

    void parsePattern(FileInputStream fis) {
        try {
//            FileInputStream fis = cxt.openFileInput("patterntest");
//            Scanner sc = new Scanner(fin);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String blah = br.readLine();
            Log.d(TAG, "parsePatternasdf: " + blah);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        int i = 0;
//        while (!rawInstructions.get(i).toLowerCase().startsWith("direction")) {
//            i++;
//        }
//        Log.d(TAG, "parsePattern: " + rawInstructions.get(i));
//
//        i++;
//
//        String line = rawInstructions.get(i);

    }

    void append(String st) {

    }

    void append(String st, Stitch x) {

    }
}
