package com.charln2.crochendo.Instructions;

import java.util.HashMap;
import java.util.Scanner;

public class InstructionFactory {
    private static final String TAG = InstructionFactory.class.getSimpleName();

    private InstructionFactory() {
    }

    public static Instruction getInstruction(String rawInstruction) throws InstantiationException {
        rawInstruction = rawInstruction.toLowerCase().trim();
        rawInstruction = rawInstruction.replaceAll("work", "");

        Instruction i;
        i = find(rawInstruction);
        i.parse(rawInstruction);
        return i;
    }

    private static Instruction find(String rawInstruction) throws InstantiationException {
        StringBuilder sb = new StringBuilder();
        Scanner sc = new Scanner(rawInstruction);

        Instruction instruc = null;

        //todo: refactor D.R.Y.
        // check 1: match stitches exactly
        // "sl st" is 2 words in length. Potential for
        for (int i = 0; i < 2; i++) {
            if (sc.hasNext()) {
                sb.append(sc.next() + " ");
                if (stitchInstructions.containsKey(sb.toString().trim())) {
                    instruc = stitchInstructions.get(sb.toString().trim()).create();
                    break;
                }
            }
        }
        if (instruc == null) {
            // check 2: if check 1 fails, check for special case
            // in a more brute-force way
            for (String s : specialCaseInstructions.keySet()) {
                if (rawInstruction.startsWith(s)) {
                    instruc = specialCaseInstructions.get(s).create();
                }
            }
        }
        if (instruc == null) throw new InstantiationException(
                String.format("No existing instruction for \"%s\"", rawInstruction));

        return instruc;
    }

    private static HashMap<String, Instruction> stitchInstructions = new HashMap<String, Instruction>() {{
        put("ch", new ChainInstruction());
        put("dc", new DoubleCrochetInstruction());
        put("sk", new SkipInstruction());
        put("sk", new SkipInstruction());
        put("row", new RowInstruction());
        put("sl st", new SlipStitchInstruction());
        put("repeat", new RepeatInstruction());
    }};
    private static HashMap<String, Instruction> specialCaseInstructions = new HashMap<String, Instruction>() {{
        put("turn", new TurnInstruction());
        put("*", new HoldInstruction());
        put("(", new ShellInstruction());
        //todo: chain group
    }};
}
