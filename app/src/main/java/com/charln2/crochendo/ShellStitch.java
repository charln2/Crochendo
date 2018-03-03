package com.charln2.crochendo;

public class ShellStitch extends Stitch {
    Stitch shell;

    public ShellStitch() {
        super("^^^");
    }

    protected ShellStitch(String note) {
        this();
        shell.next = null;
        shell.prev = null;
        this.note = note;
    }

    public void addShellStitch(Stitch s) {
        if (shell == null) {
            shell = s;
            return;
        }
        Stitch ins = shell;
        while (ins.next() != null) {
            ins = ins.next;
        }
        ins.next = s;
        s.prev = ins;
    }
}
