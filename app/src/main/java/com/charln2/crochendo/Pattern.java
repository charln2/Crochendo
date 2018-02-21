package com.charln2.crochendo;

import java.util.ArrayList;

/**
 * Created by charl on 2/21/2018.
 */

public class Pattern {
    Stitch head, tail;
    ArrayList<Stitch> rows;

    public Pattern() {

    }

    void parsePattern(String s) {

    }

    void append(String st) {

    }

    void append(String st, Stitch x) {

    }

    private class Stitch {
        String name;
        Stitch prev, next;
        ArrayList<Stitch> anchors;
        String note;
        private Stitch() {}
        public Stitch(String name) {
            this.name = name;
        }
    }
}
