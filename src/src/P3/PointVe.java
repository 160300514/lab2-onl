package src.P3;

public class PointVe
{
    private final Position Station_Loc;
    private final String BusName;
    private final Integer Layin_time;
    private final Boolean INOUT;//INOUT = true: IN, =false: OUT
    /*
    a class hold useful information, better than those class asked to implement.
        Rep field:
            private final Position Station_Loc;
            private final String BusName;
            private final Integer Layin_time;
            private final Boolean INOUT;
        methods are easy to think, just to get the unexposed varibles.

        The INOUT variable which is Boolean class, is designed to judge the to separated point.
        *******************************************************************************************8

        AF(PointVe) = {(bus, time, stop)| the bus stops @Stop @Time canmake sense}
        RI(PointVe): true if this point really exists.
        Rep Exposure: private, final, new.

     */
    public PointVe(Position Station, String Bus, Integer time, Boolean INOUT)
    {
        this.Station_Loc = Station;
        this.BusName = Bus;
        this.Layin_time = time;
        this.INOUT = INOUT;
    }
    public PointVe(PointVe pre, Boolean INOUT_CHANGED)
    {
        this.Layin_time = pre.getLayin_time();
        this.Station_Loc = pre.getStation_Loc();
        this.BusName = pre.getBusName();
        this.INOUT = INOUT_CHANGED;
    }
    public Position getStation_Loc()
    {
        return this.Station_Loc;
    }
    public String getBusName()
    {
        return this.BusName;
    }
    public Integer getLayin_time()
    {
        return this.Layin_time;
    }
    public Boolean getINOUT()
    {
        return this.INOUT;
    }
    @Override
    public String toString()
    {
        return "Staying point: bus: "+this.BusName+"\tStay @Station:\t"+this.Station_Loc.toString()+"\n\t\t\t@Time:\t"+this.Layin_time+"\n";
    }
}
