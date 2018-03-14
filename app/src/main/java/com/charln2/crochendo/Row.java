package com.charln2.crochendo;

public class Row {
    Stitch head, tail = null;


    private boolean ltr = true;

    public Row() {
    }

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
        Stitch lastAncor = null;
        while (cur != null) {
            //todo: handle stitchgroups and chaingroups
            if (matchingAnchors(cur)) {
                sb.append(String.format("%-5s|", "^^^"));
                while (matchingAnchors(cur)) {
                    cur = ltr ? cur.next : cur.prev;
                }
                cur = ltr ? cur.next : cur.prev;
                continue;
            }
            if (cur.name.equals("ch")) {
                Stitch other = ltr ? cur.next : cur.prev;
                int count = 1;
                while (other != null && other.name.equals("ch")) {
                    count++;
                    cur = ltr ? cur.next : cur.prev;
                    other = ltr ? cur.next : cur.prev;
                }
                if (count > 1) {
                    sb.append(String.format("%-5s|", "ch-" + count));
                    cur = ltr ? cur.next : cur.prev;
                    continue;
                }
            }

            sb.append(String.format("%-5s|", cur.toString()));
            cur = ltr ? cur.next : cur.prev;
        }

        sb.setLength(sb.length() - 1); // trim last "|"

        return sb.toString();
    }

    boolean matchingAnchors(Stitch st) {
        if (st == null) return false;
        Stitch other = ltr ? st.next : st.prev;
        return other != null
                && other.anchor != null
                && st.anchor == other.anchor;
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
            cur = cur.anchor.prev;
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
        if (head == tail) {
            // 1 element remaining, delete.
            head = tail = null;
        } else {
            ret.prev.next = null;
        }
        tail = tail.prev;
        ret.next = ret.prev = null;
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
