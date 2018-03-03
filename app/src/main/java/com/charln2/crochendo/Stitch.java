package com.charln2.crochendo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


public class Stitch {
    String name;

    Stitch prev, next;
    ArrayList<Stitch> anchors;
    String note;
//    // kinda cludgey. Find a better way but good makeshift design choice for now
//    // todo: extensible storage for valid stitches
//    public static final HashSet<String> stitches = new HashSet<String>(){{
//        add("ch");
//        add("dc");
//        add("sl st");
//        add("row");
//        add("sk");
//        add("(");
//    }};
//    public Stitch(String name) {
//        this.name = name;
//    }
    public Stitch(String name) {
        this.name = name;
        this.prev = null;
        this.next = null;
        this.note = null;
    }
    // todo: decide if constructor is better. Light factory
//    public static Stitch getStitch(String rawInstruction)  {
        // level 3:
//        String validStitch = hasValidStitch(rawInstruction);
//        // todo: handle shell case. (handling in Shell Instruction's Execute method
//        if (validStitch.equals("(")) {
//            //  name = ^^^
//            // parse/add instructions into shell
//            // outer instruction class handles attaching to pattern & anchoring
//
//        }
//        return validStitch == null ? null : new Stitch(validStitch, rawInstruction);
//    }
    // package private, so Pattern can instantiate
    protected Stitch(String name, String note) {
        this(name);
        this.note = note;
    }

    void addAnchor(Stitch s) {
        if (anchors == null) {
            anchors = new ArrayList<>();
        }
        anchors.add(s);
    }

    @Override
    public String toString() {
//        return String.format("%-5s|", name);
        return name;

    }

    public Stitch prev() {
        return prev;
    }
    public Stitch next() {
        return next;
    }
}
