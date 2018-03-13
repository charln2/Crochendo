package com.charln2.crochendo.Instructions;

import com.charln2.crochendo.ChainGroup;
import com.charln2.crochendo.Pattern;
import com.charln2.crochendo.Stitch;
import com.charln2.crochendo.StitchGroup;

import java.security.acl.Group;
import java.util.Scanner;


class ChainInstruction extends Instruction {

    @Override
    Instruction create() {
        return new ChainInstruction();
    }

    ChainInstruction() {
        abbr = "ch";
        ith = 0;
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
    protected Stitch attach(Pattern p) {
        Stitch tail = p.peek();
        if (tail instanceof ChainGroup) {
            ((ChainGroup) tail).add(1);
            return null;
        } else if (tail instanceof StitchGroup) {
            ((StitchGroup) tail).add(new Stitch(abbr));
            return null;
        } else if (tail.toString().equals("ch")) {
            try {
                p.getRow(-1).pop();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            p.add(new ChainGroup(2));
            return null;
        } else if (tail.toString().equals("dc")){
            // todo: Make Stitch abstract class, w/ dc, sc, sl st, etc inheriting
            StitchGroup sg = new StitchGroup();
            try {
                sg.add(p.getRow(-1).pop());
                //todo: any inbetween for SG? Or just these 2?
                sg.add(new Stitch(abbr));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            p.add(sg);
            return sg;
        }
        Stitch st = new Stitch("ch");
        p.add(st);
        return st;
    }
}
