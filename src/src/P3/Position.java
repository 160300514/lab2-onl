package src.P3;

class Position
{
    private final String Name;
    private final String Latitude;
    private final String Longtitude;

    public Position(String Name, String Latitude, String Longtitude)
    {
        this.Name = Name;
        this.Latitude = Latitude;
        this.Longtitude = Longtitude;
    }

    public String getName()
    {
        return Name;
    }

    public String getLatitude()
    {
        return this.Latitude;
    }

    public String getLongtitude()
    {
        return this.Longtitude;
    }

    @Override
    public String toString()
    {
        return this.Name+"\t switch:"+this.Latitude+"\t"+this.Longtitude;
    }
}