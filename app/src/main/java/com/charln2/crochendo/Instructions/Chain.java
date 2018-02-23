package com.charln2.crochendo.Instructions;

import com.charln2.crochendo.Pattern;
import com.charln2.crochendo.Stitch;

import java.util.Scanner;

/**
 * Created by Ripley on 2/22/2018.
 */

public class Chain extends Instruction {
    public Chain(String rawInstruction) {
        abbr = "ch";
        moveX = 0;
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

    @Override
    public void execute(Pattern p) {
        for (int i = 0; i < times; i++) {
            Stitch s = new Stitch("ch", note);
//            x.addAnchor(s);
            p.append(s);
        }
    }
}
