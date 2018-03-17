package com.charln2.crochendo.Instructions;

import com.charln2.crochendo.Pattern;
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
        targetStitch = sc.next();
    }

    @Override
    public void execute(Pattern p) {
        if (targetStitch != null) {
            p.moveX(ith, targetStitch);
        }
        for (int i = 0; i < stitchNames.size(); i++) {
            String name = stitchNames.get(i);
            int count = stitchCounts.get(i);
            for (int j = 0; j < count; j++) {
                Stitch st = attach(p, name);
                anchor(p, st);
            }
        }
    }

    //!assumption: sl st or any 2+ word stitches not in shell; always 2 args
    // NOT always 2 args.
    private void extractShell(String parens) {
        stitchNames = new ArrayList<>();
        stitchCounts = new ArrayList<>();
        // (3 dc, ch 1, 3 dc)
        // (dc, ch 1, dc) in next ch-1 sp
        parens = parens.replaceAll("[()]", "");
        for (String stitch : parens.split(",")) {
            String[] args = stitch.trim().split("\\s");
            if (args.length == 1) {
                stitchCounts.add(1);
                stitchNames.add(args[0]);
            } else {
                for (String s : args) {
                    Scanner sc = new Scanner(s);
                    while (sc.hasNext()) {
                        if (sc.hasNextInt()) {
                            stitchCounts.add(sc.nextInt());
                        } else {
                            stitchNames.add(sc.next());
                        }
                    }
                }
            }
        }
    }
}
