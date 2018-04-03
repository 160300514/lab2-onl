/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package test.P1.graph;

import static org.junit.Assert.*;

import src.P1.graph.ConcreteVerticesGraph;

import org.junit.Test;

import src.P1.graph.Graph;
import src.P1.graph.Vertex;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    /*public static void main(String[] args)
    {
        ConcreteVerticesGraph g = new ConcreteVerticesGraph();
        //System.out.println(g.toString());
        System.out.println(src[0]);
        g.add(src[0]);
        System.out.println(src[0]);
        //g.remove(src[0]);
        System.out.println(g.toString());
    }*/
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph<>();
    }
    private static String src[] = {"a","aa","aaa","sad","white_album"};
    private static String tar[] = {"sa","adas","dsad","sd","la"};
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    @Test
    public void toStringTest()
    {
        Graph<String> g= new ConcreteVerticesGraph<>();
        g.add(src[0]);
        g.set(src[0],tar[1],11);
        g.set(src[0],tar[0],12);
        g.set(tar[0],src[0],23);
        g.set(tar[2],src[1],12);
        g.set(tar[1],src[3],123);
        g.set(src[4],tar[1],1);
        g.set(src[3],tar[0],3);
        g.remove(tar[0]);
        g.remove(src[0]);
        g.remove(src[1]);
        System.out.println(g.toString());
        assertEquals("Graph covers 2 set of relations, with: \nEdge: from:adas\tto:\n\t\t\t\t\tsad\tWith wei:123\nEdge: from:white_album\tto:\n\t\t\t\t\tadas\tWith wei:1\n", g.toString());
    }
    

    
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    @Test
    public void VertexTest()
    {
        Vertex<String> tester = new Vertex(src[0]);
        tester.resetWeight(tar[0],12);
        tester.resetWeight(tar[1],32);
        tester.remove(tar[0]);
        System.out.println(tester.toString());
        assertEquals("src from: a\nWith edges are:\nEdge: from src, to:adas\twith weight=32\n",tester.toString());
    }

    private Integer srcc[] = {1,2,3,4,5,66,44};
    private Integer tarr[] = {213,214,414,313,432,433};

    @Test
    public void unsignedTest()
    {
        Graph<Integer> gg = new ConcreteVerticesGraph<>();
        gg.add(srcc[0]);
        gg.add(tarr[0]);
        gg.set(srcc[0],tarr[0],10);
        gg.set(srcc[0],tarr[1],12);
        gg.remove(tarr[0]);
        System.out.println(gg.toString());
        String ans = "Graph covers 1 set of relations, with: \nEdge: from:1\tto:\n\t\t\t\t\t214\tWith wei:12\n";
        assertEquals(ans, gg.toString());
    }

    @Test
    public void testRemoveFT()
    {
        ConcreteVerticesGraph<String> gg = new ConcreteVerticesGraph<>();
        gg.set("we","ssr",0);
        gg.set("we","sss",2);
        //gg.removeEdge("we","ssr");
        gg.set("we","ssr",0);
        System.out.println(gg.toString());
    }

    public void toALLTest()
    {

    }
}