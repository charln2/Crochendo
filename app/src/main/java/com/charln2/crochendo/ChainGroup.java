package com.charln2.crochendo;

/**
 * Created by charl on 3/2/2018.
 */

public class ChainGroup extends Stitch {
    int n;
    public ChainGroup(int n) {
        super("chain group", "ch-"+n);
        this.n = n;
    }
    @Override
    public String toString() {
        return String.format("ch-%d",n);
    }
}
