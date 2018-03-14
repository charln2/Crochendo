package com.charln2.crochendo.Instructions;

import com.charln2.crochendo.ChainGroup;
import com.charln2.crochendo.Pattern;
import com.charln2.crochendo.Row;
import com.charln2.crochendo.Stitch;

import java.util.Scanner;

public abstract class Instruction {
    String abbr = "MISSING ABBREV";
    String note = "";
    int repeatCount = 1;
    String targetStitch = null;
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
        for (int i = 0; i < repeatCount; i++) {
            Stitch s = attach(p, abbr);
            if (targetStitch != null) {
                p.moveX(ith, targetStitch);
            }
            anchor(p, s);
        }
        if (note != null) {
            executeSecondaryInstructions(p);
        }
    }

    // move/anchor
    protected Stitch attach(Pattern p, String stitchName) {
        Stitch s = new Stitch(stitchName);
        // attach to pattern
        p.add(s);
        return s;
    }

    protected void anchor(Pattern p, Stitch st) {
        if (targetStitch != null) {
            st.setAnchor(p.getX());
        }
    }

    private void executeSecondaryInstructions(Pattern p) {
        if (note.contains("(beginning ch counts as")) {
            Stitch x = p.getX();
            Row row = p.getRow(-2);
            int numChains = 0;
            try {
                while (row.peekTail() != x) {
                    row.pop();
                    numChains++;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            p.getRow(-1).prepend(new ChainGroup(numChains));
        }
    }
}

