package src.P3;

import src.P1.graph.ConcreteVerticesGraph;
import src.P1.graph.Graph;
import src.P1.graph.Pair;
import src.P1.graph.Vertex;

import java.sql.Array;
import java.util.*;

public class PathFinder implements RoutePlanner
{
    private ConcreteVerticesGraph<String> Graph;
    private Map<String, String> pre;
    private Set<Stoper> stop;
    private Map<String, Liner> Buslines;
    private Map<String, String> StationSep;//Station(NBus Seped)->Station
    private Map<String, Stoper> StationParam;//Station(Speciality)->Stoper
    private Map<String, Integer> SepedBusTime;//BusStaionseped->BusStartTime
    private Integer maxWaitTime;

    /*
    Class: PathFinder implements RoutePlanner
        Rep:
            private ConcreteVerticesGraph<String> Graph;
            private Map<String, String> pre;
            private Set<Stoper> stop;
            private Map<String, Liner> Buslines;
            private Map<String, String> StationSep;//Station(NBus Seped)->Station
            private Map<String, Stoper> StationParam;//Station(Speciality)->Stoper
            private Map<String, Integer> SepedBusTime;//BusStaionseped->BusStartTime
            private Integer maxWaitTime;

        the first four value have been illustrated before, here explain the next four value:

            StationSep: the Separated Bus Station mapped to the original bus Station.
                        (Example: “start dash_61C_9200” it specified the station: “start dash”
                         and the bus stopped: “61C”, and time: “9200”seconds. And this station
                         mapped to the station: “start dash”(isay… hihihi)

            StationParam: the Separated bus Station mapped to the Station info: Stoper,
                          which contains the value of latitude, longitude and so on.

            Pre: the private value used in the method Dijkstra to store the pre-visit point.

            SepedBusTime: the Separated bus Station mapped to bus start time.

        Method:
            1.	private void gener_station_map()
                    this method can provide the Rep value: StationSep, StationParam,
                    SepedBusTime and is used while constructing the class.

            2.	public Itinerary computeRoute(Stop src, Stop tar,int Time)
                    It first compute the possible Station it can start with, due to
                    I have separated the station into different points, connected with
                    an edge between two points, which duration is less than the “max Wait Time”.
                    And then, I run Dijkstra algorithm from each possible start points, and finally
                    compare each result computed by the algorithm, get the legal answer with lowest time cost.

            3.	private Itinerary dateBack(Pair<Map<String, String>, Map<String, Integer>> minlist,
                                                    Integer mintime, String target, Integer initiaqltime)
                    To generate the path from the result of the Dijkstra, we need the dateback method,
                    it computes from the target point and get the src point of it, and repeat the same
                    process until we meet the src or null point. Using Stack to reverse the arrange.
                    If we meet the null point, and the former point is not one of the src point, this may
                    be caused by some fault or the graph cannot generate from src to tar.
                    Finally, returns the ans with Itinerary structure.

            4.	public Map<String, Integer> Dijkstra(String src)
                    running Algorithm: Dijkstra, based on graph provided as global value.
                    From src, returns Map<String, Integer> defines the distance from src
                    to key point, src is a generated point with Station, BusName, time.


       **************************************************************************************************
        AF(RoutePlanner) = {a graph of traffic lines, and possible solutions}

        RI()=undefined/

        rep expose:
            return value only relates the ans which is a string varible.
            using the private, new , final.

        *************************************************************
     */

    public PathFinder(ConcreteVerticesGraph<String> trafficDesigner, Set<Stoper> stopList, Map<String, Liner> Buslines, Integer maxWaitTime)
    {
        Graph = trafficDesigner;
        stop = stopList;
        this.Buslines = Buslines;
        this.pre = new HashMap<>();
        StationSep = new HashMap<>();
        this.StationParam = new HashMap<>();
        this.SepedBusTime = new HashMap<>();
        this.maxWaitTime = maxWaitTime;
        gener_station_map();
    }

    public Map<String, String> getSep()
    {
        return this.StationSep;
    }

    public List<Itinerary> ProvidedForTest(String src, String tar, Integer time)
    {
        Stoper st = this.StationParam.get(src);
        assert st!=null;
        System.out.println("Start:   "+st.getName());
        Stoper ed = this.StationParam.get(tar);
        System.out.println("End:  "+ed.getName());
        return computeRoute(st, ed, time);
    }

    private void gener_station_map()
    {
        System.out.println("Station Name:");
        for(Stoper stp:stop)
        {
            String Station = stp.getName();
            List< Pair<String, Integer> > bus = stp.getBusTimeTable();
            for(Pair<String, Integer> p: bus)
            {
                String tmp  = getPositionName(Station, p.getKey1(), p.getKey2());
                //System.out.println(tmp);
                this.StationSep.put(tmp, Station);
                this.SepedBusTime.put(tmp, p.getKey2());
            }
            this.StationParam.put(Station, stp);
        }
        /*test
        System.out.println("Key: StationParam");
        for(String key:this.StationParam.keySet())
        {
            System.out.println(key);
        } */
    }

    private String getPositionName(String Station, String BusName, Integer Arrival)
    {
        return Station + "_" + BusName + "_" + Arrival.toString();
    }

    public void checkRep()
    {
        assert Graph != null && pre != null && stop!=null && Buslines!=null;
    }

    public List<String> findStopsBySubstring(String search_key)
    {
        List<String> ans = new ArrayList<>();
        for(Stoper s:this.stop)
        {
            if(s.getName().contains(search_key))
            {
                ans.add(s.getName());
            }
        }
        return ans;
    }

    public List<Itinerary> computeRoute(Stop src, Stop tar,int Time)//no gener
    {
        List<Pair<String, Integer>> possibleStartTime = new ArrayList<>();//SepedStation->Timedelay
        List<String> possibleEndTime = new ArrayList<>();
        for(String key: this.StationSep.keySet())
        {
            Integer timedelay = this.SepedBusTime.get(key)-Time;
            if(this.StationSep.get(key).equals(src.getName()) && timedelay <= this.maxWaitTime)
            {
                possibleStartTime.add(new Pair<>(key, timedelay));
            }
            if(this.StationSep.get(key).equals(tar.getName()))
            {
                possibleEndTime.add(key);
            }
        }
        /*
        Test
        System.out.println("Start Point");
        for(Pair<String, Integer> p: possibleStartTime)
        {
            System.out.println(p.getKey1()+"With delay"+p.getKey2());
        }
        System.out.println("End point");
        for(String key:possibleEndTime)
        {
            System.out.println(key);
        }
        return null;
        */
        List<Itinerary> anss = new ArrayList<>();
        Integer mintime = 0x7f7f7f7f;
        Pair<Map<String, String>, Map<String, Integer>> minlist = null;
        String target = null;
        Integer timedelay = 0;
        List<Pair<Map<String, String> ,Map<String, Integer>>> holdon = new ArrayList<>();
        for(Pair<String, Integer>p: possibleStartTime)
        {
            Map<String, Integer> ans = Dijkstra(p.getKey1());
            System.out.println("One possible Solution: ");
            for(String key:ans.keySet())
            {
                System.out.println(key+"\t"+ans.get(key));
            }
            String srcc = this.StationSep.get(p.getKey1());
            System.out.println("\n");
            for(String t:possibleEndTime)
            {
                Integer timecost = ans.get(t)+p.getKey2();
                if(timecost<mintime)
                {
                    mintime = timecost;
                    minlist = new Pair<>(new HashMap<>(this.pre), ans);
                    target = t;
                    timedelay = p.getKey2();
                    Itinerary asn = dateBack(minlist, mintime, target, timedelay);
                    anss.add(asn);
                }
            }
        }
        return anss;
    }

    private Itinerary dateBack(Pair<Map<String, String>, Map<String, Integer>> minlist, Integer mintime, String target, Integer initiaqltime)
    {
        /*
        Test

        System.out.println("minlist");
        for(String key:minlist.getKey1().keySet())
        {
            System.out.println(key+"\tposs:\t"+minlist.getKey1().get(key));
        }*/
        for(String key: minlist.getKey2().keySet())
        {
            System.out.println(key+"\twithtime\t"+minlist.getKey2().get(key));
        }
        //return null;
        Stack<Pair<Integer, Pair<String, String>>> st = new Stack<>();
        Map<String, String> diff = minlist.getKey1();
        Map<String,Integer> timeoffer = minlist.getKey2();
        String Target = target;
        Integer timeoff = timeoffer.get(Target);
        Itinerary ans = new Itinerary(this.stop);
        while(true)
        {
            String Source = diff.get(Target);
            if(Source == null)
                break;
            Integer timeon = timeoffer.get(Source);

            //ans.addPartTime(Source, Target, timeoff-timeon);

            st.push(new Pair<>(timeoff-timeon, new Pair<>(Source, Target)));
            if(timeon == 0)
            {
                //ans.addPartTime("Your Start Point", Source, initiaqltime);
                st.push(new Pair<>(initiaqltime, new Pair<>("Your Start Point", Source)));
                break;
            }
            timeoff = timeon;
            Target = Source;
        }
        while(!st.isEmpty())
        {
            Pair<Integer, Pair<String, String>> p = st.peek();
            ans.addPartTime(p.getKey2().getKey1(), p.getKey2().getKey2(), p.getKey1());
            st.pop();
        }
        return ans;
    }

    public boolean dataConstructor()
    {
        //throw new RuntimeException("not implemented");
        gener_station_map();
        return true;
    }

    public Map<String, Integer> Dijkstra(String src)
    {
        int inf = 0x7f7f7f7f;
        Map<String, Integer> dis = new HashMap<>();
        List<Vertex<String>> gra = Graph.FetchFromOther();
        //Map<String, Boolean> vis = new HashMap<>();
        Set<String> vis = new HashSet<>();
        for(Vertex<String> poi: gra)
        {
            if(poi.masterOfVertex().equals(src))
            {
                Map<String , Integer> connect = poi.targetMap();
                dis.putAll(connect);
                for(String ke:connect.keySet())
                {
                    pre.put(ke, src);
                }
                Set<String> other = Graph.vertices();
                dis.put(src,0);
                for(String key:other)
                {
                    //visitPoints.put(key, new HashSet<>());
                    if(!dis.containsKey(key))
                    {
                        dis.put(key, inf);
                        pre.put(key, null);
                    }
                    //vis.put(key, false);
                }
            }
        }
        /*
        for(String key: dis.keySet())
        {
            System.out.println("Orig: "+key+"\t"+dis.get(key));
        }*/
        //vis.put(src,true);
        vis.add(src);
        int cnt = 1;
        while(cnt!=Graph.vertices().size())
        {
            cnt++;
            int min = inf;
            String poi = null;
            for(String key:dis.keySet())
            {
                if(vis.contains(key)) continue;
                Integer tmp = dis.get(key);
                if(tmp<min)
                {
                    min = tmp;
                    poi = key;
                }
            }
            System.out.println("Select: "+poi);
            if(poi==null) break;
            vis.add(poi);
            Map<String, Integer> tar = Graph.targets(poi);
            for(String able: tar.keySet())
            {
                Integer wei = dis.get(poi)+tar.get(able);
                if(!vis.contains(able) && wei<dis.get(able))
                {
                    dis.replace(able, wei);
                    pre.replace(able, poi);
                }
            }
        }
        return dis;
    }
}
