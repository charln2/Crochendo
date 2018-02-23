package com.charln2.crochendo;

import android.util.Log;

import com.charln2.crochendo.Instructions.Instruction;

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
        Log.d(TAG, "parsePattern: " + this.toString());
    }

    void executeInstructions() {
        while (!q.isEmpty()) {
            q.poll().execute(this);
        }
    }

    void parseLine(String line) {
        Scanner sc = new Scanner(line);
        sc.useDelimiter("[,:;.—]");
        //<stitch, times, in x, note>

        while (sc.hasNext()) {
            Instruction inst = InstructionFactory.getInstruction(sc.next());
            if (inst != null) {
                q.add(inst);
            }
        }
    }

    public void append(Stitch st) {
        if (head == null) {
            head = tail = x = new Stitch("sl st");
        }
        tail.next = st;
        st.prev = tail;
        tail = tail.next;
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
}
