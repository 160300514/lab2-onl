package src.P3;

import src.P1.graph.Pair;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Stoper implements Stop
{
    private final String StopName;
    private final String Latitude;
    private final String Longtitude;
    private final List< Pair<String, Integer> > BusTimeTable  = new ArrayList<>();//bus -> time: in sec
    private Boolean Updated;
    private final Integer maxWaitTime;

    /*
    *   Rep:
    private final String StopName;
    private final String Latitude;
    private final String Longitude;
    private final List< Pair<String, Integer> > BusTimeTable  = new ArrayList<>();//bus -> time: in sec
    private Boolean Updated;
    private final Integer maxWaitTime;
    These are  rep in Stoper, in short, it gives a detailed info about one stop:
    its name, latitude, longitude, and the bustimetable of the bus coming to the
    stop and stop,Updated is a private value represents the arrange of whether
    the BusTimeTable is sorted, which uses Collections.sort() method.

    AF: AF(Stoper()) -> Stop with buslines, bus time table.
    RI:whether a busline stop at this stop.
    rep expose: private, new
     */
    public Stoper(String StopName, String Latitude, String Longtitude, Integer maxWaitTime)
    {
        this.StopName = StopName;
        this.Longtitude = Longtitude;
        this.Latitude = Latitude;
        this.maxWaitTime = maxWaitTime;
        this.Updated = false;
    }

    public Stoper(String StopName, String Latitude, String Longtitude)
    {
        this.StopName = StopName;
        this.Longtitude = Longtitude;
        this.Latitude = Latitude;
        this.maxWaitTime = 1200;
        this.Updated = false;
    }

    public Stoper(String BusName, String StopName, String Latitude, String Longtitude, Integer VisitTime)
    {
        this.StopName = StopName;
        this.Latitude = Latitude;
        this.Longtitude = Longtitude;
        this.BusTimeTable.add(new Pair<>(BusName, VisitTime));
        this.maxWaitTime = 1200;
        this.Updated = false;
    }

    public Stoper(String BusName, String StopName, String Latitude, String Longtitude, Integer VisitTime, Integer maxWaitTime)
    {
        this.StopName = StopName;
        this.Latitude = Latitude;
        this.Longtitude = Longtitude;
        this.BusTimeTable.add(new Pair<>(BusName, VisitTime));
        this.maxWaitTime = maxWaitTime;
        this.Updated = false;
    }

    public void addBusTime(String BusName, Integer StopTime)
    {
        this.BusTimeTable.add(new Pair<>(BusName, StopTime));
        this.Updated = false;
    }

    public String getName()
    {
        return StopName;
    }

    public String getLatitude()
    {
        return this.Latitude;
    }

    public String getLongitude()
    {
        return this.Longtitude;
    }

    public List<Pair<String,Integer>> getBusTimeTable()
    {
        return new ArrayList<>(this.BusTimeTable);
    }

    public Boolean isEqual(String LatitudeComp, String LongtitudeComp, String StopNameComp)
    {
        return this.Latitude.equals(LatitudeComp) && this.Longtitude.equals(LongtitudeComp) && this.StopName.equals(StopNameComp);
    }

    private void Pair_Arrange_ascent()
    {
        this.Updated = true;
        Collections.sort(this.BusTimeTable, new Comparator<Pair<String, Integer>>() {
            @Override
            public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
                return o1.getKey2().compareTo(o2.getKey2());
            }
        });
    }

    public List<Pair<String, Integer>> anyAbleToCatch(Integer ArriveTime)
    {
        List<Pair<String, Integer>> ans = new ArrayList<>();
        System.out.println(this.StopName+"\t"+this.Updated);
        if(this.Updated==false)
            Pair_Arrange_ascent();
        for(Pair<String, Integer> p: this.BusTimeTable)
        {
            Integer timeac = (p.getKey2() - ArriveTime);
            if(timeac >= 0 && timeac <= this.maxWaitTime)
            {
                ans.add(p);
            }
        }
        return ans;
    }

    @Override
    public String toString()
    {
        /*
         *private final String StopName;
         *private final String Latitude;
         *private final String Longtitude;
         *private final List< Pair<String, Integer> > BusTimeTable  = new ArrayList<>();//in sec
         *private Boolean Updated;
         *private final Integer maxWaitTime;
         */
        StringWriter swt = new StringWriter();
        swt.write("Stop: "+this.StopName+"\tPosition: "+Latitude+","+Longtitude+"\tWait"+maxWaitTime+"\n");
        swt.write("Stopped bus=\n");
        for(Pair<String, Integer> p : this.BusTimeTable)
        {
            swt.write("Bus Name:\t"+p.getKey1()+"\tTime:\t"+p.getKey2()+"\n");
        }
        return swt.toString();
    }
}
