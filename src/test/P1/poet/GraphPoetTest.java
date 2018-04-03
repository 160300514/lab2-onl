/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package test.P1.poet;

import static org.junit.Assert.*;

import src.P1.poet.GraphPoet;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
    // Testing strategy
    //   TODO
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled()
    {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // TODO tests
    @Test
    public void poemTest() throws IOException
    {
        GraphPoet gp = new GraphPoet(new File("src/src/P1/poet/mugar-omni-theater.txt"));
        String asn = gp.poem("Test the system.");
        System.out.println(asn);
        assertEquals("Test of the system.",asn);
    }
}
