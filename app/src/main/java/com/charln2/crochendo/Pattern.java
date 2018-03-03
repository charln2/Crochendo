package com.charln2.crochendo;

import android.util.Log;

import com.charln2.crochendo.Instructions.Instruction;
import com.charln2.crochendo.Instructions.InstructionFactory;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import static android.content.ContentValues.TAG;



public class Pattern {

    private Stitch head, tail, x;
    ArrayList<Row> rows;
    private Queue<Instruction> qInstructions = new LinkedList<>();
    HashMap<String, Stitch> hold = new HashMap<>();
    private boolean rsIsOdd = true;

    // default constructor for testing purposes
    public Pattern() {
        rows = new ArrayList<>();
        try {
            qInstructions.add(InstructionFactory.getInstruction("Row 0:"));
            qInstructions.add(InstructionFactory.getInstruction("sl st"));
        } catch (InstantiationException e) {
            e.printStackTrace();
            Log.e(TAG, "Pattern: Unable to construct Pattern object");
        }

        executeInstructions();
    }
    public Pattern(FileInputStream rawInstructions) throws InstantiationException {
        this();
        parsePattern(rawInstructions);
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
        while (!qInstructions.isEmpty()) {
            qInstructions.poll().execute(this);
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

            qInstructions.add(InstructionFactory.getInstruction(rawInstruction)); // "(" handled too!
            // "(" SHELL:
            // todo: create shell class
            // SHELL: if startsWith (, group, skip, continue to next ,.
            // (stitches) in next...,
                // add remaining until ), then remaining instruction up to ,.
                // shell aggregates many stitches, uses remaining [...in next] instruction.
                // behaves like a regular stitch, but groups instructions
        }
    }

    public void add(Stitch st) {
//        if (head == null) {
//            head = tail = rows.get(0).head;
//            return;
//        }
        rows.get(rows.size()-1).add(st);
        tail = st;
    }

    public void moveX(int num) {
        moveX(num, "");
    }
    public void moveX(String anchorStitch) {
        moveX(1, anchorStitch);
    }

    public void moveX(int ith, String anchorTarget) {
        try {
            while (ith > 0) {
                do {
                    x = x.prev;
                } while (x.name.equals("sk"));
                if (anchorTarget == null || x.name.equalsIgnoreCase(anchorTarget)) {
                    ith--;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.e(TAG, "moveX: anchorTarget %s not found");
        }
    }

    public void startNewRow() {
        if (!rows.isEmpty()) {
            x = rows.get(rows.size()-1).tail;
        }

        boolean printDirection = rows.size()%2==0; // even: ltr, odd: rtl
        rows.add(new Row(printDirection));
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();

        for(int i = rows.size()-1; i >= 0; i--) {
            out.append(rows.get(i).toString());
            // append padding, if row index odd
            if (i > 0 && i%2==1) {
                Stitch padder = rows.get(i-1).head;
                int spaces = 0;
                while (padder != null && padder.anchors == null) {
                    spaces++;
                    padder=padder.next;
                }
                for(int sp = 0; sp < spaces; sp++) {
                    out.insert(0,("     |"));
                }
            }
            // get earliest anchored stitch of prev row
            // count spaces down to row start, exclusive
            // prepend that many "blank" padding spaces onto
            out.append('\n');
        }
        out.setLength(out.length()-1);
        return out.toString();
    }

    // todo: breaks factory pattern. may change later.
    public void overwritePreviousRowEnd(String s) {
        // todo: new instruction class: migrate Stitches
//        "4th ch from hook"
//        chain last 4
//        add "Row 1"
//        from Row 1, skip to 4th ch
//        add/anchor dc to row 1.
//        (beginning ch counts as dc)
//        compress/promote last 3 into shell (ch group?)
//        "anchor" chain becomes new end attached to Row 1
//        ch group inserted between Row1 stitch and most recent stitch.

//        rows.remove(rows.size()-1);
//        rows.add(x);
    }

    public void addAnchor(Stitch s) {
        x.addAnchor(s);
    }
}
