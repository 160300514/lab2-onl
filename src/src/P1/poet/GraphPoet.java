/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package src.P1.poet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import src.P1.graph.Graph;
import src.P1.graph.Pair;
/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {

    private final Graph<String> graph = Graph.empty();

    // Abstraction function:
    //   AF(graph<String) = graph:{edge|edge(src,tar,wei)}
    // Representation invariant:
    //   true if edge of graph is actually  exist in the graph
    // Safety from rep exposure:
    //   private ,new
    
    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException
    {
        //throw new RuntimeException("not implemented");

        Map<Pair<String,String>,Integer> mmp = new HashMap<>();
        Scanner scn = new Scanner(corpus);
        String previous = scn.next();
        while(scn.hasNext())
        {
            String ans = scn.next();
            Pair<String,String> p = new Pair<>(previous.toLowerCase(),ans.toLowerCase());
            if(mmp.keySet().contains(p))
                mmp.replace(p, mmp.get(p)+1);
            else
                mmp.put(p, 1);
            //System.out.println(ans);
            previous = ans;
        }
        for(Pair<String,String>p: mmp.keySet())
        {
            graph.set(p.getKey1(),p.getKey2(),mmp.get(p));
        }
        //System.out.println(mmp.size());
    }
    
    // TODO checkRep
    
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input)
    {
        //throw new RuntimeException("not implemented");
        String[]str = input.split(" ");
        String poems = "";
        poems += str[0];
        for(int i=0;i<str.length-1;i++)
        {
            Map<String, Integer> generated = new HashMap<>();
            Map<String, Integer> targets = graph.targets(str[i].toLowerCase());
            for(String s:targets.keySet())
            {
                //System.out.println(s);
                Map<String,Integer> tmp = graph.targets(s);
                for(String sr: tmp.keySet())
                {
                    if(sr.toLowerCase().equals(str[i+1].toLowerCase()))
                    {
                        generated.put(s, targets.get(s)+tmp.get(sr));
                    }
                }
            }
            if(generated.keySet().size() != 0)
            {
                String maxstr = "";
                Integer hold = -1;
                for(String mas: generated.keySet())
                {
                    if(generated.get(mas)>hold)
                    {
                        maxstr = mas;
                        hold = generated.get(mas);
                    }
                }
                poems += " "+maxstr;
            }
            else
            {
                //System.out.println("not get"+str[i]);
                //Integer xxxxxx = 1;

            }
            poems += " "+str[i+1];
        }
        return poems;
    }
    
    // TODO toString()
    public String toString()
    {
        return graph.toString();
    }
}