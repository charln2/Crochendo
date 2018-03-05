package com.charln2.crochendo;

/**
 * Created by Ripley on 3/1/2018.
 */

public class Row {
    Stitch head, tail = null;
    private boolean ltr = true;

    public Row() {
    }

    public Row(boolean ltr) {
        this.ltr = ltr;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // todo: refactor, D.R.Y.
        if (ltr) {
            Stitch cur = head;
            while (cur != null) {
                sb.append(String.format("%-5s|", cur.toString()));
                cur = cur.next;
            }
        } else {
            Stitch cur = tail;
            while (cur != null) {
                sb.append(String.format("%-5s|", cur.toString()));
                cur = cur.prev;
            }
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1); // trim last "|"
        }
        return sb.toString();
    }

    public String toStringExpanded() {
        StringBuilder sb = new StringBuilder();
        // todo: refactor, D.R.Y.
        if (ltr) {
            Stitch cur = head;
            while (cur != null) {
                if (!cur.name.equals("sk")) {
                    if (cur instanceof ShellStitch) {
                        Stitch shell = ((ShellStitch) cur).shell;
                        while (shell != null) {
                            sb.append(String.format("%-5s|", shell.toString()));
                            shell = shell.next;
                        }
                    } else {
                        sb.append(String.format("%-5s|", cur.toString()));
                    }
                }
                cur = cur.next;
            }
        } else {
            Stitch cur = tail;
            while (cur != null) {
                if (!cur.name.equals("sk")) {
                    if (cur instanceof ShellStitch) {
                        Stitch shell = ((ShellStitch) cur).shell;
                        while (shell != null) {
                            sb.append(String.format("%-5s|", shell.toString()));
                            shell = shell.next;
                        }
                    } else {
                        sb.append(String.format("%-5s|", cur.toString()));
                    }
                }
                cur = cur.prev;
            }
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1); // trim last "|"
        }
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
