package com.charln2.crochendo;

import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InstructionParsingUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        assertEquals(2, 1+1);
    }

    @Test
    public void print_ch32() throws Exception {
        Pattern p = new Pattern();
        p.parseLine("ch 5");
        p.executeInstructions();

        assertEquals("sl st|ch   |ch   |ch   |ch   |ch   ", p.toString());
    }

    @Test
    public void print_row() throws Exception {
        Pattern p = new Pattern();
        p.parseLine("ch 5");
        assertFalse(p.isNewRow());

        p.parseLine("Row 1 (RS):");
        p.executeInstructions();
        assertTrue(p.isNewRow());
    }

    @Test
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