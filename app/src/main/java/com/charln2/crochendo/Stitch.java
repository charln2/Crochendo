package com.charln2.crochendo;

import java.util.ArrayList;



public class Stitch {
    String name;
    Stitch prev, next, shell;
    ArrayList<Stitch> anchors;
    String note;
    private Stitch() {}
    public Stitch(String name) {
        this.name = name;
        this.prev = null;
        this.next = null;
    }
    public Stitch(String name, String note) {
        this(name);
        this.note = note;
    }
    void addAnchor(Stitch a) {
        if (anchors == null) {
            anchors = new ArrayList<>();
        }
        anchors.add(a);
    }

    @Override
    public String toString() {
        return name;
    }
}