package src.P3;

import src.P1.graph.ConcreteVerticesGraph;
import src.P1.graph.Pair;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class EnhancedBuildAdapter implements RoutePlannerBuilder
{
    private ConcreteVerticesGraph<PointVe> graph = new ConcreteVerticesGraph<>();
    //private Set<Stoper> stopList = new HashSet<>();
    //private Map<String, Liner> Buslines = new HashMap<>();
    private Map<String, Map<Integer, List<PointVe>>> busline = new HashMap<>();
    private Map<String, Position> stopname = new HashMap<>();
    private Map<String, List<Pair<PointVe,PointVe>>> mp = new HashMap<>();

    /*
    graph: hold the edges, vertices in it, and in the method of computeRoute(),
           I added the start point into the graph. The implementation of the
           graph: I separated each bus into points, each represents one station,
           and in one station, the point representing one bus stop is separated
           into two points, one is in, the other is out, in the station, “in” points
           can add edge to the “out” points, however we cannot reverse it, and from
           “out” points can add edge to the next station’s “in” point of the same bus,
           inside the station: if two buses stops within the “maxWaitTime”, we can add
           edge from “in” point to “out” point with weight equals to the total time to wait,
           when it comes to the same bus(same start time which means the rider won’t get
           off the bus), I add edge from the “in” point to the “out” point, with the weight
           equals to 1, to avoid the “set” method in @Class Concrete Vertex Graph.
     Rep exposure: private, new.
     AF()
     */
    private void ReadFile(String fileName) throws FileNotFoundException
    {
        Scanner sc = new Scanner(new File(fileName));
        while(sc.hasNext())
        {
            String nextLine = sc.nextLine();
            String []Sep = nextLine.split(",");
            String bus = Sep[0];
            Integer numOfSt = Integer.parseInt(Sep[1]);
            Integer timeStart = 0;
            boolean flag = false;
            List<PointVe> tmp = new ArrayList<>();
            for(int i=0;i<numOfSt;i++)
            {
                String next = sc.nextLine();
                String []sep = next.split(",");
                tmp.add(new PointVe(new Position(sep[0], sep[1], sep[2]), bus, Integer.parseInt(sep[3]), true));
                if(stopname.containsKey(sep[0]))
                {
                    if(!stopname.get(sep[0]).equals(new Position(sep[0], sep[1], sep[2])))
                    {
                        throw new RuntimeException("re-occurance of the same name, but different point.");
                    }
                }
                else
                {
                    stopname.put(sep[0], new Position(sep[0],sep[1],sep[2]));
                }
                if(i==0)
                {
                    timeStart = Integer.parseInt(sep[3])+1;
                    if(!this.busline.containsKey(bus))
                    {
                        this.busline.put(bus, new HashMap<>());
                    }
                    if (this.busline.get(bus).containsKey(timeStart))
                    {
                        throw new RuntimeException("key repeated");
                    }
                    else
                    {
                        flag = true;
                    }
                }
                if(i == numOfSt-1)
                {
                    this.busline.get(bus).put(timeStart, tmp);
                }
            }
        }
    }

    public EnhancedRoutePlanner build(String fileName, Integer maxWaitTime) throws FileNotFoundException
    {
        ReadFile(fileName);
        /*
        Test

        for(String key: busline.keySet())
        {
            System.out.println(key+ "\tStart @Time:\n");
            for(Integer st:busline.get(key).keySet())
            {
                System.out.println("\t\t\t\t"+st+"with stops: ");
                for(PointVe p:busline.get(key).get(st))
                {
                    System.out.println(p.toString());
                }
            }
        }*/

        for(String key: busline.keySet())
        {
            for(Integer st:busline.get(key).keySet())
            {
                List<PointVe> tmp = this.busline.get(key).get(st);
                Collections.sort(tmp, new Comparator<PointVe>() {
                    @Override
                    public int compare(PointVe o1, PointVe o2) {
                        return o1.getLayin_time().compareTo(o2.getLayin_time());
                    }
                });
                Iterator<PointVe> it = tmp.iterator();
                PointVe pre = it.next();
                PointVe prein = new PointVe(pre, true);
                PointVe preout = new PointVe(pre, false);
                graph.set(prein, preout,0);
                if(mp.containsKey(pre.getStation_Loc().getName()))
                {
                    mp.get(pre.getStation_Loc().getName()).add(new Pair<>(prein, preout));
                }
                else
                {
                    List<Pair<PointVe,PointVe>> tpp = new ArrayList<>();
                    tpp.add(new Pair<>(prein, preout));
                    mp.put(pre.getStation_Loc().getName(), tpp);
                }
                while(it.hasNext())
                {
                    PointVe now = it.next();
                    PointVe nowin = new PointVe(now, true);
                    PointVe nowout = new PointVe(now, false);
                    graph.set(nowin, nowout,1);
                    graph.set(preout, nowin, nowin.getLayin_time()-preout.getLayin_time());
                    prein = nowin;
                    preout = nowout;
                    if(mp.containsKey(now.getStation_Loc().getName()))
                    {
                        mp.get(now.getStation_Loc().getName()).add(new Pair<>(nowin, nowout));
                    }
                    else if(true)
                    {
                        List<Pair<PointVe, PointVe>> tzz = new ArrayList<>();
                        tzz.add(new Pair<>(nowin, nowout));
                        mp.put(now.getStation_Loc().getName(), tzz);
                    }
                }
            }
        }
        for(String stop:mp.keySet())
        {
            List<Pair<PointVe,PointVe>> lst = mp.get(stop);
            Collections.sort(lst, new Comparator<Pair<PointVe, PointVe>>() {
                @Override
                public int compare(Pair<PointVe, PointVe> o1, Pair<PointVe, PointVe> o2) {
                    return o1.getKey2().getLayin_time().compareTo(o2.getKey2().getLayin_time());
                }
            });
            Iterator<Pair<PointVe, PointVe>> it = lst.iterator();
            Pair<PointVe, PointVe> pre = it.next();
            while(it.hasNext())
            {
                Pair<PointVe, PointVe> now = it.next();
                int weight = now.getKey2().getLayin_time()-pre.getKey1().getLayin_time();
                if(weight <= maxWaitTime)
                    graph.set(pre.getKey1(), now.getKey2(), weight);
                pre = now;
            }
        }
        //System.out.println(graph.toString());
        return new EnhancedRoutePlanner(this.graph, this.busline, this.stopname, this.mp,maxWaitTime);
    }
}
