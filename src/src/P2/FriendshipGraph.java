package src.P2;

import src.P1.graph.ConcreteVerticesGraph;


public class FriendshipGraph
{
    private final ConcreteVerticesGraph<Person> fg = new ConcreteVerticesGraph<>();
    /**
     * Abstraction function:
     *    AF(fg) = {edge(p1,p2)| p1 and p2 is friend}
     *
     * Representation invariant: checkRep
     *     RI(graph(vertices, edges)) == true
     * Safety from rep exposure:
     *     using copy, private final invariants to get rid of exposure
     */
    public FriendshipGraph()
    {
        checkRep();
    }
    private void checkRep()
    {
        assert fg != null;
    }
    public void addVertex(Person p)
    {
        fg.add(p);
    }
    public void addEdge(Person p1,Person p2)
    {
        fg.set(p1,p2,1);
    }
    public int getDistance(Person p1,Person p2)
    {
        return fg.getDistanceBetween(p1, p2);
    }
}
