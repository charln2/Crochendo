package com.charln2.crochendo.Instructions;

import com.charln2.crochendo.Pattern;
import com.charln2.crochendo.Stitch;

import java.util.Scanner;

/**
 * Created by charl on 2/23/2018.
 */

public class Beginning extends Instruction {
    StringBuilder search;
    public Beginning(String rawInstructions) {
        abbr = "beginning";
        note = rawInstructions;
        parse(rawInstructions);
    }
    @Override
    void parse(String rawInstruction) {
        //"(beginning ch counts as dc)"
        Scanner sc = new Scanner(rawInstruction);
        sc.next(); // skip "(beginning)"
        while (sc.hasNext()) {
            search.append(sc.next());
            if (Pattern.stitches.contains(search.toString())) break;
        }
    }

    @Override
    public void execute(Pattern p) {
        // breaks factory pattern; may change later
        p.shiftNewestRowToNext(search.toString());
    }
}
