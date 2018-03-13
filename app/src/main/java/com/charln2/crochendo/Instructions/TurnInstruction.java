package com.charln2.crochendo.Instructions;

class TurnInstruction extends Instruction {
    @Override
    Instruction create() {
        return new TurnInstruction();
    }

    TurnInstruction() {
        abbr = "turn";
        targetStitch = "";
    }
}
