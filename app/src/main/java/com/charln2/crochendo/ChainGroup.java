package com.charln2.crochendo;

import java.util.ArrayList;

public class ChainGroup extends StitchGroup {

    public ChainGroup() {
        super("ch-");
    }

    public ChainGroup(int n) {
        this();
        add(n);
    }

    public ChainGroup(String note) {
        this();
        this.note = note;
    }

    public void add(int n) {
        for (int i = 0; i < n; i++) {
            Stitch st = new Stitch("ch");
            stitchGroup.add(st);
            if (stitchGroup.size() == 1) {
                st.prev = this.prev;
                continue;
            }
            st.prev = stitchGroup.get(stitchGroup.size() - 1);
            st.prev.next = st;
            st.next = this.next;
        }
    }

    @Override
    Stitch nextPort() {
        return stitchGroup.get(stitchGroup.size()-1);
    }

    @Override
    public String toString() {
        return String.format("ch-%d", stitchGroup.size());
    }

    @Override
    Stitch[] getGroupList(boolean ltr) {
        Stitch[] ret = new Stitch[stitchGroup.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = stitchGroup.get(i);
        }
        return ret;
    }
}
