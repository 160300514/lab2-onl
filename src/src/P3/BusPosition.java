package src.P3;

//import javax.jnlp.IntegrationService;

public class BusPosition
{
    private final String BusName;
    private final String Latitude;
    private final String Longtitude;
    private final String StationName;
    private final Integer TimeScale;
    private final Integer hashCode;

    public BusPosition(String BusName, String StationName, String Latitude, String Longtitude, Integer Time, Integer hashCode)
    {
        this.BusName = BusName;
        this.Latitude = Latitude;
        this.Longtitude = Longtitude;
        this.StationName = StationName;
        this.TimeScale = Time;
        this.hashCode = hashCode;
    }

    public BusPosition(Position p, String BusName, Integer TimeScale, Integer hashCode)
    {
        this.BusName = BusName;
        this.Latitude = p.getLatitude();
        this.Longtitude = p.getLongtitude();
        this.StationName = p.getName();
        this.TimeScale = TimeScale;
        this.hashCode = hashCode;
    }

    public String getBusName()
    {
        return BusName;
    }

    public String getLatitude()
    {
        return this.Latitude;
    }

    public String getLongtitude()
    {
        return this.Longtitude;
    }

    public String getStationName()
    {
        return this.StationName;
    }

    public Integer getTimeScale()
    {
        return this.TimeScale;
    }

    @Override
    public boolean equals(Object obj)
    {
        BusPosition o = (BusPosition) obj;
        if(obj instanceof BusPosition)
        {
            return this.BusName.equals(o.BusName) && this.TimeScale.equals(o.TimeScale) && this.StationName.equals(o.StationName) && this.Latitude.equals(o.Latitude) && this.Longtitude.equals(o.Longtitude);
        }
        else
            return false;
    }

    @Override
    public String toString()
    {
        return this.BusName+"\tTime:\t"+this.TimeScale+"\t switch:"+this.StationName+"\t"+this.Latitude+"\t"+this.Longtitude;
    }
}
