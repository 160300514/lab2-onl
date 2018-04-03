package src.P3;

import org.junit.runner.notification.RunListener;
import src.P1.graph.ConcreteVerticesGraph;
import src.P1.graph.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Time;
import java.util.*;

public class BuildAdapter implements RoutePlannerBuilder
{
    private Set<Stoper> stopList;
    private Map<String, Liner> Buslines;
    private final ConcreteVerticesGraph<String> trafficDesigner;
    //private Map<BusPosition, String> NameHash;
    /*
    Class: BuildAdapter implements RoutePlannerBuilder
        Rep:
                private Set<Stoper> stopList;
                private Map<String, Liner> Buslines;
                private final ConcreteVerticesGraph<String> trafficDesigner;
        BuildAdapter reads from the filename provided, the interface is:
                Build(String FileName, Integer maxWaitTime)
        And it finally returns a implementation of RoutePlanner class, and can use this method to initialize the RoutePlanner.

        Af(StopList, Buslines, trafficDesigner) = {graph:edge,vertices| from file: edge(v1, v2)}
        RI(stoplist): true if exist.
        Rep Exposure:
            Private, new, final

     */
    public BuildAdapter()
    {
        this.stopList = new HashSet<>();
        this.trafficDesigner = new ConcreteVerticesGraph<>();
        this.Buslines = new HashMap<>();
        //this.NameHash = new HashMap<>();
    }

    public PathFinder build(String fileName, Integer maxWaitTime) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(fileName));
        while(sc.hasNext())
        {
            String Liness = sc.nextLine();
            //System.out.println(Liness);
            String[] FirstLine = Liness.split(",");
            String NameOfBus = FirstLine[0];
            Integer lasts = Integer.parseInt(FirstLine[1]);
            boolean containBusLine = false;
            for(String str : this.Buslines.keySet())
            {
                if(str.equals(NameOfBus))
                {
                    containBusLine = true;
                    break;
                }
            }
            if(!containBusLine)
            {
                this.Buslines.put(NameOfBus, new Liner(NameOfBus));
            }
            Integer Starttime = 0;
            for(Integer i=0;i<lasts;i++)
            {
                String Linn = sc.nextLine();
                //System.out.println(Linn);
                String[] Line = Linn.split(",");
                String NameOfStop = Line[0];
                String Latitude = Line[1];
                String Longititude = Line[2];
                Integer ADTime = Integer.parseInt(Line[3]);
                //System.out.println(ADTime);
                Boolean isAdded = false;
                if(i==0) Starttime = ADTime;
                for(Stoper s: this.stopList)
                {
                    if(s.isEqual(Latitude, Longititude, NameOfStop))
                    {
                        s.addBusTime(NameOfBus, ADTime);
                        isAdded = true;
                    }
                }
                if(!isAdded)
                {
                    stopList.add(new Stoper(NameOfBus, NameOfStop, Latitude, Longititude, ADTime, maxWaitTime));
                }

                this.Buslines.get(NameOfBus).addBusStopTime(Starttime, NameOfStop, Latitude, Longititude, ADTime);
            }
        }
        /*
        *   Test:

        for(String str:this.Buslines.keySet())
        {
            System.out.println(this.Buslines.get(str).toString()+"\n\n\n");
        }
        for(Stoper sp: this.stopList)
        {
            System.out.println(sp.toString());
        }*/

        //build graph using data str above:
        System.out.println("Building full graph....");
        BuildGraph();
        return new PathFinder(this.trafficDesigner, this.stopList, this.Buslines,maxWaitTime);
    }

    private String getPositionName(String Station, String BusName, Integer Arrival)
    {
        return Station + "_" + BusName + "_" + Arrival.toString();
    }

    private void BuildGraph()
    {
        //traffic
        for(Stoper sp: stopList)
        {
            List<Pair<String, Integer>> bustime = sp.getBusTimeTable();
            String StopName = sp.getName();
            for(Pair<String, Integer> psi: bustime)
            {
                System.out.println(psi.getKey1()+psi.getKey2().toString());
                String Generated = getPositionName(StopName, psi.getKey1(), psi.getKey2());
                trafficDesigner.add(Generated);
            }
            System.out.println(sp.toString());
        }



        for(Stoper sp: stopList)
        {
            List<Pair<String, Integer>> bustime = sp.getBusTimeTable();
            String StopName = sp.getName();
            for(Pair<String, Integer> psi: bustime)
            {
                String GenerFrom = getPositionName(StopName, psi.getKey1(), psi.getKey2());

                List<Pair<String, Integer>> catchs = sp.anyAbleToCatch(psi.getKey2());
                for(Pair<String, Integer> pss: catchs)
                {
                    Integer Duration = pss.getKey2()-psi.getKey2();
                    String GenerTo = getPositionName(StopName, pss.getKey1(), pss.getKey2());
                    assert Duration >= 0;
                    trafficDesigner.set(GenerFrom, GenerTo, Duration);
                }
            }
        }

        for(String bus: this.Buslines.keySet())
        {
            Liner line = this.Buslines.get(bus);
            Map<Integer, Set<Pair<Position, Integer>>> TimePlan = line.getLineMap();
            for(Integer StartTime: TimePlan.keySet())
            {
                Set<Pair<Position, Integer>> roll = TimePlan.get(StartTime);
                List<Pair<Position, Integer>> Roll = new ArrayList<>();
                Roll.addAll(roll);
                Collections.sort(Roll, new Comparator<Pair<Position, Integer>>() {
                    @Override
                    public int compare(Pair<Position, Integer> o1, Pair<Position, Integer> o2) {
                        return o1.getKey2().compareTo(o2.getKey2());
                    }
                });
                int pos = 0;
                String EdgeFrom;
                Integer EdgeStarttime=0;
                String GenerFrom="";
                for(Pair<Position ,Integer> psi: Roll)
                {
                    if(pos==0)
                    {
                        pos = 1;
                        EdgeFrom = psi.getKey1().getName();
                        EdgeStarttime = psi.getKey2();
                        GenerFrom = getPositionName(EdgeFrom, bus, EdgeStarttime);
                    }
                    else
                    {
                        String EdgeTo = psi.getKey1().getName();
                        Integer EdgeArrival = psi.getKey2();
                        String GenerTo = getPositionName(EdgeTo, bus, EdgeArrival);
                        trafficDesigner.set(GenerFrom, GenerTo, EdgeArrival-EdgeStarttime);
                        GenerFrom = GenerTo;
                    }
                }
            }
        }
        System.out.println("graph build finished.");
        //test:
        //System.out.println(trafficDesigner.toString());
    }

}
