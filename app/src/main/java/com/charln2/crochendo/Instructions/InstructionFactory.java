package com.charln2.crochendo.Instructions;

import java.util.HashSet;
import java.util.Scanner;


/**
 * Created by Ripley on 2/22/2018.
 */

public class InstructionFactory {
    private static final String TAG = InstructionFactory.class.getSimpleName();

    private InstructionFactory(){}
    public static Instruction getInstruction(String rawInstruction) throws InstantiationException{
        Scanner sc = new Scanner(rawInstruction);
        StringBuilder abbrev = new StringBuilder();
        while (!isValidInstruction(abbrev.toString()) && sc.hasNext()) {
            if(abbrev.toString().matches("\\d+")) {
                abbrev.setLength(0); // clear out number, start over.
            }
            abbrev.append(sc.next());
        }
        if (isValidInstruction(abbrev.toString())) {
            switch (abbrev.toString()) {
                case "ch":
                    return new Chain(rawInstruction);
                case "row":
                    return new Row(rawInstruction);
                case "dc":
                    return new DoubleCrochet(rawInstruction);
                case "sk":
                    return new Skip(rawInstruction);
                    //todo: turn instruction
                    //todo: slip stitch instruction

                default:
                    if (abbrev.toString().startsWith("*")) {
                        //todo: make Hold instruction
    //                    return new Hold(rawInstruction);
                    }
                    if (abbrev.toString().startsWith("(")) {
                        //todo: make shell instruction
    //                    return new Shell(rawInstruction);
                    }
            }
        }
        throw new InstantiationException(String.format("No factory class for \"%s\"", abbrev));
    }

    // kinda cludgey. Find a better way but good makeshift design choice for now
    public static final HashSet<String> stitches = new HashSet<String>(){{
        add("ch");
        add("dc");
        add("sl st");
        add("row");
        add("sk");
    }};
    public static boolean isValidInstruction(String s) {
        s = s.toLowerCase().trim();
        return stitches.contains(s) || s.startsWith("*");
    }
}
