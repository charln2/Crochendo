package com.charln2.crochendo;

import java.util.ArrayList;

public class StitchGroup extends Stitch {
    private ArrayList<Stitch> stitchGroup;

    public StitchGroup() {
        super("^^^");
        stitchGroup = new ArrayList<>();
    }


    public StitchGroup(String note) {
        this();
        this.note = note;
    }

    @Override
    Stitch nextAnchorStitch() {
        return stitchGroup.get(stitchGroup.size()-1);
    }

    public void add(Stitch st) {
        stitchGroup.add(st);

        if (stitchGroup.size() == 1) {
            st.prev = this.prev;
        } else {
            st.prev = stitchGroup.get(stitchGroup.size()-2);
            st.prev.next = st;
        }

        st.next = this.next;
    }

    Stitch[] getGroupList(boolean ltr) {
        Stitch[] ret = new Stitch[stitchGroup.size()];
        for (int i = 0, j = stitchGroup.size()-1; j >= 0; i++, j--) {
            ret[i] = (ltr) ? stitchGroup.get(i) : stitchGroup.get(j);
        }
        return ret;
    }
}
