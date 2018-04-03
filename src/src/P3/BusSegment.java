package src.P3;

public class BusSegment implements TripSegment
{
    private String Start;
    private String End;
    private Integer time;
    public void set(String src,String tar,Integer time)
    {
        this.Start = src;
        this.End = tar;
        this.time = time;
    }

    public String getSrc()
    {
        return this.Start;
    }

    public String getTar()
    {
        return this.End;
    }

    public Integer getTimeSpend()
    {
        return this.time;
    }
}
