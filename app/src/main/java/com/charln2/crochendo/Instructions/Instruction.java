package com.charln2.crochendo.Instructions;

import com.charln2.crochendo.Pattern;

import java.util.Scanner;

/**
 * Created by Ripley on 2/24/2018.
 */

public abstract class Instruction {
    String abbr = "MISSING ABBREV";
    String note = "";

    void parse(String rawInstruction) {
        Scanner sc = new Scanner(rawInstruction);
        // put (...) in note
        String parens = sc.findInLine("\\(*\\)");
        if (parens != null) {
            note = parens;
        }
    }
    public abstract void execute(Pattern p);
}
