package com.charln2.crochendo;

import org.junit.Test;

import static org.junit.Assert.*;

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

        assertEquals(p.toString(), "sl st|ch   |ch   |ch   |ch   |ch   ");
    }
}