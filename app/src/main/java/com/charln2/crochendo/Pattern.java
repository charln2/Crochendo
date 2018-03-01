package com.charln2.crochendo;

import android.util.Log;

import com.charln2.crochendo.Instructions.InstructionFactory;
import com.charln2.crochendo.Instructions.Instruction;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

import static android.content.ContentValues.TAG;



public class Pattern {

    private Stitch head, tail, x, dummy;
    ArrayList<Stitch> rowEnds;
    private boolean rsIsOdd = true;
    private Queue<Instruction> q = new LinkedList<>();
    HashMap<String, Stitch> hold = new HashMap<>();

    // default constructor for testing purposes
    public Pattern() {
        head = tail = new Stitch("sl st");
        dummy = new Stitch("dummy");
        dummy.next = head;
        head.prev = dummy;
        rowEnds = new ArrayList<>();
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
            if (rawInstruction.contains("(beginning ch counts as)")) {
                rowEnds.add(x);
            }
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
        tail.next = st;
        st.prev = tail;
        tail = tail.next;
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
        rowEnds.add(tail);
        x = tail;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        ArrayList<String> row = new ArrayList<>();

        Stack<Stitch> toPrint = new Stack<>();

        toPrint.push(dummy);
        for(int i = 0; i < rowEnds.size(); i++) {
            toPrint.push(rowEnds.get(i));
        }
        if (tail != toPrint.peek()) {
            toPrint.push(tail);
        }

        while (toPrint.size() > 1) {
            row.clear();
            Stitch curStitch = toPrint.pop();
            Stitch dest = toPrint.peek();
            while (curStitch != dest) {
                if (toPrint.size()%2==0) {
                    // add each odd row in "reverse" order during descent
                    row.add(curStitch.toString());
                } else {
                    row.add(0, curStitch.toString());
                }
                curStitch = curStitch.prev;
            }
            for (String s : row) {
                out.append(String.format("%-5s|", s));
            }
            out.setLength(out.length()-1);
            out.append('\n');
            //todo: add shell sub-instructions?
        }
        out.setLength(out.length()-1); // get rid of \n
        return out.toString();
    }

    // todo: breaks factory pattern. may change later.
    public void overwritePreviousRowEnd(String s) {
        rowEnds.remove(rowEnds.size()-1);
        rowEnds.add(x);
    }

    public void addAnchor(Stitch s) {
        x.addAnchor(s);
    }
}
