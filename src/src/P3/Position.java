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

    @Override
    public boolean equals(Object obj)
    {
        assert obj instanceof Position;
        Position p = (Position) obj ;
        return p.getName().equals(this.Name) && p.getLatitude().equals(this.getLatitude()) && p.getLongtitude().equals(this.Longtitude);
    }
}