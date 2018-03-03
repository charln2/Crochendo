package com.charln2.crochendo;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InstructionParsingUnitTest {
    private Pattern p;

    String printExpected(String[] arr) {
        StringBuilder sb = new StringBuilder();
        for(String s : arr) {
            sb.append(String.format("%-5s|", s));
        }
        sb.setLength(sb.length()-1);
        return sb.toString();
    }

    @Before
    public void setUp() throws Exception {
        p = new Pattern();
    }

    @Test(timeout=1000) // in ms
    //(expected = IllegalStateException.class)
    @Ignore ("Excluding example test given for reference. Just seeing how it works.")
    public void addition_isCorrect_example() throws Exception {
        assertEquals(4, 2 + 2);
        assertEquals(2, 1+1);
    }

    @Test
    public void empty_pattern() throws Exception {
        String[] expected = {"sl st"};
        assertEquals(printExpected(expected), p.toString());
    }

    @Test
    public void ch_11() throws Exception {
        p.parseLine("ch 11");
        p.executeInstructions();
        String[] e = {"sl st", "ch","ch","ch","ch","ch","ch","ch","ch","ch","ch","ch"};
        assertEquals(printExpected(e), p.toString());
    }
    @Test(expected = InstantiationException.class)
    public void nonexistent_instruction() throws Exception {
        p.parseLine("zq");
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

    @Test
    public void dc_4th_fr_hook() throws Exception {
        p.parseLine("ch 11, Row 1 (RS):");
        p.parseLine(" Dc in 4th ch from hook (beginning ch counts as dc)\n");
        p.executeInstructions();
        //todo: x location

        String[] r1 = {"","","","","","","","","dc"};
        String[] r0 = {"sl st", "ch","ch","ch","ch","ch","ch","ch","ch","ch","ch","ch"};
        StringBuilder sb = new StringBuilder();
        sb.append(printExpected(r1)).append('\n').append(printExpected(r0));
        assertEquals(sb.toString(), p.toString());
    }



    @Test
    public void skip() throws Exception {
        p.parseLine("ch 7");
        p.parseLine("RowInstruction 1");
        p.parseLine(" Dc in 4th ch from hook (beginning ch counts as dc)\n");
        p.parseLine("sk 3 ch");
        p.executeInstructions();
        assertEquals("                         |dc   |sk   |sk   |sk   "
                            + "\nsl st|ch   |ch   |ch   |ch   |ch   |ch   |ch   ", p.toString());
    }

    @Test(expected = NoClassDefFoundError.class)
    public void anchor_not_found() throws Exception {
        p.parseLine("ch 7");
        p.parseLine("RowInstruction 1");
        p.parseLine(" Dc in 4th ch from hook (beginning ch counts as dc)\n");
        p.parseLine("sk 2 zfakeAnchor");
        p.executeInstructions();
    }

    @Test
    @Ignore ("hold not yet implemented")
    public void hold() throws Exception {



//        Scanner sc = new Scanner("*stitch with asterisk");
//        StringBuilder sb = new StringBuilder("*stitch with asterisk");
//        sb.replace(0,1, "");
//        System.out.println(sb.toString());
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
                "RowInstruction 1 (RS): Dc in 4th ch from hook (beginning ch counts as dc), *sk next 2 ch, (3 dc, ch 1, 3 dc) in next ch, sk next 2 ch, dc in next 2 ch; repeat from * across, turn—4 pattern repeats.\n" +
                "RowInstruction 2: Ch 3 (counts as dc here and throughout), sk first dc, dc in next dc, *sk next 2 dc, dc in next dc, ch 1, (dc, ch 1, dc) in next ch-1 sp, ch 1, dc in next dc, sk next 2 dc, dc in next 2 dc; repeat from * across, turn.\n" +
                "RowInstruction 3: Ch 3, sk first dc, dc in next dc, *sk next ch-1 sp, (2 dc, ch 3, 2 dc) in next ch-1 sp, sk next ch-1 sp, sk next dc, dc in next 2 dc; repeat from * across, turn.\n" +
                "                                                                 \n" +
                "RowInstruction 4: Ch 3, sk first dc, dc in next dc, *(3 dc, ch 1, 3 dc) in next ch-3 sp, sk next 2 dc, dc in next 2 dc; repeat from * across, turn.\n" +
                "Repeat Rows 2–4 until piece measures about 58″/147.5cm. Do not fasten off.";
        Scanner sc = new Scanner(str);
        // commas note enclosed in parentheses or matching list of specific known delimiters
        sc.useDelimiter("(,(?![^()]*+\\)))|[:;.—]");
        while (sc.hasNext()) {
            System.out.println(sc.next());
        }
    }






}