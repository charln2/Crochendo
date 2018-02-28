package com.charln2.crochendo;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InstructionParsingUnitTest {
    private Pattern p;

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
    public void print_ch32() throws Exception {
        p.parseLine("ch 7");
        p.executeInstructions();

        assertEquals("sl st|ch   |ch   |ch   |ch   |ch   |ch   |ch   ", p.toString());
    }
    @Test(expected = InstantiationException.class)
    public void nonexistent_instruction() throws Exception {
        p.parseLine("zq");
    }
    @Test
    public void row() throws Exception {
        p.parseLine("ch 7");
        assertTrue(p.rowEnds.isEmpty());

        p.parseLine("Row 1 (RS):");
        p.executeInstructions();
        assertTrue(p.rowEnds.size() == 1);

        p.parseLine(" Dc in 4th ch from hook (beginning ch counts as dc)\n");
        p.executeInstructions();
        // todo: print by testing 0th and 1st row.
    }

    @Test
    public void double_crochet() throws Exception {
        p.parseLine("ch 7");
        p.parseLine("Row 1 (RS):");
        p.parseLine(" Dc in 4th ch from hook (beginning ch counts as dc)\n");
        p.executeInstructions();
        assertEquals("ch   |ch   |ch   |ch   |ch   |ch   |ch   |dc   ", p.toString());
        //todo: x location
    }



    @Test
    public void skip() throws Exception {
        p.parseLine("ch 7");
        p.parseLine("Row 1");
        p.parseLine(" Dc in 4th ch from hook (beginning ch counts as dc)\n");
        p.parseLine("sk 3 ch");
        p.executeInstructions();
        assertEquals("                         |dc   |sk   |sk   |sk   "
                            + "\nsl st|ch   |ch   |ch   |ch   |ch   |ch   |ch   ", p.toString());
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
                "Row 1 (RS): Dc in 4th ch from hook (beginning ch counts as dc), *sk next 2 ch, (3 dc, ch 1, 3 dc) in next ch, sk next 2 ch, dc in next 2 ch; repeat from * across, turn—4 pattern repeats.\n" +
                "Row 2: Ch 3 (counts as dc here and throughout), sk first dc, dc in next dc, *sk next 2 dc, dc in next dc, ch 1, (dc, ch 1, dc) in next ch-1 sp, ch 1, dc in next dc, sk next 2 dc, dc in next 2 dc; repeat from * across, turn.\n" +
                "Row 3: Ch 3, sk first dc, dc in next dc, *sk next ch-1 sp, (2 dc, ch 3, 2 dc) in next ch-1 sp, sk next ch-1 sp, sk next dc, dc in next 2 dc; repeat from * across, turn.\n" +
                "                                                                 \n" +
                "Row 4: Ch 3, sk first dc, dc in next dc, *(3 dc, ch 1, 3 dc) in next ch-3 sp, sk next 2 dc, dc in next 2 dc; repeat from * across, turn.\n" +
                "Repeat Rows 2–4 until piece measures about 58″/147.5cm. Do not fasten off.";
        Scanner sc = new Scanner(str);
        // commas note enclosed in parentheses or matching list of specific known delimiters
        sc.useDelimiter("(,(?![^()]*+\\)))|[:;.—]");
        while (sc.hasNext()) {
            System.out.println(sc.next());
        }
    }






}