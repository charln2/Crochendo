package com.charln2.crochendo.Instructions;

import com.charln2.crochendo.Pattern;

/**
 * Created by Ripley on 2/22/2018.
 */

public abstract class Instruction {
    protected String abbr = "MISSING ABBREV";
    protected int moveX = 0;
    protected int times = 1;
    protected String note = "";

    abstract void parse(String rawInstruction);
    public abstract void execute(Pattern p);
}
