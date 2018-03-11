package com.charln2.crochendo;

public class ChainGroup extends StitchGroup {
    private int chains;
    public ChainGroup() {
        super("ch-");
        chains = 0;
    }

    public ChainGroup(int n) {
        this();
        chains = n;
    }
    public ChainGroup(String note) {
        this();
        this.note = note;
    }

    public void add(int n) {
        chains += n;
    }

    @Override
    Stitch nextAnchorStitch() {
        return prev;
    }

    @Override
    public String toString() {
        return String.format("ch-%d", chains);
    }
}
