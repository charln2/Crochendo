package com.charln2.crochendo.Instructions;

import com.charln2.crochendo.Pattern;

/**
 * Created by Ripley on 2/22/2018.
 */

public abstract class StitchInstruction {
    String abbr = "MISSING ABBREV";
    int moveX = 0;
    int times = 1;
    String note = "";

   abstract void parse(String rawInstruction);

    public void execute(Pattern p) {
        // todo: "counts as" check.
        if (note.contains("beginning ch counts as")) {
            p.shiftNewestRowToNext("ch");
        }
    }
}
