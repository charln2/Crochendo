package com.charln2.crochendo.Instructions;

import com.charln2.crochendo.Pattern;


class RowInstruction extends Instruction {
    RowInstruction() {
        abbr = "row";
    }

    @Override
    Instruction create() {
        return new RowInstruction();
    }

    @Override
    void parse(String rawInstruction) {
        //Todo: validate rows in Pattern's ArrayList?
        //Row 1 (RS)
        //Row 2

        //Todo: RowInstruction[s] 2-4 (grouped rows)
        note = rawInstruction;
    }

    @Override
    public void execute(Pattern p) {
        p.startNewRow();
    }
}
