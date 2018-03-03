package com.charln2.crochendo.Instructions;

import com.charln2.crochendo.Pattern;

/**
 * Created by Ripley on 3/1/2018.
 */

public class SlipStitchInstruction extends Instruction {
    public SlipStitchInstruction() {
        abbr = "sl st";
    }

    @Override
    Instruction create() {
        return new SlipStitchInstruction();
    }
}
