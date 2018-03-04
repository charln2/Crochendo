package com.charln2.crochendo.Instructions;

/**
 * Created by charl on 3/3/2018.
 */

public class TurnInstruction extends Instruction {
    @Override
    Instruction create() {
        return new TurnInstruction();
    }

    public TurnInstruction() {
        abbr = "turn";
        anchorStitch = "";
    }
}
