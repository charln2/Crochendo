package com.charln2.crochendo.Instructions;

import java.util.Scanner;



public class DoubleCrochetInstruction extends Instruction {
    public DoubleCrochetInstruction() {
        abbr = "dc";
    }

    @Override
    Instruction create() {
        return new DoubleCrochetInstruction();
    }

    @Override
    void parse(String rawInstruction) {
        super.parse(rawInstruction);
        //"dc in 4th ch from hook (beginning ch counts as dc)"
        rawInstruction = rawInstruction.replaceAll("\\d(st|nd|rd|th)", "");
        rawInstruction = rawInstruction.replaceAll("(\\sin)?(\\snext)?", "");
        rawInstruction = rawInstruction.replaceAll("first", "1");
        Scanner sc = new Scanner(rawInstruction);
        if (sc.hasNextInt()) {
            times = sc.nextInt();
        }
        sc.skip(abbr);
        if (sc.hasNextInt()) {
            ith = sc.nextInt();
        }
        if (sc.hasNext()) {
            anchorStitch = sc.next();
        }

        if (sc.findInLine("from hook") != null) {
            ith--;
        }
    }
}
