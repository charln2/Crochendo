package com.charln2.crochendo;

import android.util.Log;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

/**
 * Created by charl on 2/21/2018.
 */

public class Pattern {
    HashSet<String> stitches = new HashSet<String>(){{
        add("ch");
    }};

    Stitch head, tail, x;
    ArrayList<Stitch> rows;
    private Queue<Instruction> q = new LinkedList<>();
    // make a queue

    // default constructor for testing purposes
    public Pattern() {
        if (head == null) {
            head = tail = x = new Stitch("sl st");
        }
    }
    public Pattern(FileInputStream rawInstructions) {
        this();
        parsePattern(rawInstructions);
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
        print();
    }

    void executeInstructions() {
        while (!q.isEmpty()) {
            q.poll().execute(this, x);
        }
    }

    void parseLine(String line) {
        Scanner sc = new Scanner(line);
        sc.useDelimiter("[,:;.]");
        //<stitch, times, in x, note>

        while (sc.hasNext()) {
            Instruction inst = InstructionFactory.getInstruction(sc.next());
            if (inst != null) {
                q.add(inst);
            }

//            switch (abbr) {
//                case "ch":
//                    InstructionFactory.getInstruction()
//                default:
//                    Log.e(TAG, "parseLine: Stitch not recognized", new InstantiationException() );
//            }
//            // make Instruction, add to queue
//            if (sc.hasNextInt()) {
//
//            } else if (sc.hasNext("in")) {
//
//            } else {
//
//            }
        }
    }
    void append(Stitch st) {
        if (head == null) {
            head = tail = x = new Stitch("sl st");
        }
        tail.next = st;
        st.prev = tail;
        tail = tail.next;
    }

    String print() {
        StringBuilder out = new StringBuilder();
        Stitch cur = head;
        while (cur != null) {
            out.append(cur.name).append(" | ");
            cur = cur.next;
        }
//        Log.d(TAG, "print pattern: \n" + out.toString());
        return out.toString();
    }
}
