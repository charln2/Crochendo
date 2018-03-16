package com.charln2.crochendo;

import java.util.ArrayList;
import java.util.Collections;

public class Row {
    Stitch head, tail;
    private boolean ltr;
    private ArrayList<ArrayList<Stitch>> stitchGroups;


    Row() {
        ltr = true;
        head = tail = null;
        stitchGroups = new ArrayList<>();
    }

    Row(boolean ltr) {
        this();
        this.ltr = ltr;
    }

    boolean isLtr() {
        return ltr;
    }

    @Override
    public String toString() {
//        return toStringCompressed();
//        return "row";
        return toStringCompressed();
    }

    private String toStringCompressed() {
        if (isEmpty()) return "";
        ArrayList<String> workingList = new ArrayList<>();
        Stitch cur = head;
        while (cur != null) {
            //todo: handle stitchgroups and chaingroups
            if (cur.consecutiveChains(cur.next) || cur.sameAnchor(cur.next)) {
                cur = processPrintGroup(cur, workingList);
            } else {
                workingList.add(cur.toString());
                cur = cur.next;
            }
        }
        StringBuilder ret = new StringBuilder();
        for (int i = 0, j = workingList.size()-1; i < workingList.size(); i++, j--) {
            String s = (ltr) ? workingList.get(i) : workingList.get(j);
            ret.append(String.format("%-5s|", s));
        }

        if (!ltr) {
            for (ArrayList<Stitch> grp : stitchGroups) {
                Collections.reverse(grp);
            }
        }

        ret.setLength(ret.length() - 1); // trim last "|"
        return ret.toString();
    }

    private Stitch processPrintGroup(Stitch cur, ArrayList<String> workingList) {
        boolean isAllChains = true;
        stitchGroups.add(new ArrayList<Stitch>());
        ArrayList<Stitch> group = stitchGroups.get(stitchGroups.size()-1);

        do {
            if (!cur.toString().equalsIgnoreCase("ch")) isAllChains = false;
            group.add(cur);
            cur = cur.next;
        } while (cur != null &&
                (cur.toString().equals("ch") || cur.sameAnchor(cur.prev)));

        if (isAllChains) {
            int iLast = stitchGroups.size()-1;
            workingList.add("ch-"+stitchGroups.get(iLast).size());
            stitchGroups.remove(iLast);
        } else {

            workingList.add("^"+ stitchGroups.size() +"^"); // ith group
        }

        return cur;
    }

    String toStringExpanded() {
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
