package com.charln2.crochendo;

public class ChainGroup extends Stitch {

    private int numChains = 0;

    public ChainGroup() {
        super("ch-");
    }

    public ChainGroup(int n) {
        this();
        numChains = n;
    }

    public ChainGroup(String note) {
        this();
        this.note = note;
    }

    public void add(int n) {
        numChains += n;
    }

    @Override
    public String toString() {
        return String.format("ch-%d", numChains);
    }
}
