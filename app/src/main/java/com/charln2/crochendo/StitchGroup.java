package com.charln2.crochendo;

import java.util.ArrayList;

public class StitchGroup extends Stitch {
    private ArrayList<Stitch> stitchGroup;

    public boolean isChainGroup() {
        return ischainGroup;
    }

    private boolean ischainGroup = true;

    public StitchGroup() {
        super("^^^");
        stitchGroup = new ArrayList<>();
    }

    public StitchGroup(String note) {
        this();
        this.note = note;
    }

    public void add(Stitch st) {
        if (ischainGroup && !st.name.equalsIgnoreCase("ch")) {
            ischainGroup = false;
        }
        stitchGroup.add(st);
    }

    @Override
    public String toString() {
        if (ischainGroup) {
            return String.format("ch-%d", stitchGroup.size());
        } else {
            return super.toString();
        }
    }

    Stitch[] getGroupList(boolean ltr) {
        Stitch[] ret = new Stitch[stitchGroup.size()];
        for (int i = 0, j = stitchGroup.size()-1; j >= 0; i++, j--) {
            ret[i] = (ltr) ? stitchGroup.get(i) : stitchGroup.get(j);
        }
        return ret;
    }
}
