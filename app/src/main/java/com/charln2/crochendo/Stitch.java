package com.charln2.crochendo;

public class Stitch {
    String name;
    Stitch prev, next, anchor;
    String note;

    public Stitch(String name) {
        this.name = name;
        this.prev = this.next = this.anchor = null;
        this.note = null;
    }

    public void setAnchor(Stitch anchor) {
        this.anchor = anchor;
    }

    Stitch nextPort(int ith, String target) {
        Stitch ret = this;
        while (ith > 0) {
            do {
                ret = ret.prev;
                if (anchor == null)
                    throw new NoClassDefFoundError(String.format(
                            "Could not find port stitch named '%s' in row", target));
            } while (ret.name.equals("sk"));
            //todo: any more stitches that are ignored?

            if (anchor.name.equals(target) || target.equals("")) {
                ith--;
            }
        }
        return ret;
    }

    // package private, so Pattern can instantiate
    public Stitch(String name, String note) {
        this(name);
        this.note = note;
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
}
