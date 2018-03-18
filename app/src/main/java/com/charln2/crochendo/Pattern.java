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

public class Pattern {
    public static final String TAG = Pattern.class.getName();
    private Stitch x; // anchoring stitch: marks next port stitch for new stitch to be anchored to
//    private int size;
    private Queue<Instruction> qInstructions = new LinkedList<>();
    private ArrayList<Instruction> processed = new ArrayList<>();
    HashMap<String, Integer> hold = new HashMap<>();
    ArrayList<Row> rows;

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
        String line;
        while (sc.hasNextLine()) {
            // ch 32,
            line = sc.nextLine();
            Log.d(TAG, "parsePattern: " + line);
            parseLine(line);
        }

        executeInstructions();
        Log.d(TAG, "parsePattern: " + this.toString());
    }

    void parsePattern(String rawPattern) throws InstantiationException {
        Scanner sc = new Scanner(rawPattern);
        String line;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
//            Log.d(TAG, "parsePattern: " + line);
            parseLine(line);
        }

        executeInstructions();
//        Log.d(TAG, "parsePattern: " + this.toString());
    }

    void parseLine(String line) throws InstantiationException {
        line = line.toLowerCase().trim();
        Scanner sc = new Scanner(line);
        // commas note enclosed in parentheses or matching list of specific known delimiters
        sc.useDelimiter("(,(?![^()]*+\\)))|[:;.]");
        //<stitch, times, in x, note>

        while (sc.hasNext()) {
            String rawInstruction = sc.next();
            // additional unpacking of special-case instruction line fragments

            // HOLD: *[some stitch]
            // map * stitch position, continue with rest of stitch
            if (rawInstruction.startsWith("*")) {
                int i;
                for (i = 0; i < rawInstruction.length() && rawInstruction.charAt(i) == '*'; i++) ;
                qInstructions.add(InstructionFactory.getInstruction(rawInstruction.substring(0, i)));
                rawInstruction = rawInstruction.substring(i);
            }

            // add instruction to queue
            qInstructions.add(InstructionFactory.getInstruction(rawInstruction)); // "(" handled too!
        }
    }

    void executeInstructions() {
        while (!qInstructions.isEmpty()) {
            Instruction i = qInstructions.poll();
            i.execute(this);
            processed.add(i);
        }
    }

    public Stitch getTail() {
        return getRow(-1).tail;
    }
    public void add(Stitch st) {
        rows.get(rows.size() - 1).add(st);
//        size++;
    }

    public void moveX(int ith, String anchorTarget) {
        anchorTarget = anchorTarget.replaceAll("-1", "");
        x = x.nextPort(ith, anchorTarget);
    }

    public void startNewRow() {
        if (!rows.isEmpty()) {
            x = rows.get(rows.size() - 1).tail;
        }

        boolean printDirection = rows.size() % 2 == 0; // even: ltr, odd: rtl
        rows.add(new Row(printDirection));
    }

    @Override
    public String toString() {
        // todo: handle StitchGroups and ChainGroups
        StringBuilder out = new StringBuilder();

        //todo: call row.toString() and row.toStringExpanded
        // todo: implement (padding()) for Stitch object, print 1st row w/ padding
        //todo: print rest of rows from 1..n
        for (int i = rows.size() - 1; i >= 0; i--) {
            Row r = rows.get(i);
            for (int p = 0; p < r.getPadding(); p++) {
                out.append("     |");
            }
            out.append(r.toString()).append('\n');
            // todo: give every row "padding" in toString()? What would that look like?
        }
        out.setLength(out.length() - 1);
        return out.toString();
    }

    public Stitch getX() {
        return x;
    }

    public Row getRow(int i) {
        if (i < 0) {
            i = rows.size() + i;
        }
        if (i < 0 || i >= rows.size()) {
            throw new IndexOutOfBoundsException(String.format("Row %d not found", i));
        }
        return rows.get(i);
    }

    // ? breaks factory method a bit. refactor?
    public void hold(String key) {
        hold.put(key, processed.size() + 1); // instruction after hold
    }

    public ArrayList<Instruction> copyInstructionsFrom(String key) {
        ArrayList<Instruction> instructions = new ArrayList<>();
        for (int i = hold.get(key); i < processed.size(); i++) {
            instructions.add(processed.get(i));
        }
        return instructions;
    }

    String printRow(int n) {
        StringBuilder sb = new StringBuilder();
        Row r1 = getRow(n);
        Row r2 = getRow(n-1);
        if (n >= 1 && !r1.isLtr()) {
            int padding = r1.getPadding();
            if (padding >= 0) {
                for(int i = 0; i < padding; i++) {
                    sb.append("     |");
                }
            }
            sb.append(r1.toString()).append('\n');
            if (padding < 0) {
                for(int i = 0; i < Math.abs(padding); i++) {
                    sb.append("     |");
                }
            }
            sb.append(r2.toStringExpanded());
        } else {
            sb.append(r1).append('\n').append(r2.toStringExpanded());
        }
        return sb.toString();
    }
}
