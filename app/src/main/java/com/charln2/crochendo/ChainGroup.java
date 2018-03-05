package com.charln2.crochendo;

/**
 * Created by charl on 3/2/2018.
 */

public class ChainGroup extends Stitch {
    private int numChains;

    public ChainGroup(int numChains) {
        super("ch-" + numChains);
        this.numChains = numChains;
    }

    @Override
    public String toString() {
        return String.format("ch-%d", numChains);
    }
}
