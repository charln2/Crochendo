package com.charln2.crochendo.Instructions;

import com.charln2.crochendo.Pattern;



public class Row extends Instruction {
    public Row(String rawInstruction) {
        abbr = "row";
        note = rawInstruction;
//        parse(rawInstruction);
    }
    @Override
    void parse(String rawInstruction) {
        //Todo: validate rows in Pattern's ArrayList?
        //Row 1 (RS)
        //Row 2

        //Todo: Row[s] 2-4 (grouped rows)
    }

    @Override
    public void execute(Pattern p) {
        p.startNewRow();
    }
}
