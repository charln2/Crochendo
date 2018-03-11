package com.charln2.crochendo;

import java.util.ArrayList;

public class Stitch {
    String name;
    Stitch prev, next;
    ArrayList<Stitch> anchors;
    String note;

    public Stitch(String name) {
        this.name = name;
        this.prev = null;
        this.next = null;
        this.note = null;
    }

    Stitch nextAnchorStitch() {
        return this.prev;
    }

    // package private, so Pattern can instantiate
    public Stitch(String name, String note) {
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
        return name;
    }

    public int countSpaces() {
        int i = 0;
        Stitch counter = this;
        while (counter.prev != null) {
            i++;
            counter = counter.prev;
        }
        return i;
    }

    Stitch pop() {
        if (this.prev != null) {
            this.prev.next = null;
            this.prev = null;
        }
        this.next = null;
        return this;
    }
}
