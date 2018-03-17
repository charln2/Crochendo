package com.charln2.crochendo;

import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class InstructionParsingUnitTest {
    private Pattern p;
    private static final String stFmt = "%-5s|";

    String printExpected(String[] arr) {
        StringBuilder sb = new StringBuilder();
        for(String s : arr) {
            sb.append(String.format(stFmt, s));
        }
        sb.setLength(sb.length()-1);
        return sb.toString();
    }

    @Before
    public void setUp() throws Exception {
        p = new Pattern();
    }

    @Test
    public void empty_pattern() throws Exception {
        String[] expected = {"sl st"};
        String actual = p.toString();
        assertEquals(printExpected(expected), actual);
    }
    @Test
    public void ch_11() throws Exception {
        p.parseLine("ch 11");
        p.executeInstructions();
        String[] e = {"sl st", "ch-11"};
        String actual = p.toString();
        assertEquals(printExpected(e), actual);
    }
    @Test
    public void ch_11_expanded() throws Exception {
        p.parseLine("ch 11");
        p.executeInstructions();
        String[] e = {"sl st", "ch","ch","ch","ch","ch","ch","ch","ch","ch","ch","ch"};
        assertEquals(printExpected(e), p.getRow(0).toStringExpanded());
    }
    @Test(expected = InstantiationException.class)
    public void nonexistent_instruction() throws Exception {
//        try {
            p.parseLine("zq");
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }
    }
    @Test
    public void row_1st() throws Exception {
        // todo: print by testing 0th and 1st row.
        p.parseLine("ch 11, Row 1 (RS):");
        assertTrue(p.rows.size() == 1);
        p.executeInstructions();
        assertTrue(p.rows.size() == 2);

//        p.parseLine(" Dc in 4th ch from hook (beginning ch counts as dc)\n");
//        p.executeInstructions();
    }

    /**
     * padding = 8
     *      |     |     |     |     |     |     |     |dc
     * sl st|ch   |ch   |ch   |ch   |ch   |ch   |ch   |ch   |ch   |ch   |ch
     * sl st|ch-11
     * @throws Exception
     */
    @Test
    public void dc_4th_fr_hook() throws Exception {
        p.parseLine("ch 11, Row 1 (RS):");
        p.parseLine(" Dc in 4th ch from hook\n");
        p.executeInstructions();
        //todo: x location

        String[] r1 = {"","","","","","","","","dc"};
        String[] r0 = {"sl st", "ch","ch","ch","ch","ch","ch","ch","ch","ch","ch","ch"};
        StringBuilder sb = new StringBuilder();
        sb.append(printExpected(r1)).append('\n').append(printExpected(r0));
        String row = p.printRow(1);
        assertEquals(sb.toString(), row);
    }

    @Test
    public void chain_group() throws Exception {
        p.parseLine("ch 11, Row 1 (RS):");
        p.executeInstructions();
        p.parseLine(" Dc in 4th ch from hook (beginning ch counts as dc)\n");
        p.executeInstructions();

        String[] r1 = {"","","","","","","","","dc","ch-3"};
        String[] r0 = {"sl st", "ch","ch","ch","ch","ch","ch","ch","ch"};
        StringBuilder sb = new StringBuilder();
        sb.append(printExpected(r1)).append('\n').append(printExpected(r0));
        assertEquals(sb.toString(), p.printRow(1));
    }

    @Test
    public void skip() throws Exception {
        p.parseLine("ch 11");
        p.parseLine("Row 1");
        p.parseLine(" Dc in 4th ch from hook (beginning ch counts as dc)\n");
        p.parseLine("sk 3 ch");
        p.executeInstructions();


        String[] r1 = {"","","","","","sk","sk","sk","dc","ch-3"};
        String[] r0 = {"sl st", "ch","ch","ch","ch","ch","ch","ch","ch"};
        StringBuilder sb = new StringBuilder();
        sb.append(printExpected(r1)).append('\n').append(printExpected(r0));
        assertEquals(sb.toString(), p.printRow(1));
    }

    @Test(expected = NoClassDefFoundError.class)
    public void anchor_not_found() throws Exception {
        p.parseLine("ch 7");
        p.parseLine("Row 1");
        p.parseLine(" Dc in 4th ch from hook (beginning ch counts as dc)\n");
        p.parseLine("sk 2 zfakeAnchor");
        p.executeInstructions();
    }

    @Test
    public void hold_skip() throws Exception {
        // row 1
        // sl st
        p.parseLine("ch 11");
        p.parseLine("Row 1");
        p.parseLine(" Dc in 4th ch from hook (beginning ch counts as dc)\n");
        p.parseLine("*sk next 2 ch");
        p.executeInstructions();


        String[] r1 = {"","","","","","","sk","sk","dc","ch-3"};
        String[] r0 = {"sl st", "ch","ch","ch","ch","ch","ch","ch","ch"};
        StringBuilder sb = new StringBuilder();
        sb.append(printExpected(r1)).append('\n').append(printExpected(r0));
        assertEquals(sb.toString(), p.printRow(1));
        assertSame(6, p.hold.get("*"));
        Stitch check = p.rows.get(0).tail.prev;
        int i = 0;
        while (check != null) {
            i++;
            check = check.next;
        }
        assertEquals(2, i);
    }

    @Test
    public void shell_print() throws Exception {
        p.parseLine("ch 11");
        p.parseLine("Row 1");
        p.parseLine(" Dc in 4th ch from hook (beginning ch counts as dc)\n");
        p.parseLine("*sk next 2 ch");
        p.parseLine(" (3 dc, ch 1, 3 dc) in next ch");
        p.executeInstructions();


        String[] r1 = {"","","","","","^1^","sk","sk","dc","ch-3"};
        String[] r0 = {"sl st", "ch","ch","ch","ch","ch","ch","ch","ch"};
        StringBuilder sb = new StringBuilder();
        sb.append(printExpected(r1)).append('\n').append(printExpected(r0));
        assertEquals(sb.toString(), p.printRow(1));
    }

    @Test
    public void print_shell_expanded() throws Exception {
        p.parseLine("ch 11");
        p.parseLine("Row 1");
        p.parseLine(" Dc in 4th ch from hook (beginning ch counts as dc)\n");
        p.parseLine("*sk next 2 ch");
        p.parseLine(" (3 dc, ch 1, 3 dc) in next ch");
        p.executeInstructions();

        String[] r1 = {"dc","dc","dc","ch","dc","dc","dc","dc","ch-3"};
        StringBuilder sb = new StringBuilder();
        sb.append(printExpected(r1));
        assertEquals(sb.toString(), p.getRow(1).toStringExpanded());
    }

    @Test
    public void repeat_group_1() throws Exception {
        p.parseLine("ch 11");
        p.parseLine("Row 1");
        p.parseLine(" Dc in 4th ch from hook (beginning ch counts as dc)\n");
        p.parseLine("*sk next 2 ch");
        p.parseLine(" (3 dc, ch 1, 3 dc) in next ch");
        p.parseLine(" sk next 2 ch");
        p.parseLine("dc in next 2 ch");
        p.executeInstructions();

        String[] r1 = {"","dc","dc","sk","sk","^1^","sk","sk","dc","ch-3"};
        String[] r0 = {"sl st", "ch","ch","ch","ch","ch","ch","ch","ch"};
        StringBuilder sb = new StringBuilder();
        sb.append(printExpected(r1)).append('\n').append(printExpected(r0));
        assertEquals(sb.toString(), p.printRow(1));

    }

    @Test
    public void turn() throws Exception {
        p.parseLine("ch 11");
        p.parseLine("Row 1");
        p.parseLine(" Dc in 4th ch from hook (beginning ch counts as dc)\n");
        p.parseLine("*sk next 2 ch");
        p.parseLine(" (3 dc, ch 1, 3 dc) in next ch");
        p.parseLine(" sk next 2 ch");
        p.parseLine("dc in next 2 ch");
        p.parseLine("turn");
        p.executeInstructions();

        String[] r1 = {"turn","dc","dc","sk","sk","^1^","sk","sk","dc","ch-3"};
        String[] r0 = {"sl st", "ch","ch","ch","ch","ch","ch","ch","ch"};
        StringBuilder sb = new StringBuilder();
        sb.append(printExpected(r1)).append('\n').append(printExpected(r0));
        assertEquals(sb.toString(), p.printRow(1));
    }

    @Test
    public void repeat_row1() throws Exception {
        p.parseLine("ch 32");
        p.parseLine("Row 1");
        p.parseLine(" Dc in 4th ch from hook (beginning ch counts as dc)\n");
        p.parseLine("*sk next 2 ch");
        p.parseLine(" (3 dc, ch 1, 3 dc) in next ch");
        p.parseLine(" sk next 2 ch");
        p.parseLine(" dc in next 2 ch");
        p.parseLine(" repeat from * across");
        p.parseLine("turn");
        p.executeInstructions();

        String[] r1 = {"turn","dc","dc","sk","sk","^4^","sk","sk","dc","dc","sk","sk","^3^","sk","sk","dc","dc","sk","sk","^2^","sk","sk","dc","dc","sk","sk","^1^","sk","sk","dc","ch-3"};
        String[] r0 = {"sl st", "ch","ch","ch","ch","ch","ch","ch","ch","ch","ch","ch","ch","ch","ch","ch","ch","ch","ch","ch","ch","ch","ch","ch","ch","ch","ch","ch","ch","ch"};
        StringBuilder sb = new StringBuilder();
        sb.append(printExpected(r1)).append('\n').append(printExpected(r0));
        assertEquals(sb.toString(), p.printRow(1));
    }

    @Test
    public void row2() throws Exception {
        p.parsePattern("Ch 32\n" +
                        "\n" +
                        "Row 1 (RS)\n" +
                        " Dc in 4th ch from hook (beginning ch counts as dc)\n" +
                        " *sk next 2 ch\n" +
                        " (3 dc, ch 1, 3 dc) in next ch\n" +
                        " sk next 2 ch\n" +
                        " dc in next 2 ch\n" +
                        " repeat from * across\n" +
                        " turn—4 pattern repeats\n" +
                        "\n" +
                        "Row 2\n" +
                        " Ch 3 (counts as dc here and throughout)\n" +
                        " sk first dc\n" +
                        " dc in next dc\n" +
                        " *sk next 2 dc\n" +
                        " dc in next dc\n" +
                        " ch 1\n" +
                        " (dc, ch 1, dc) in next ch-1 sp\n" +
                        " ch 1\n"
                        + " dc in next dc\n"
                        + " sk next 2 dc\n"
                        + " dc in next 2 dc\n"
                        + " repeat from * across\n"
//                        + " turn"
        );
        String[] r2 = {"sl st"};
        String[] r1 = {"sl st"};
        StringBuilder sb = new StringBuilder();
        sb.append(printExpected(r2)).append('\n').append(printExpected(r1));
        String actual = p.printRow(2);
        assertEquals(sb.toString(), actual);
    }

    // --- Small Experiments ---

    @Test
    public void scanner() throws Exception {
        Scanner sc = new Scanner("Some text (with text in braces),");
        String braces = sc.findInLine("\\(.*\\)");
        System.out.println(braces.replaceAll("[()]", ""));
        assertEquals("(with text in braces)", braces);

        sc = new Scanner("text without braces");
        String mystery = sc.findInLine("\\(.*\\)");
        System.out.println(mystery);
    }
    @Test
    public void scan() throws Exception {
        String str = "Ch 32.\n" +
                "Row 1 (RS): Dc in 4th ch from hook (beginning ch counts as dc), *sk next 2 ch, (3 dc, ch 1, 3 dc) in next ch, sk next 2 ch, dc in next 2 ch; repeat from * across, turn—4 pattern repeats.\n" +
                "Row 2: Ch 3 (counts as dc here and throughout), sk first dc, dc in next dc, *sk next 2 dc, dc in next dc, ch 1, (dc, ch 1, dc) in next ch-1 sp, ch 1, dc in next dc, sk next 2 dc, dc in next 2 dc; repeat from * across, turn.\n" +
                "Row 3: Ch 3, sk first dc, dc in next dc, *sk next ch-1 sp, (2 dc, ch 3, 2 dc) in next ch-1 sp, sk next ch-1 sp, sk next dc, dc in next 2 dc; repeat from * across, turn.\n" +
                "                                                                 \n" +
                "Row 4: Ch 3, sk first dc, dc in next dc, *(3 dc, ch 1, 3 dc) in next ch-3 sp, sk next 2 dc, dc in next 2 dc; repeat from * across, turn.\n" +
                "Repeat Rows 2–4 until piece measures about 58″/147.5cm. Do not fasten off.";
        Scanner sc = new Scanner(str);
        // commas note enclosed in parentheses or matching list of specific known delimiters
        sc.useDelimiter("(,(?![^()]*+\\)))|[:;.]");
        while (sc.hasNext()) {
            System.out.println(sc.next());
        }
    }
}