package com.charln2.crochendo;

/**
 * Created by Ripley on 3/1/2018.
 */

public class Row {
    boolean ltr = true;
    Stitch head, tail = null;
    int index;
    public Row() {}
    public Row(boolean ltr) {
        this.ltr = ltr;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // todo: refactor, D.R.Y.
        if (ltr) {
            Stitch cur = head;
            while (cur != tail) {
                sb.append(String.format("%-5s|", cur.toString()));
                cur = cur.next;
            }
        } else {
            Stitch cur = tail;
            while (cur != head) {
                sb.append(String.format("%-5s|", cur.toString()));
                cur = cur.prev;
            }
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length()-1); // trim last "|"
        }
        return sb.toString();
    }

    //todo: toStringExpanded
    void add(Stitch s) {
        if (head == null) {
            head = tail = s;
        }
        s.prev = tail;
        tail.next = s;
        tail = tail.next;
    }
}
