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
            sb.setLength(sb.length()-1); // trim last "|"
        }
        return sb.toString();
    }

    //todo: toStringExpanded
    public String toStringExpanded() {
        StringBuilder sb = new StringBuilder();
        // todo: refactor, D.R.Y.
        if (ltr) {
            Stitch cur = head;
            while (cur != null) {
                if (cur instanceof ShellStitch) {
                    Stitch shell = ((ShellStitch) cur).shell;
                    while (shell != null) {
                        sb.append(String.format("%-5s|", shell.toString()));
                        shell = shell.next;
                    }
                } else {
                    sb.append(String.format("%-5s|", cur.toString()));
                }
                cur = cur.next;
            }
        } else {
            Stitch cur = tail;
            while (cur != null) {
                if (cur instanceof ShellStitch) {
                    Stitch shell = ((ShellStitch) cur).shell;
                    while (shell != null) {
                        sb.append(String.format("%-5s|", shell.toString()));
                        // todo: print backwards? They're usually symmetric
                        shell = shell.next;
                    }
                } else {
                    sb.append(String.format("%-5s|", cur.toString()));
                }
                cur = cur.prev;
            }
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length()-1); // trim last "|"
        }
        return sb.toString();
    }

    void add(Stitch s) {
        if (head == null) {
            head = tail = s;
            return;
        }
        s.prev = tail;
        tail.next = s;
        tail = tail.next;
    }

    public Stitch pop() throws IllegalAccessException {
        Stitch ret = tail;
        if (tail == null) throw new IllegalAccessException("Row is empty");

        tail = tail.prev;
        if (tail != null)
            tail.next = null;

        return tail;
    }

    public Stitch peekLast() throws IllegalAccessException {
        if (tail == null) {
            throw new IllegalAccessException("Row is empty");
        }
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
