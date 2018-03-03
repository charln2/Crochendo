package com.charln2.crochendo.Instructions;

import com.charln2.crochendo.Pattern;



public class RowInstruction extends Instruction {
    public RowInstruction() {
        abbr = "row";
    }

    @Override
    Instruction create() {
        return new RowInstruction();
    }

    @Override
    void parse(String rawInstruction) {
        //Todo: validate rows in Pattern's ArrayList?
        //RowInstruction 1 (RS)
        //RowInstruction 2

        //Todo: RowInstruction[s] 2-4 (grouped rows)
        note = rawInstruction;
    }

    @Override
    public void execute(Pattern p) {
        p.startNewRow();
    }
}
