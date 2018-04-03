/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package src.P1.graph;

//import javax.jnlp.IntegrationService;
import java.io.StringWriter;
import java.util.*;
import src.P1.graph.Pair;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex<L>> vertices;
    
    // Abstraction function:
    //   AF(List<>) = Graph{edge| edge(src,tar,wei)}
    // Representation invariant:
    //   true if each edge's weight > 0, vertices is not ambiguous
    // Safety from rep exposure:
    //   private, final, new
    
    // TODO constructor
    public ConcreteVerticesGraph()
    {
        vertices = new ArrayList<>();
        //System.out.println("ds");
        checkRep();
    }

    public ConcreteVerticesGraph(ConcreteVerticesGraph<L> other)
    {
        this.vertices = new ArrayList<>();
        this.vertices.addAll(other.FetchFromOther());
        checkRep();
    }
    // TODO checkRep
    private void checkRep()
    {
        //System.out.println("dds");
        assert vertices!=null;
        Iterator<Vertex<L>> it = vertices.iterator();
        while(it.hasNext())
        {
            Vertex<L> v = it.next();
            if(v == null|| v.masterOfVertex()==null)
            {
                if(v.targetSet().size()!=0)
                    assert true;
                else it.remove();
            }
        }
        //System.out.println("ddsas");
    }
    @Override public boolean add(L vertex)
    {
        //System.out.println("in add:");
        //System.out.println(vertex+"\n");
        checkRep();
        Iterator<Vertex<L>> it = vertices.iterator();
        while(it.hasNext())
        {
            Vertex<L> v = it.next();
            //System.out.println(v.toString());
            if(v.masterOfVertex().equals(vertex))
                return false;
        }
        vertices.add(new Vertex<>(vertex));
        return true;
    }
    
    @Override public int set(L source, L target, int weight)
    {
        //System.out.println("in set:\n");
        if(weight == 0)
        {
            for(Vertex<L> v:vertices)
            {
                if(v.masterOfVertex().equals(source))
                {
                    if(v.targetSet().contains(target))
                    {
                        Integer tmp = v.getWeight(target);
                        v.remove(target);
                        return tmp;
                    }
                }
            }
            return 0;
        }
        else if(weight > 0)
        {
            for(Vertex<L> v:vertices)
            {
                if(v.masterOfVertex().equals(source))
                {
                    return v.resetWeight(target, weight);
                }
            }
            vertices.add(new Vertex<>(source, target, weight));
            return 0;
        }
        return 0;
    }
    
    @Override public boolean remove(L vertex)
    {
        boolean flag = false;
        Iterator<Vertex<L>> it = vertices.iterator();
        while(it.hasNext())
        {
            Vertex<L> ve = it.next();
            if(ve.masterOfVertex().equals(vertex))
            {
                it.remove();
                flag = true;
            }
            else
            {
                if(ve.targetSet().contains(vertex))
                {
                    ve.remove(vertex);
                    flag = true;
                }
            }
        }
        return flag;
    }

    public boolean removeEdge(L VerFrom,L VerTo)
    {
        return set(VerFrom,VerTo,0) > 0;
    }
    
    @Override public Set<L> vertices()
    {
        Set<L> ans = new HashSet<>();
        for(Vertex<L> v:vertices)
        {
            ans.add(v.masterOfVertex());
            ans.addAll(v.targetSet());
        }
        return ans;
    }
    
    @Override public Map<L, Integer> sources(L target)
    {
        Map<L,Integer> ans = new HashMap<>();
        for(Vertex<L> v: vertices)
        {
            if(v.targetSet().contains(target))
            {
                ans.put(v.masterOfVertex(), v.getWeight(target));
            }
        }
        return ans;
    }
    
    @Override public Map<L, Integer> targets(L source)
    {
        for(Vertex<L> v:vertices)
        {
            if(v.masterOfVertex().equals(source))
            {
                return v.targetMap();
            }
        }
        return new HashMap<>();
    }
    
    // TODO toString()
    @Override public String toString()
    {
        //System.out.println("op:\n");
        StringWriter swt = new StringWriter();
        Integer cnt_zero = 0;
        //swt.write("Graph covers "+vertices.size()+" set of relations, with: \n");
        for(Vertex<L> v: vertices)
        {
            if(v.targetSet().size() == 0)
            {
                cnt_zero += 1;
                continue;
            }
            swt.write("Edge: from:"+v.masterOfVertex().toString()+"\tto:\n");
            for(L tar: v.targetSet())
            {
                swt.write("\t\t\t\t\t"+tar.toString()+"\tWith wei:"+v.getWeight(tar).toString()+"\n");
            }
        }
        String title = new String("Graph covers "+(vertices.size()-cnt_zero)+" set of relations, with: \n");
        return title+swt.toString();
    }
    public Integer getDistanceBetween(L o1,L o2)
    {
        Map<L, List<L> > mmp = new HashMap<>();
        for(Vertex<L> o: vertices)
        {
            List<L> ll = new LinkedList<>(o.targetSet());
            mmp.put(o.masterOfVertex(), ll);
        }
        List<L> vis = new LinkedList<>();
        Queue<Pair<L, Integer>> que = new LinkedList<>();
        Map<L, Integer> dis = new HashMap<>();
        que.add(new Pair<>(o1, 0));
        vis.add(o1);
        dis.put(o1, 0);
        while(!que.isEmpty())
        {
            Pair<L, Integer> p = que.poll();
            L now = p.getKey1();
            Integer curdis = p.getKey2()+1;
            for(L key: mmp.get(now))
            {
                if(!vis.contains(key))
                {
                    que.add(new Pair<>(key, curdis));
                    vis.add(key);
                    dis.put(key, curdis);
                }
            }
        }
        if(dis.keySet().contains(o2))
        {
            return dis.get(o2);
        }
        return -1;
    }
    public List<Vertex<L>> FetchFromOther()
    {
        return new ArrayList<>(this.vertices);
    }
}

