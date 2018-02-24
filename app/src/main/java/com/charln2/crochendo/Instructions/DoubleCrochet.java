package com.charln2.crochendo.Instructions;

import com.charln2.crochendo.Pattern;

import java.util.Scanner;

/**
 * Created by Ripley on 2/24/2018.
 */

public class DoubleCrochet extends StitchInstruction {
    public DoubleCrochet(String rawInstruction) {
        abbr = "dc";
        parse(rawInstruction);
    }

    @Override
    void parse(String rawInstruction) {
        //"dc in 4th ch from hook (beginning ch counts as dc)"
        rawInstruction = rawInstruction.replaceAll("\\d(st|nd|rd|th)", "");
        rawInstruction = rawInstruction.replaceAll("(\\sin)?(\\snext)?", "");
        rawInstruction = rawInstruction.replaceAll("first", "1");
        Scanner sc = new Scanner(rawInstruction);
        if (sc.hasNextInt()) {
            times = sc.nextInt();
        }
        sc.skip(abbr);
        if (sc.hasNextInt()) {
            moveX = sc.nextInt();
        }
        if (sc.hasNext()) {
            skipStitch = sc.next();
        }

        if (sc.findInLine("from hook") != null) {
            moveX--;
        }

        // put (...) in note
        String parens = sc.findInLine("\\(*\\)");
        if (parens != null) {
            note = parens;
        }
    }

    @Override
    public void execute(Pattern p) {
        super.execute(p);
        p.advanceXToNext(skipStitch, moveX);
        attatch(p);
    }
}
