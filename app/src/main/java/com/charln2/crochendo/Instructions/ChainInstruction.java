package com.charln2.crochendo.Instructions;

import java.util.Scanner;



public class ChainInstruction extends Instruction {
    public ChainInstruction() {
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
