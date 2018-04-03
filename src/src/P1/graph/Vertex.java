package src.P1.graph;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * TODO specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 *
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
public class Vertex<L> {

    // TODO fields
    private L source;
    private Map<L, Integer> ConnectValue = new HashMap<>();
    // Abstraction function:
    //   AF(Vertex) = {edge|edge(src=this.source, tar=one_key, weight=key_value}
    // Representation invariant:
    //   RI(Vertex) = true if all edges described in Vertex is in a graph
    // Safety from rep exposure:
    //   private, new

    // TODO constructor
    public Vertex(L src, Map<? extends L,? extends java.lang.Integer> c)
    {
        this.source = src;
        this.ConnectValue.putAll(c);
        checkRep();
    }
    public Vertex(L src)
    {
        this.source = src;
        this.ConnectValue.clear();
        this.checkRep();
    }
    public Vertex(L src,L tar,Integer wei)
    {
        assert wei > 0;
        this.source = src;
        this.ConnectValue.clear();
        this.ConnectValue.put(tar,wei);
        this.checkRep();
    }
    // TODO checkRep
    private void checkRep()
    {
        assert !ConnectValue.keySet().contains(this.source);
        for(L key:ConnectValue.keySet())
        {
            assert ConnectValue.get(key) > 0;
        }
    }
    public Map<L, Integer> targetMap()
    {
        return new HashMap<>(this.ConnectValue);
    }
    public boolean remove(L target)
    {
        if(this.targetSet().contains(target))
        {
            ConnectValue.remove(target);
            return true;
        }
        return false;
    }
    public Integer resetWeight(L target, Integer weight)//weight>0
    {
        assert weight > 0;
        if(this.ConnectValue.containsKey(target))
        {
            Integer tmp = this.ConnectValue.get(target);
            this.ConnectValue.replace(target, weight);
            return tmp;
        }
        this.ConnectValue.put(target, weight);
        return 0;
    }
    // TODO methods
    public L masterOfVertex()
    {
        return (this.source);
    }
    public Set<L> targetSet()
    {
        return this.ConnectValue.keySet();
    }
    public Integer getWeight(L target)
    {
        if(this.ConnectValue.containsKey(target))
        {
            return this.ConnectValue.get(target);
        }
        return 0;
    }
    public boolean edgeExists(L src,L tar, Integer wei)
    {
        return src.equals(this.source) && this.ConnectValue.keySet().contains(tar) && this.ConnectValue.get(tar).equals(wei);
    }
    // TODO toString()
    @Override public String toString()
    {
        StringWriter swt = new StringWriter();
        swt.write("src from: "+this.source+"\nWith edges are:\n");
        for(L key:ConnectValue.keySet())
        {
            swt.write("Edge: from src, to:"+key+"\twith weight="+ConnectValue.get(key)+"\n");
        }
        return swt.toString();
    }
}

