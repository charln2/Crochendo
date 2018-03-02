package com.charln2.crochendo.Instructions;

import com.charln2.crochendo.Pattern;
import com.charln2.crochendo.Stitch;

import java.util.Scanner;

/**
 * Created by Ripley on 2/22/2018.
 */

public abstract class Instruction {
    String abbr = "MISSING ABBREV";
    String note = "";
    int times = 1;
    String anchorStitch;
    int ith = 1;

    void parse(String rawInstruction) {
        Scanner sc = new Scanner(rawInstruction);
        // put (...) in note
        String parens = sc.findInLine("\\(*\\)");
        if (parens != null) {
            note = parens;
        }
    }

    public void execute(Pattern p) {
        for(int i = 0; i < times; i++) {
            Stitch s = Stitch.getStitch(abbr);
            p.add(s);

            if (anchorStitch != null) {
                p.moveX(ith, anchorStitch);
                p.addAnchor(s);
                if (note.contains("beginning ch counts as")) {
                    p.overwritePreviousRowEnd("ch");
                }
            }
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
        }
    }
}
