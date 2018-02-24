package com.charln2.crochendo;

import android.util.Log;

import com.charln2.crochendo.Instructions.StitchInstruction;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

/**
 * Created by charl on 2/21/2018.
 */

public class Pattern {

    private Stitch head, tail, x;
    private ArrayList<Stitch> rows;
    private static boolean newRow = false;
    private boolean rsIsOdd = true;
    private Queue<StitchInstruction> q = new LinkedList<>();
    HashMap<String, Stitch> hold = new HashMap<>();

    // default constructor for testing purposes
    public Pattern() {
        if (head == null) {
            head = tail = x = new Stitch("sl st");
            rows = new ArrayList<>();
            rows.add(x);
        }
    }
    public Pattern(FileInputStream rawInstructions) throws InstantiationException {
        this();
        parsePattern(rawInstructions);
    }

    public boolean isNewRow() {
        return newRow;
    }

    void parsePattern(FileInputStream fis) throws InstantiationException {
//            FileInputStream fis = cxt.openFileInput("patterntest");
//            Scanner sc = new Scanner(fin);
//            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
//            String blah = br.readLine();
        Scanner sc = new Scanner(new InputStreamReader(fis));
        String line = "";
        while (sc.hasNextLine()) {
            // ch 32,
            line = sc.nextLine();
            Log.d(TAG, "parsePattern: " + line);
            parseLine(line);
        }

        executeInstructions();
        Log.d(TAG, "parsePattern: " + this.toString());
    }

    void executeInstructions() {
        while (!q.isEmpty()) {
            q.poll().execute(this);
        }
    }

    void parseLine(String line) throws InstantiationException {
        line = line.toLowerCase().trim();
        Scanner sc = new Scanner(line);
        // commas note enclosed in parentheses or matching list of specific known delimiters
        sc.useDelimiter("(,(?![^()]*+\\)))|[:;.—]");
        //<stitch, times, in x, note>

        while (sc.hasNext()) {
            String rawInstruction = sc.next();
            // additional unpacking of special-case instruction line fragments
            // HOLD: *[some stitch]
                // map * stitch position, continue with rest of stitch
            while (rawInstruction.startsWith("*")) {
                int i;
                for (i = 0; i < rawInstruction.length() && rawInstruction.charAt(i)!='*'; i++);
                q.add(InstructionFactory.getInstruction(rawInstruction.substring(0,i)));
                rawInstruction = rawInstruction.substring(i);
            }
            q.add(InstructionFactory.getInstruction(rawInstruction)); // "(" handled too!
            // "(" SHELL:
            // todo: create shell class
            // SHELL: if startsWith (, group, skip, continue to next ,.
            // (stitches) in next...,
                // append remaining until ), then remaining instruction up to ,.
                // shell aggregates many stitches, uses remaining [...in next] instruction.
                // behaves like a regular stitch, but groups instructions
        }
    }

    public void append(Stitch st) {
        if (head == null) {
            head = tail = x = new Stitch("sl st");
            rows.add(x);
        }
        tail.next = st;
        st.prev = tail;
        tail = tail.next;

        if (newRow) {
            rows.add(st);
            newRow = false;
        }
    }

    public void setNewRow(boolean newRow) {
        this.newRow = newRow;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        Stitch cur = head;
        while (cur != null) {
            out.append(String.format("%-5s|", cur));
            cur = cur.next;
        }
        out.setLength(out.length()-1);
        return out.toString();
    }

    // todo: breaks factory pattern. may change later.
    public void shiftNewestRowToNext(String s) {
        Stitch cur = x.next;
        while (!cur.equals(s)) {
            cur = cur.next;
        }
        rows.remove(rows.size()-1);
        rows.add(cur);
    }
}
