package src.P3;


public class Pair3D<L1, L2, L3>
{
    private L1 o1;
    private L2 o2;
    private L3 o3;
    public Pair3D(L1 inito1, L2 inito2, L3 inito3)
    {
        this.o1 = inito1;
        this.o2 = inito2;
        this.o3 = inito3;
    }

    public L1 getO1() {
        return o1;
    }

    public L2 getO2() {
        return o2;
    }

    public L3 getO3() {
        return o3;
    }

    @Override
    public String toString()
    {
        return o1.toString()+"\t"+o2.toString()+"\t"+o3.toString();
    }
}
