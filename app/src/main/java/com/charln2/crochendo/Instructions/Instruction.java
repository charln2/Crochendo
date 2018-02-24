package com.charln2.crochendo.Instructions;

import com.charln2.crochendo.Pattern;

/**
 * Created by Ripley on 2/24/2018.
 */

public abstract class Instruction {
    String abbr = "MISSING ABBREV";
    String note = "";

    abstract void parse(String rawInstruction);
    public abstract void execute(Pattern p);
}
