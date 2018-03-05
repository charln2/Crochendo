package com.charln2.crochendo.Instructions;

import com.charln2.crochendo.Pattern;
import com.charln2.crochendo.ShellStitch;
import com.charln2.crochendo.Stitch;

import java.util.ArrayList;
import java.util.Scanner;

class ShellInstruction extends Instruction {
    private ArrayList<String> stitchNames;
    private ArrayList<Integer> stitchCounts;

    @Override
    Instruction create() {
        return new ShellInstruction();
    }

    @Override
    void parse(String rawInstruction) {
        super.parse(rawInstruction);
        // (3 dc, ch 1, 3 dc) in ch
        Scanner sc = new Scanner(rawInstruction);
        String parens = sc.findInLine("\\(.*\\)");
        extractShell(parens);

        // extract moveX args
        sc.skip("\\sin\\s(next|first)");
        if (sc.hasNextInt()) {
            ith = sc.nextInt();
        }
        anchorStitch = sc.next();
    }

    @Override
    protected Stitch attach(Pattern p) {
        ShellStitch shell = new ShellStitch();
        for (int i = 0; i < stitchNames.size(); i++) {
            String stitchName = stitchNames.get(i);
            int count = stitchCounts.get(i);
            for (int j = 0; j < count; j++) {
                shell.addShellStitch(new Stitch(stitchName));
            }
        }
        p.add(shell);
        return shell;
    }

    //!assumption: sl st or any 2+ word stitches not in shell; always 2 args
    private void extractShell(String parens) {
        stitchNames = new ArrayList<>();
        stitchCounts = new ArrayList<>();
        // (3 dc, ch 1, 3 dc)
        parens = parens.replaceAll("[()]", "");
        Scanner sc = new Scanner(parens);
        sc.useDelimiter(",\\s*|\\s+");
        while (sc.hasNext()) {
            if (sc.hasNextInt()) {
                stitchCounts.add(sc.nextInt());
            } else {
                stitchNames.add(sc.next());
            }
        }
    }
}
