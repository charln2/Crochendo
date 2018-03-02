package com.charln2.crochendo.Instructions;

import com.charln2.crochendo.Stitch;


/**
 * Created by Ripley on 2/22/2018.
 */

public class InstructionFactory {
    private static final String TAG = InstructionFactory.class.getSimpleName();

    private InstructionFactory(){}
    public static Instruction getInstruction(String rawInstruction) throws InstantiationException{
        rawInstruction = rawInstruction.toLowerCase().trim();
        Stitch newStitch = Stitch.getStitch(rawInstruction);
        if (newStitch != null) {
            switch (newStitch.toString()) {
                case "ch":
                    return new Chain(rawInstruction);
                case "row":
                    return new RowInstruction(rawInstruction);
                case "dc":
                    return new DoubleCrochet(rawInstruction);
                case "sk":
                    return new Skip(rawInstruction);
                    //todo: turn instruction
                case "(":
                    //todo: make shell instruction
//                    return new Shell(rawInstruction);
                case "sl st":
                    //todo: slip stitch instruction
                    return new SlipStitch(rawInstruction);
            }
        } else if (rawInstruction.startsWith("*")) {
            //todo: make Hold instruction
            //                    return new Hold(rawInstruction);
        }
        throw new InstantiationException(
                String.format("No existing instruction for \"%s\"", rawInstruction));
    }


}
