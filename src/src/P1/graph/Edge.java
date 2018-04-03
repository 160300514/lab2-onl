package src.P1.graph;

/**
 * TODO specification
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 *
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */

public class Edge<L> {

    // TODO fields
    private final L src;
    private final L tar;
    private final Integer wei;
    /**
     * Abstraction function:
     *      AF(Edge) = edge in graph
     * Representation invariant:
     *      true if edge in graph
     * Safety from rep exposure:
     *      private, new
     */
    /**
     * Edge(...)
     * @param: String(Label: src,tar), Integer: weight of an edge from src to tar
     * @method: constructor
     * @return: no
     */
    public Edge(L source, L target, Integer weight)
    {
        this.src = source;
        this.tar = target;
        this.wei = weight;
        checkRep();
    }
    /**
     * checkRep()
     * @param: \
     * @method: check if a rep invariant
     * @return: assert
     */
    private void checkRep()
    {
        assert this.wei >= 0 && ! this.src.equals(this.tar);
    }
    /**
     * checkExist(Edge)
     * @param: another Edge
     * @method: examine whether they're same
     * @return: true or false
     */
    public Boolean checkExist(Edge<L> other)
    {
        return other.src.equals(this.src) && other.tar.equals(this.tar);
    }
    /**
     * checkORExist(String)
     * @param: String
     * @method: examine whether this has
     * @return: true or false
     */
    public Boolean checkORExist(L other)
    {
        return other.equals(this.src) || other.equals(this.tar);
    }
    /**
     * edgeWeight()
     * @param: no
     * @method: \
     * @return: weight of this edge
     */
    public Integer edgeWeight()
    {
        return this.wei;
    }
    /**
     * edgeSource()
     * @param: no
     * @method: \
     * @return: String:src of this edge
     */
    public L edgeSource()
    {
        return this.src;
    }
    /**
     * edgeTarget()
     * @param: no
     * @method: \
     * @return: String:tar of this edge
     */
    public L edgeTarget()
    {
        return this.tar;
    }
    @Override public String toString()
    {
        return new String("Edge: source from="+this.src.toString()+" target to="+this.tar.toString()+" with weight="+this.wei.toString());
    }
}
