package src.P3;

import javafx.geometry.Pos;
import src.P1.graph.Pair;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Liner
{
    private final String BusName;
    private final Map<Integer, Set<Pair<Position, Integer>>> BusTimePlan;
    //Map: Start time-> timetable

    /*
    *  Class: Liner
        Which describe a line a specified bus runs, it controls with a starttime
        which makes one bus is specified to one “Liner”.
        Rep:
            private final String BusName;
            private final Map<Integer, Set<Pair<Position, Integer>>> BusTimePlan;
        Methods:
            ddBusStopTime, toString(@Override)


        AF(Liner) = {t, stoper| time a bus stops.}
        Ri(Liner) whether is describes a bus line.
        REp expose: private new
     */


    public Liner(String BusName)
    {
        this.BusName = BusName;
        BusTimePlan = new HashMap<>();
    }

    public Liner(String BusName, Integer StartTime, String BusStopName, String Latitude, String Longtitude, Integer TimeStop1)
    {
        this.BusName = BusName;
        this.BusTimePlan = new HashMap<>();
        Set<Pair<Position,Integer>> sst = new HashSet<>();
        Position ppr = new Position(BusStopName,Latitude,Longtitude);
        sst.add(new Pair<>(ppr , TimeStop1));
        this.BusTimePlan.put(StartTime, sst);
    }

    public void addBusStopTime(Integer StartTime, String StopName, String Latitude, String Longtitude, Integer TimeStop)
    {
        Position ppe = new Position(StopName,Latitude,Longtitude);
        if(this.BusTimePlan.containsKey(StartTime))
        {
            this.BusTimePlan.get(StartTime).add(new Pair<>(ppe, TimeStop));
        }
        else
        {
            Set<Pair<Position,Integer>> tmp = new HashSet<>();
            tmp.add(new Pair<>(ppe, TimeStop));
            this.BusTimePlan.put(StartTime, tmp);
        }
    }

    public String getBusName()
    {
        return this.BusName;
    }

    public Map<Integer, Set<Pair<Position, Integer>>> getLineMap()
    {
        return new HashMap<>(this.BusTimePlan);
    }

    public boolean isEqual(String BusName)
    {
        return BusName.equals(this.BusName);
    }

    @Override
    public String toString()
    {
        StringWriter swt = new StringWriter();
        swt.write("BusTime"+this.BusName+"\n");
        for(Integer stt: this.BusTimePlan.keySet())
        {
            swt.write("StartTime= "+stt+"\n");
            for(Pair<Position, Integer> p:this.BusTimePlan.get(stt))
            {
                swt.write("\t\t with Point:"+p.getKey1().toString()+"Departtime="+p.getKey2()+"\n");
            }
        }
        return swt.toString();
    }
}

