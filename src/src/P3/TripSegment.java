package src.P3;

public interface TripSegment
{
    public void set(String src,String tar,Integer time);

    public String getSrc();

    public String getTar();

    public Integer getTimeSpend();
}
