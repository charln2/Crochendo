package com.charln2.crochendo;

public class ShellStitch extends Stitch {
    Stitch shell;

    public ShellStitch() {
        super("^^^");
    }

    protected ShellStitch(String note) {
        this();
        this.note = note;
    }

    public void addShellStitch(Stitch s) {
        if (shell == null) {
            shell = s;
        }
        shell.next = s;
        s.prev = shell;
    }
}
