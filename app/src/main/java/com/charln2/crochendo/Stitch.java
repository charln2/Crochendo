package com.charln2.crochendo;

import java.util.HashSet;

public class Stitch {
    String name;
    Stitch prev, next, anchor;
    String note;
    private HashSet<String> aliasSet;

    public Stitch(String name) {
        this.name = name;
        this.prev = this.next = this.anchor = null;
        this.note = null;
        this.aliasSet = null;
    }

    public void setAnchor(Stitch anchor) {
        this.anchor = anchor;
    }

    Stitch nextPort(int ith, String target) {
        Stitch ret = this;
        while (ith > 0) {
            do {
                ret = ret.prev;
                if (ret == null)
                    throw new NoClassDefFoundError(String.format(
                            "Could not find port stitch named '%s' in row", target));
            } while (ret.name.equals("sk"));
            //todo: any more stitches that are ignored?

            if (target.equals("") || ret.name.equals(target)
                    || (ret.aliasSet != null && ret.aliasSet.contains(target))) {
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

    boolean sameAnchor(Stitch oth) {
        return oth != null
                && oth.anchor != null
                && anchor != null
                && anchor == oth.anchor;
    }

    boolean consecutiveChains(Stitch oth) {
        if (name.equals("sl st")) {
            return false;
        }
        return name.equals("ch") || oth != null && oth.name.equals("ch");
    }

    public void addAlias(String s) {
        if (aliasSet == null) {
            aliasSet = new HashSet<>();
        }
        aliasSet.add(s);
    }
}
