package com.charln2.crochendo.Instructions;

import com.charln2.crochendo.Pattern;
import com.charln2.crochendo.Stitch;

/**
 * Created by Ripley on 2/22/2018.
 */

public abstract class StitchInstruction extends Instruction{
    int moveX = 0;
    String skipStitch;
    int times = 1;

   abstract void parse(String rawInstruction);

    public void execute(Pattern p) {
        // todo: "counts as" check.
        if (note.contains("beginning ch counts as")) {
            p.shiftNewestRowToNext("ch");
        }
    }

    void attatch(Pattern p) {
        attatch(p, true);
    }
    void attatch(Pattern p, boolean anchor) {
        for(int i = 0; i < times; i++) {
            Stitch s = new Stitch(abbr, note);
            p.append(s);
            if (anchor) {
                p.addAnchor(s);
            }
        }
    }
}
