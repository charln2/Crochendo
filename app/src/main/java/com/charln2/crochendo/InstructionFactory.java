package com.charln2.crochendo;

import android.util.Log;

import com.charln2.crochendo.Instructions.Chain;
import com.charln2.crochendo.Instructions.Instruction;

import java.util.Scanner;


/**
 * Created by Ripley on 2/22/2018.
 */

public class InstructionFactory {
    private static final String TAG = InstructionFactory.class.getSimpleName();

    private InstructionFactory(){}
    public static Instruction getInstruction(String rawInstruction) {

        Scanner sc = new Scanner(rawInstruction);
        StringBuilder abbrev = new StringBuilder();
        while (sc.hasNext() && !sc.hasNextInt()) {
            abbrev.append(sc.next());
        }
        switch (abbrev.toString()) {
            case "ch":
                return new Chain(rawInstruction);
            default:
                Log.e(TAG, "getInstruction: ", new InstantiationException());
        }
        return null;
    }
}
