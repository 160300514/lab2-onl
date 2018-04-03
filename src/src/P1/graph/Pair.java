package src.P1.graph;

public class Pair<L1, L2>
{
    private  L1 k1;
    private L2 k2;
    public Pair(L1 key1,L2 key2)
    {
        this.k1= key1;
        this.k2= key2;
    }
    public L1 getKey1()
    {
        return this.k1;
    }
    public L2 getKey2()
    {
        return this.k2;
    }
}