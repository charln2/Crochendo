package com.charln2.crochendo;

import android.util.Log;

import com.charln2.crochendo.Instructions.Chain;
import com.charln2.crochendo.Instructions.Row;
import com.charln2.crochendo.Instructions.StitchInstruction;

import java.util.HashSet;
import java.util.Scanner;


/**
 * Created by Ripley on 2/22/2018.
 */

public class InstructionFactory {
    private static final String TAG = InstructionFactory.class.getSimpleName();

    private InstructionFactory(){}
    public static StitchInstruction getInstruction(String rawInstruction) throws InstantiationException{

        Scanner sc = new Scanner(rawInstruction);
        StringBuilder abbrev = new StringBuilder();
        while (!isValidInstruction(abbrev.toString()) && sc.hasNext()) {
            abbrev.append(sc.next());
        }
        if (!isValidInstruction(abbrev.toString())) {
            throw new InstantiationException(String.format("No factory class for %s", abbrev));
        }
        switch (abbrev.toString()) {
            case "ch":
                return new Chain(rawInstruction);
            case "row":
                return new Row(rawInstruction);

            default:
                if (abbrev.toString().startsWith("*")) {
                    //todo: make Hold instruction
//                    return new Hold(rawInstruction);
                }
                if (abbrev.toString().startsWith("(")) {
                    //todo: make shell instruction
//                    return new Shell(rawInstruction);
                }
                Log.e(TAG, "getInstruction: Problems instantiating instruction in factory", new InstantiationException());
        }
        return null;
    }

    // kinda cludgey. Find a better way but good makeshift design choice for now
    public static final HashSet<String> stitches = new HashSet<String>(){{
        add("ch");
        add("dc");
        add("sl st");
        add("row");
    }};
    public static boolean isValidInstruction(String s) {
        s = s.toLowerCase().trim();
        return stitches.contains(s) || s.startsWith("*");
    }
}
