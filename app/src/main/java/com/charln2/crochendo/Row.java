package com.charln2.crochendo;

public class Row {
    Stitch head, tail = null;


    private boolean ltr = true;

    public Row() { }

    Row(boolean ltr) {
        this.ltr = ltr;
    }

    public boolean isLtr() {
        return ltr;
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
                sb.append(String.format("%-5s|", cur.toString()));
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

    int getPadding() {
        int count = 0;
        Stitch cur = tail;
        // go backwards until stitch with anchor reached
        while (cur != null && cur.anchor == null) {
             count--;
            cur = cur.prev;
        }

        // if anchor found for prev, count remaining spaces for this row as padding
        if (cur != null) {
            cur = cur.anchor;
            while (cur != null) {
                count++;
                cur = cur.prev;
            }
        }
        return count;
    }

    public Stitch pop() throws IllegalAccessException {
        if (tail == null) throw new IllegalAccessException("Row is empty");

        Stitch ret = tail;
        tail.next = tail.prev = null;

        if (head == tail) {
            // 1 element remaining, delete.
            head = tail = null;
        } else {
            tail.prev.next = null;
        }
        return ret;
    }

    public Stitch peekTail() {
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

    public int size() {
        Stitch st = head;
        int size = 0;
        while (st != null) {
            size++;
            st = st.next;
        }
        return size;
    }
}
