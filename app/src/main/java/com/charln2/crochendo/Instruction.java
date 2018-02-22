package com.charln2.crochendo;

/**
 * Created by Ripley on 2/22/2018.
 */

public abstract class Instruction {
    protected String abbr = "MISSING ABBREV";
    protected int moveX = 0;
    protected int times = 1;
    protected String note = "";

    abstract void parse(String rawInstruction);
    abstract void execute(Pattern p, Stitch x);
}
