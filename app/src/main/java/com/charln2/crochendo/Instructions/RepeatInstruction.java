package com.charln2.crochendo.Instructions;

import com.charln2.crochendo.Pattern;

import java.util.ArrayList;
import java.util.Scanner;

public class RepeatInstruction extends Instruction {
    private String holdName;

    @Override
    Instruction create() {
        return new RepeatInstruction();
    }

    RepeatInstruction() {
        abbr = "repeat";
    }

    @Override
    void parse(String rawInstruction) {
        // repeat from * across
        Scanner sc = new Scanner(rawInstruction);
        sc.skip(".*from");
        holdName = sc.next();
//        to = sc.next();
        // findInLine("across")
    }

    @Override
    public void execute(Pattern p) {
        ArrayList<Instruction> copy = p.copyInstructionsFrom(holdName);
        int spaces = p.getPort().countSpaces();
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
