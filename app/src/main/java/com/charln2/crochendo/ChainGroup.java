package com.charln2.crochendo;

/**
 * Created by charl on 3/2/2018.
 */

public class ChainGroup extends Stitch {
    int n;
    private ChainGroup(int n) {
        super("ch", "ch-"+n);
    }
    @Override
    public String toString() {
        return "ch-"+n;
    }
}
