package com.charln2.crochendo.Instructions;

import java.util.HashMap;
import java.util.Scanner;


/**
 * Created by Ripley on 2/22/2018.
 */

public class InstructionFactory {
    private static final String TAG = InstructionFactory.class.getSimpleName();

    private InstructionFactory(){}
    public static Instruction getInstruction(String rawInstruction) throws InstantiationException{
        rawInstruction = rawInstruction.toLowerCase().trim();
        rawInstruction = rawInstruction.replaceAll("work","");

        Instruction i = null;
        i = fetchInstruction(rawInstruction);
        i.parse(rawInstruction);
        return i;
    }

    static Instruction fetchInstruction(String rawInstruction) throws InstantiationException {
        StringBuilder sb = new StringBuilder();
        Scanner sc = new Scanner(rawInstruction);

        //todo: refactor D.R.Y.
        // check 1: match stitches exactly
        // "sl st" is 2 words in length. Potential for
        for(int i = 0; i < 2; i++) {
            if (sc.hasNext()) {
                sb.append(sc.next());
                if (stitchInstructions.containsKey(sb.toString())) {
                    return stitchInstructions.get(sb.toString()).create();
                }
            }
        }
        // check 2: if check 1 fails, check for special case
        // in a more brute-force way
        for(String s : specialCaseInstructions.keySet()) {
            if (rawInstruction.startsWith(s)) {
                return specialCaseInstructions.get(s).create();
            }
        }

        throw new InstantiationException(
                String.format("No existing instruction for \"%s\"", rawInstruction));
    }

    static HashMap<String,Instruction> stitchInstructions = new HashMap<String, Instruction>() {{
        put("ch", new ChainInstruction());
        put("row", new RowInstruction());
        put("dc", new DoubleCrochetInstruction());
        put("sk", new SkipInstruction());
    }};
    static HashMap<String,Instruction> specialCaseInstructions = new HashMap<String, Instruction>() {{
        put("sl st", new SlipStitchInstruction());
        //todo: shell instruction
        //put("(", new ShellInstruction());
//        if (rawInstruction.contains("(beginning ch counts as)")) {
//            //todo: unpack "hidden" instructions
////                rows.add(x);
//        }
        //todo: turn
        //todo: hold
//        while (rawInstruction.startsWith("*")) {
//            int i;
//            for (i = 0; i < rawInstruction.length() && rawInstruction.charAt(i)!='*'; i++);
//            qInstructions.add(InstructionFactory.getInstruction(rawInstruction.substring(0,i)));
//            rawInstruction = rawInstruction.substring(i);
//        }
        //todo: chain group
    }};
}
