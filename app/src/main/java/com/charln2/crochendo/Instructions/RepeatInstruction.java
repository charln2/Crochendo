package com.charln2.crochendo.Instructions;

import com.charln2.crochendo.Pattern;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by charl on 3/4/2018.
 */

public class RepeatInstruction extends Instruction {
    String holdName;
    @Override
    Instruction create() {
        return new RepeatInstruction();
    }

    public RepeatInstruction() {
        abbr = "repeat";
    }

    @Override
    void parse(String rawInstruction) {
        // repeat from * across
        Scanner sc = new Scanner(rawInstruction);
        sc.skip(".*from");
        holdName = sc.next();
//        to = sc.next();
    }

    @Override
    public void execute(Pattern p) {
        //copy specific instructions from
        //Pattern method to get slice if processed list from index i?
        ArrayList<Instruction> copy = p.copyInstructions(holdName);
        int spaces = p.getX().countSpaces();
        int stitchCount = 0;
        for (Instruction i : copy) {
            stitchCount += i.times;
        }

        while (spaces >= copy.size()) {
            for (int i = 0; i < copy.size(); i++) {
                copy.get(i).execute(p);
            }
            spaces -= stitchCount;
        }
    }
}
