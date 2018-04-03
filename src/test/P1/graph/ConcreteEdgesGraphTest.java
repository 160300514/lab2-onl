/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package test.P1.graph;

import static org.junit.Assert.*;

import org.junit.Test;

import src.P1.graph.ConcreteEdgesGraph;
import src.P1.graph.ConcreteVerticesGraph;
import src.P1.graph.Graph;
import src.P1.graph.Edge;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest<L> extends GraphInstanceTest {

    /*public static void main(String[] args)
    {
        Graph<String> g = new ConcreteVerticesGraph();
        g.add(src[0]);
        boolean add = g.add(tar[1]);
        g.set(src[0],tar[1],10);
        g.set(src[0],tar[0],2);
        g.set(src[1],tar[0],8);
        g.set(src[2],tar[3],67);
        g.set(src[2],tar[1],6);
        g.remove(src[0]);
        System.out.println(g.toString());
    }*/

    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<>();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //   TODO
    private static String src[] = {"a","aa","aaa","sad","white_album"};
    private static String tar[] = {"sa","adas","dsad","sd","la"};
    @Test
    public void toStringTest()
    {
        Graph<String> g = emptyInstance();
        g.add(src[0]);
        g.add(tar[1]);
        g.set(src[0],tar[1],10);
        g.set(src[0],tar[0],2);
        g.set(src[1],tar[0],8);
        g.set(src[2],tar[3],67);
        g.set(src[2],tar[1],6);
        g.remove(src[0]);
        String ans = "Graph covers 5 points, with: \naa aaa sd adas sa \nIt owns 3 edges\nEdge: from:aa\t to:sa\t with:8\nEdge: from:aaa\t to:sd\t with:67\nEdge: from:aaa\t to:adas\t with:6\n";
        System.out.println(g.toString());
        assertEquals(ans,g.toString());
    }
    

    @Test
    public void toALLTest()
    {
        Graph<String> g = emptyInstance();
        g.add(src[0]);
        g.add(tar[1]);
        g.set(src[0],tar[1],10);
        g.set(src[0],tar[0],2);
        g.set(src[1],tar[0],8);
        g.set(src[2],tar[3],67);
        g.set(src[2],tar[1],6);
        g.remove(src[0]);
        //String ans = "Graph covers 5 points, with: \naa aaa sd adas sa \nIt owns 3 edges\nEdge: from:aa\t to:sa\t with:8\nEdge: from:aaa\t to:sd\t with:67\nEdge: from:aaa\t to:adas\t with:6\n";
        System.out.println(g.toString());
        //assertEquals(ans,g.toString());
        assertEquals(5, g.vertices().size());
        assertEquals(0, g.sources(src[0]).size()+g.targets(src[0]).size());
    }
    /*
     * Testing Edge...
     */

    // Testing strategy for Edge
    @Test
    public void EdgeTest()
    {
        Edge<String> e = new Edge(src[0],tar[0],121);
        assertEquals("Edge: source from="+src[0]+" target to="+tar[0]+" with weight=121",e.toString());
    }
    private Integer srcc[] = {1,2,3,4,5,66,44};
    private Integer tarr[] = {213,214,414,313,432,433};
    @Test
    public void unsignedTest()
    {
        Graph<Integer> gg = new ConcreteEdgesGraph<>();
        gg.add(srcc[0]);
        gg.add(tarr[0]);
        gg.set(srcc[0],tarr[0],10);
        gg.set(srcc[0],tarr[1],12);
        gg.remove(tarr[0]);
        System.out.println(gg.toString());
        String ans = "Graph covers 2 points, with: \n1 214 \nIt owns 1 edges\nEdge: from:1\t to:214\t with:12\n";
        assertEquals(ans, gg.toString());
    }
    public void VertexTest()
    {
        //pass
    }
    public void testRemoveFT()
    {
        //pass
    }
}
