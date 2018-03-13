package com.charln2.crochendo;

import java.util.ArrayList;

public class Stitch {
    String name;
    Stitch prev, next, port;
    ArrayList<Stitch> anchors;
    String note;

    public Stitch(String name) {
        this.name = name;
        this.prev = null;
        this.next = null;
        this.note = null;
    }

    public void setPort(Stitch port) {
        this.port = port;
    }

    //? todo: Is there no way to infer method virtually? Thats garbage.
    Stitch nextPort() {
        if (this instanceof ChainGroup) {
            return ((ChainGroup)this).nextPort();
        } else if (this instanceof StitchGroup) {
            return ((StitchGroup)this).nextPort();
        } else {
            return this.prev;
        }
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

    public Stitch peekAnchor() {
        if (anchors == null) return null;
        return anchors.get(anchors.size()-1);
    }

    public Stitch popAnchor() throws IllegalAccessException {
        if (anchors == null) {
            throw new IllegalAccessException("Stitch contains no anchors.");
        }
        Stitch st = peekAnchor();
        anchors.remove(anchors.size()-1);
        return st;
    }
}
