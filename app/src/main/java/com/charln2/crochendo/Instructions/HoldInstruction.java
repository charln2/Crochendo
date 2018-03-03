package com.charln2.crochendo.Instructions;

import com.charln2.crochendo.Pattern;

/**
 * Created by charl on 3/3/2018.
 */

public class HoldInstruction extends Instruction {
    String key;
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
        p.hold.put(key, p.getX().prev());
    }
}
