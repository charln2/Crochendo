package com.charln2.crochendo.Instructions;

import com.charln2.crochendo.ChainGroup;
import com.charln2.crochendo.Pattern;
import com.charln2.crochendo.Row;
import com.charln2.crochendo.Stitch;

import java.util.Scanner;

/**
 * Created by Ripley on 2/22/2018.
 */

public abstract class Instruction {
    protected String abbr = "MISSING ABBREV";
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
            anchor(p,s);
        }
        if (note != null) {
            executeSecondaryInstructions(p);
        }
    }
    // () check?
    // @Override
    // instantiate
        // appendShell

    // move/anchor
    protected Stitch attach(Pattern p) {
        Stitch s = new Stitch(abbr);
        // attatch to pattern
        p.add(s);
        return s;
    }
    protected void anchor(Pattern p, Stitch s) {
        if (anchorStitch != null) {
            p.moveX(ith, anchorStitch);
            p.addAnchor(s);
        }
    }

    private void executeSecondaryInstructions(Pattern p) {
        if (note.contains("(beginning ch counts as")) {
            Stitch x = p.getX();
            Row row = p.getRow(-2);
            int chCount = 0;
            try {
                while (row.peekLast() != x) {
                    row.pop();
                    chCount++;
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            ChainGroup cg = new ChainGroup(chCount);
            p.getRow(-1).prepend(cg);
        }
    }
}

