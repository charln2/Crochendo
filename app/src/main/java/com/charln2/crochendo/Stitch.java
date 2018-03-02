package com.charln2.crochendo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


public class Stitch {
    String name;
    Stitch prev, next, shell;
    ArrayList<Stitch> anchors;
    String note;
    // kinda cludgey. Find a better way but good makeshift design choice for now
    // todo: extensible storage for valid stitches
    public static final HashSet<String> stitches = new HashSet<String>(){{
        add("ch");
        add("dc");
        add("sl st");
        add("row");
        add("sk");
        add("(");
    }};
    private Stitch() {}
    public static Stitch getStitch(String rawInstruction)  {
        String validStitch = hasValidStitch(rawInstruction);
//        // todo: handle shell case. (handling in Shell Instruction's Execute method
//        if (validStitch.equals("(")) {
//            //  name = ^^^
//            // parse/append instructions into shell
//            // outer instruction class handles attaching to pattern & anchoring
//
//        }
        return validStitch == null ? null : new Stitch(validStitch, rawInstruction);
    }
    private Stitch(String name, String note) {
        this.name = name;
        this.prev = null;
        this.next = null;
        this.note = note;
    }

    void addAnchor(Stitch a) {
        if (anchors == null) {
            anchors = new ArrayList<>();
        }
        anchors.add(a);
    }

    void addShellStitch(Stitch s) {
        if (shell == null) {
            shell = s;
        }
        shell.next = s;
        s.prev = shell;
    }

    @Override
    public String toString() {
        return name;
    }

    private static String hasValidStitch(String s) {
        s = s.toLowerCase().trim();
        Iterator<String> iter = stitches.iterator();
        while(iter.hasNext()) {
            String validStitch = iter.next();
            if (s.startsWith(validStitch)) {
                return validStitch;
            }
        }
        return null;
    }
}