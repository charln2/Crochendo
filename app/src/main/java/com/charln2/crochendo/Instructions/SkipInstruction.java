package com.charln2.crochendo.Instructions;

import java.util.Scanner;


class SkipInstruction extends Instruction {
    @Override
    Instruction create() {
        return new SkipInstruction();
    }

    SkipInstruction() {
        abbr = "sk";
    }

    @Override
    void parse(String rawInstruction) {
        //sk first dc
        //sk next dc
        //sk next ch-1 sp
        //sk next 2 ch
        //sk next 2 dc
        super.parse(rawInstruction);
        rawInstruction = rawInstruction.replaceAll("\\s((first|next)\\s)", "");
        Scanner sc = new Scanner(rawInstruction);
        sc.skip("sk"); // fixed. Accidentally parsed "sk" as anchorStitch.
        if (sc.hasNextInt()) {
            times = sc.nextInt();
        }
        if (sc.hasNext()) {
            anchorStitch = sc.next();
        }
    }
}
