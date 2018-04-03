/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package src.P1.graph;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
    /**
     * Abstraction function:
     * AF(v1,v2) is in Graph, v1,v2 in vertices, (v1, v2, w) in edges
	 * @param: other Graph -> vertices, edges
     * @return: boolean
	 * Representation invariant: checkRep
	 *     RI(graph(vertices, edges)) == true
	 * Safety from rep exposure:
	 *     using copy, private final invariants to get rid of exposure
     */
    public ConcreteEdgesGraph(ConcreteEdgesGraph<L> other)
    {
    	vertices.addAll(other.vertices);
    	edges.addAll(other.edges);
    	checkRep();
    }
    public ConcreteEdgesGraph()
    {
        checkRep();
    }
    // checkRep
    private void checkRep()
    {
    	for(Edge<L> e: edges) {
			assert (vertices.contains(e.edgeSource()) && vertices.contains(e.edgeTarget()));
		}
    }
    /**
     * add(String vertex)
     * @param: String(Label)
     * @method: add to the set of vertices
     * @return: true if succeed
     */
    @Override public boolean add(L vertex) {

    	return vertices.add(vertex);
    }
    /**
     * set(String src, String tar, Integer wei)
     * @param: String(Label: src,tar), Integer: weight of an edge from src to tar
     * @method: add Edge to a set of edges, change a exist weight or create a new edge
     * @return: 0 if no edge before or has weight of 0, else return val of weight before
     */
    @Override public int set(L source, L target, int weight) {
        //throw new RuntimeException("not implemented");
    	Edge<L> e = new Edge<>(source, target, weight);
    	if(weight == 0)
    	{
    		Iterator<Edge<L>> it = edges.iterator();
    		while(it.hasNext())
    		{
    			Edge<L> ee = it.next();
    			if(e.equals(ee))
    			{
    				Integer val = ee.edgeWeight();
      				it.remove();
    				return val;
    			}
    		}
    		return 0;
    	}
    	else
    	{
    		if(!vertices.contains(source)) vertices.add(source);
    		if(!vertices.contains(target)) vertices.add(target);
    		Iterator<Edge<L>> it = edges.iterator();
    		boolean flag = true;
    		Integer valhold = 0;
    		while(it.hasNext())
    		{
    			Edge<L> ee = it.next();
    			if(ee.checkExist(e))
    			{
    				valhold = ee.edgeWeight();
    				it.remove();
    				flag = !flag;
    				break;
    			}
    		}
    		edges.add(e);
    		return valhold;
    	}
    }
    @Override public boolean remove(L vertex) {
        //throw new RuntimeException("not implemented");
    	Iterator<Edge<L>> it = edges.iterator();
    	boolean flag = false;
    	while(it.hasNext())
    	{
    		Edge<L> ee = it.next();
    		if(ee.checkORExist(vertex))
    		{
    			it.remove();
    			flag = true;
    		}
    	}
    	vertices.remove(vertex);
    	checkRep();
    	return flag;
    }
    
    @Override public Set<L> vertices() {
        //throw new RuntimeException("not implemented");
    	return vertices;
    }
    
    @Override public Map<L, Integer> sources(L target) {
        //throw new RuntimeException("not implemented");
    	Iterator<Edge<L>> it = edges.iterator();
    	@SuppressWarnings({ "rawtypes", "unchecked" })
		Map<L,Integer> ans = new HashMap();
    	while(it.hasNext())
    	{
    		Edge<L> e = it.next();
    		if(e.edgeTarget().equals(target))
    		{
    			ans.put(e.edgeSource(), e.edgeWeight());
    		}
    	}
    	return ans;
    }
    
    @Override public Map<L, Integer> targets(L source) {
        //throw new RuntimeException("not implemented");
    	Iterator<Edge<L>> it = edges.iterator();
    	@SuppressWarnings({ "rawtypes", "unchecked" })
		Map<L,Integer> ans = new HashMap();
    	while(it.hasNext())
    	{
    		Edge<L> e = it.next();
    		if(e.edgeSource().equals(source))
    		{
    			ans.put(e.edgeTarget(), e.edgeWeight());
    		}
    	}
    	return ans;
    }
    
    // TODO toString()
    @Override public String toString()
    {
    	StringWriter swt = new StringWriter();
    	swt.write("Graph covers "+vertices.size()+" points, with: \n");
    	for(L poi:vertices)
    	{
    		swt.write(poi.toString()+" ");
    	}
    	swt.write("\nIt owns "+edges.size()+" edges\n");
    	for(Edge<L> e:edges)
    	{
    		swt.write("Edge: from:"+e.edgeSource().toString()+"\t to:"+e.edgeTarget().toString()+"\t with:"+ e.edgeWeight().toString()+"\n");
    	}
    	return swt.toString();
    }
}
