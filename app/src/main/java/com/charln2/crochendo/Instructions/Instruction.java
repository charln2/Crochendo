package com.charln2.crochendo.Instructions;

import com.charln2.crochendo.Pattern;
import com.charln2.crochendo.Row;
import com.charln2.crochendo.Stitch;
import com.charln2.crochendo.StitchGroup;

import java.util.Scanner;
import java.util.Stack;

public abstract class Instruction {
    String abbr = "MISSING ABBREV";
    String note = "";
    int times = 1;
    String anchorStitch;
    int ith = 1;

    abstract Instruction create();

    void parse(String rawInstruction) {
        rawInstruction = rawInstruction.replaceAll("(next|first)", "");
        Scanner sc = new Scanner(rawInstruction);
        // put (...) in note
        String parens = sc.findInLine("\\(.*\\)");
        if (parens != null) {
            note = parens;
        }
    }

    public void execute(Pattern p) {
        for (int i = 0; i < times; i++) {
            Stitch s = attach(p);
            anchor(p, s);
        }
        if (note != null) {
            executeSecondaryInstructions(p);
        }
    }

    // move/anchor
    protected Stitch attach(Pattern p) {
        Stitch s = new Stitch(abbr);
        // attatch to pattern
        p.add(s);
        return s;
    }

    private void anchor(Pattern p, Stitch st) {
        if (anchorStitch != null) {
            p.moveX(ith, anchorStitch);
            p.addAnchor(st);
        }
    }

    private void executeSecondaryInstructions(Pattern p) {
        if (note.contains("(beginning ch counts as")) {
            Stitch x = p.getX();
            Row row = p.getRow(-2);
            Stack<Stitch> toAdd = new Stack<>();
            try {
                while (row.peekLast() != x) {
                    toAdd.push(row.pop());
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            StitchGroup sg = new StitchGroup();
            while (!toAdd.isEmpty()) {
                sg.add(toAdd.pop());
            }

            p.getRow(-1).prepend(sg);
        }
    }
}

