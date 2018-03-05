package com.charln2.crochendo.Instructions;

class SlipStitchInstruction extends Instruction {
    @Override
    Instruction create() {
        return new SlipStitchInstruction();
    }

    SlipStitchInstruction() {
        abbr = "sl st";
    }
}
