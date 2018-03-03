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
        Scanner sc = new Scanner(rawInstruction);
        // put (...) in note
        String parens = sc.findInLine("\\(.*\\)");
        if (parens != null) {
            note = parens;
        }
    }

    public void execute(Pattern p) {
        for(int i = 0; i < times; i++) {
            Stitch s = new Stitch(abbr);
            attach(p,s);
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
    void attach(Pattern p, Stitch s) {
        // attatch to pattern
        p.add(s);
        // attach to anchor
        if (anchorStitch != null) {
            p.moveX(ith, anchorStitch);
            p.addAnchor(s);
            if (note.contains("(beginning ch counts as")) {
                p.overwritePreviousRowEnd("ch");
            }
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

