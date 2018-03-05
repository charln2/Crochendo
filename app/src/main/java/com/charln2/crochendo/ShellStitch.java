package com.charln2.crochendo;

public class ShellStitch extends Stitch {
    Stitch shell = null;

    public ShellStitch() {
        super("^^^");
    }

    public ShellStitch(String note) {
        this();
        this.note = note;
    }

    public void addShellStitch(Stitch st) {
        if (shell == null) {
            shell = st;
            return;
        }
        Stitch ins = shell;
        while (ins.next != null) {
            ins = ins.next;
        }
        ins.next = st;
        st.prev = ins;
    }
}
