package com.charln2.crochendo.Instructions;

import com.charln2.crochendo.Pattern;


class HoldInstruction extends Instruction {
    private String key;

    @Override
    Instruction create() {
        return new HoldInstruction();
    }

    @Override
    void parse(String rawInstruction) {
        super.parse(rawInstruction);
        key = rawInstruction;
    }

    @Override
    public void execute(Pattern p) {
        p.hold(key);
    }
}
