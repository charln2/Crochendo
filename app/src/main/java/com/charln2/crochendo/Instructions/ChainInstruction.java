package com.charln2.crochendo.Instructions;

import java.util.Scanner;


class ChainInstruction extends Instruction {

    @Override
    Instruction create() {
        return new ChainInstruction();
    }

    ChainInstruction() {
        abbr = "ch";
    }

    @Override
    void parse(String rawInstruction) {
        // ch 3
        Scanner sc = new Scanner(rawInstruction);
        sc.next();
        times = sc.nextInt();
        if (sc.hasNext()) {
            note = sc.nextLine();
        }
    }
}
