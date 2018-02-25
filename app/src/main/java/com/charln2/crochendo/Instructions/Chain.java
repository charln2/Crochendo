package com.charln2.crochendo.Instructions;

import java.util.Scanner;

/**
 * Created by Ripley on 2/22/2018.
 */

public class Chain extends StitchInstruction {
    public Chain(String rawInstruction) {
        abbr = "ch";
        parse(rawInstruction);
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
