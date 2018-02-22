package com.charln2.crochendo;

import android.util.Log;

import java.io.FileInputStream;
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
        head = tail = new Stitch("sl");
        parsePattern(rawInstructions);
    }

    void parsePattern(FileInputStream fis) {
//            FileInputStream fis = cxt.openFileInput("patterntest");
//            Scanner sc = new Scanner(fin);
//            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
//            String blah = br.readLine();
        Scanner sc = new Scanner(new InputStreamReader(fis));
        for (String line = sc.nextLine(); sc.hasNextLine() && sc.nextLine().toLowerCase().startsWith("directions"); ) {

        }
        while (sc.hasNextLine()) {
            String line = sc.nextLine().toLowerCase();
            Log.d(TAG, "parsePattern: " + line);
            if (line.startsWith("directions")) {
                break;
            }
        }

        // parse first direction
        String line = sc.nextLine();
        buildPattern(line);

    }

    void buildPattern(String line) {
        Scanner sc = new Scanner(line);
        StringBuilder stitch = new StringBuilder();
        int count;
        while (sc.hasNext()) {
            if (sc.hasNextInt()) {

            } else if (sc.hasNext("in")) {

            } else {
                
            }
            stitch.append(sc.next());
        }
    }
    void append(String st) {

    }

    void append(String st, Stitch x) {

    }
}
