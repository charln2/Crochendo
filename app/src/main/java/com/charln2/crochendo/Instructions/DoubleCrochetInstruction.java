package com.charln2.crochendo.Instructions;

import java.util.Scanner;


class DoubleCrochetInstruction extends Instruction {
    DoubleCrochetInstruction() {
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
        rawInstruction = rawInstruction.replaceAll("(?<=\\d)(st|nd|rd|th)", "");
        rawInstruction = rawInstruction.replaceAll("(\\sin)", "");
        rawInstruction = rawInstruction.replaceAll("first", "1");
        Scanner sc = new Scanner(rawInstruction);
        if (sc.hasNextInt()) {
            times = sc.nextInt();
        }
        sc.skip(abbr);
        if (sc.hasNextInt()) {
            ith = sc.nextInt();
            targetStitch = sc.next();
            if (sc.findInLine("from hook") != null) {
                ith--;
            }
        } else if (sc.hasNext() && sc.next().equals("next")) {
//            sc.skip("next");
            times = sc.nextInt();
            targetStitch = sc.next();
        }
    }
}
