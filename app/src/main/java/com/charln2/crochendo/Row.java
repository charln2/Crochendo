package com.charln2.crochendo;

/**
 * Created by Ripley on 3/1/2018.
 */

public class Row {
    Stitch head, tail = null;
    private boolean ltr = true;

    public Row() { }

    Row(boolean ltr) {
        this.ltr = ltr;
    }

    @Override
    public String toString() {
        if (isEmpty()) return "";

        StringBuilder sb = new StringBuilder();

        Stitch cur = (ltr) ? head : tail;
        while (cur != null) {
            sb.append(String.format("%-5s|", cur.toString()));
            cur = ltr ? cur.next : cur.prev;
        }

        sb.setLength(sb.length() - 1); // trim last "|"

        return sb.toString();
    }

    public String toStringExpanded() {
        if (isEmpty()) return "";

        StringBuilder sb = new StringBuilder();

        Stitch cur = ltr ? head : tail;
        while (cur != null) {
            if (!cur.name.equalsIgnoreCase("sk")) {
                if (cur instanceof StitchGroup && !((StitchGroup)cur).isChainGroup()) {
                    for (Stitch st : ((StitchGroup) cur).getGroupList(ltr)) {
                        sb.append(String.format("%-5s|", st.toString()));
                    }
                } else {
                    sb.append(String.format("%-5s|", cur.toString()));
                }
            }
            cur = ltr ? cur.next : cur.prev;
        }

        sb.setLength(sb.length() - 1); // trim last "|"
        return sb.toString();
    }

    void add(Stitch st) {
        if (head == null) {
            head = tail = st;
            return;
        }
        st.prev = tail;
        tail.next = st;
        tail = tail.next;
    }

    public Stitch pop() throws IllegalAccessException {
        if (tail == null) throw new IllegalAccessException("Row is empty");

        tail = tail.prev;
        if (tail != null)
            tail.next = null;

        return tail;
    }

    public Stitch peekLast() {
        return tail;
    }

    public void prepend(Stitch st) {
        st.next = head;
        head.prev = st;
        head = st;
    }

    public boolean isEmpty() {
        return head == null && tail == null;
    }
}
