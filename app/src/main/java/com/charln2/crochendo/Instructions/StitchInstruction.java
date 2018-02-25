package com.charln2.crochendo.Instructions;

import com.charln2.crochendo.Pattern;
import com.charln2.crochendo.Stitch;

/**
 * Created by Ripley on 2/22/2018.
 */

public abstract class StitchInstruction extends Instruction{
    int times = 1;
    String anchorStitch;
    int ith = 1;

    @Override
    void parse(String rawInstruction) {
        super.parse(rawInstruction);
    }

    public void execute(Pattern p) {
        for(int i = 0; i < times; i++) {
            Stitch s = new Stitch(abbr, note);
            p.append(s);
            if (anchorStitch != null) {
                p.moveX(ith, anchorStitch);
                p.addAnchor(s);
                if (note.contains("beginning ch counts as")) {
                    p.overwritePreviousRowEnd("ch");
                }
            }
        }
    }
}
