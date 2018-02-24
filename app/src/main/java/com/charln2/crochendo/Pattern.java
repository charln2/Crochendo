package com.charln2.crochendo;

import android.util.Log;

import com.charln2.crochendo.Instructions.Instruction;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

/**
 * Created by charl on 2/21/2018.
 */

public class Pattern {
    // kinda cludgey. Find a better way but good makeshift design choice for now
    public static final HashSet<String> stitches = new HashSet<String>(){{
        add("ch");
        add("dc");
        add("sl st");
    }};
    private Stitch head, tail, x;
    private ArrayList<Stitch> rows;
    private static boolean newRow = false;
    private boolean rsIsOdd = true;
    private Queue<Instruction> q = new LinkedList<>();
    HashMap<String, Stitch> hold = new HashMap<>();

    // default constructor for testing purposes
    public Pattern() {
        if (head == null) {
            head = tail = x = new Stitch("sl st");
            rows = new ArrayList<>();
            rows.add(x);
        }
    }
    public Pattern(FileInputStream rawInstructions) {
        this();
        parsePattern(rawInstructions);
    }

    public boolean isNewRow() {
        return newRow;
    }

    void parsePattern(FileInputStream fis) {
//            FileInputStream fis = cxt.openFileInput("patterntest");
//            Scanner sc = new Scanner(fin);
//            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
//            String blah = br.readLine();
        Scanner sc = new Scanner(new InputStreamReader(fis));
        String line = "";
        while (sc.hasNextLine()) {
            // ch 32,
            line = sc.nextLine().toLowerCase();
            Log.d(TAG, "parsePattern: " + line);
            if (line.startsWith("ch 32")) {
                break;
            }
        }

        // parse first direction
//        String line = sc.nextLine();
        parseLine(line);
        executeInstructions();
        Log.d(TAG, "parsePattern: " + this.toString());
    }

    void executeInstructions() {
        while (!q.isEmpty()) {
            q.poll().execute(this);
        }
    }

    void parseLine(String line) {
        Scanner sc = new Scanner(line);
        // commas note enclosed in parentheses or matching list of specific known delimiters
        sc.useDelimiter("(,(?![^()]*+\\)))|[:;.—]");
        //<stitch, times, in x, note>

        StringBuilder sb = new StringBuilder();
        ArrayList<String> parsedInstructions = new ArrayList<>();
        while (sc.hasNext()) {
            while (sb.length() <= 0 || !isValidInstruction(sb.toString())) {
                sb.append(sc.next());
            }
            // additional unpacking of special-case instruction line fragments
            // HOLD: *[some stitch]
                // map * stitch position, continue with rest of stitch
            if (sb.toString().startsWith("*")) {
                parsedInstructions.add(sc.next("\\*"));
                sb.deleteCharAt(0);
                continue;
            }

            // "(": 2 cases
            // SUB-INSTRUCTION/ COMMENT: stitch (...counts as...)
                // queue one, then the other.

            // SHELL: if startsWith (, group, skip, continue to next ,.
            // (stitches) in next...,
                // append remaining until ), then remaining instruction up to ,.
                // shell aggregates many stitches, uses remaining [...in next] instruction.
                // behaves like a regular stitch, but groups instructions


            // queue 1+ instructions
            for (String s : parsedInstructions) {
                Instruction inst = InstructionFactory.getInstruction(s);
                if (inst != null) {
                    q.add(inst);
                }
            }
//            parsedInstructions.clear();
        }
    }

    private boolean isValidInstruction(String s) {
        HashSet<Character> validStarts = new HashSet<Character>() {{
            add('*');
            add('(');
        }};

        return stitches.contains(s) || validStarts.contains(s.charAt(0));
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
