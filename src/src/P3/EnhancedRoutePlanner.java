package src.P3;

import src.P1.graph.ConcreteVerticesGraph;
import src.P1.graph.Pair;
import src.P1.graph.Vertex;

import java.awt.*;
import java.util.*;
import java.util.List;

public class EnhancedRoutePlanner implements RoutePlanner
{
    private ConcreteVerticesGraph<PointVe> graph = new ConcreteVerticesGraph<>();
    private Map<String, Map<Integer, List<PointVe>>> busline = new HashMap<>();
    private Map<String, Position> stopname = new HashMap<>();
    private Map<String, List<Pair<PointVe,PointVe>>> station = new HashMap<>();
    private int maxWaitTime;

    /*
    Rep fields:
        private ConcreteVerticesGraph<PointVe> graph;
        private Map<String, Map<Integer, List<PointVe>>> busline;
        private Map<String, Position> stopname;
        private Map<String, List<Pair<PointVe,PointVe>>> station;
        private int maxWaitTime;

    Methods:
        public List<Itinerary> computeRoute(Stop src, Stop tar,int Time)
        private Itinerary dateback(Pair<Map<PointVe, Integer>, Map<PointVe, PointVe>> best, PointVe target)
        public Pair<Map<PointVe, Integer>, Map<PointVe, PointVe>> Dijkstra(PointVe src)

    the rep fields:
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

     Method: ComputeRoute():
                    Get through and check whether there is a possible solution, from Start place
                    and time to the target place. Using the method: DIJKSTRA. Return value is possible solution.

            Dijstra:
                    running Algorithm: Dijkstra, based on graph provided as global value. From
                    src, returns Map<String, Integer> defines the distance from src to key point,
                    src is a generated point with Station, BusName, time

            DateBack:
                    To generate the path from the result of the Dijkstra, we need the dateback method,
                   it computes from the target point and get the src point of it, and repeat the same
                   process until we meet the src or null point. Using Stack to reverse the arrange. If
                   we meet the null point, and the former point is not one of the src point, this may
                   be caused by some fault or the graph cannot generate from src to tar. Finally, returns
                    the ans with Itinerary structure.

*************************************************************************************************************
*
        AF(RoutePlanner) = any possible solutions provided bt Dijkstra,
        RI(RoutePlanner): judge whether a solution is possible.
        Rep Exposure: using private, new ,final fields and return functions.

     */
    public EnhancedRoutePlanner(ConcreteVerticesGraph<PointVe> graph, Map<String, Map<Integer, List<PointVe>>> busline, Map<String, Position> stopname, Map<String, List<Pair<PointVe,PointVe>>> mp, int maxtime)
    {
        this.graph = graph;
        this.busline = busline;
        this.stopname = stopname;
        this.station = mp;
        this.maxWaitTime = maxtime;
        checkRep();
    }

    public void checkRep()
    {
        assert graph!=null && busline!=null && stopname!=null;
    }

    public List<String> findStopsBySubstring(String search_key)
    {
        List<String> ans = new ArrayList<>();
        for(String s:this.stopname.keySet())
        {
            if(s.contains(search_key))
            {
                ans.add(s);
            }
        }
        return ans;
    }

    public List<Itinerary> computeRoute(Stop src, Stop tar,int Time)
    {
        //add first node:
        List<Pair<PointVe,PointVe>> srclst = this.station.get(src.getName());
        Iterator<Pair<PointVe,PointVe>> itsrc = srclst.iterator();
        PointVe startpre = itsrc.next().getKey1();
        PointVe startin = new PointVe(startpre.getStation_Loc(), "Start Point", Time, true);
        PointVe startout = new PointVe(startin, false);
        graph.set(startin, startout,1);
        //throw new RuntimeException("not implement");
        List<Pair<PointVe, PointVe>> lst = station.get(src.getName());
        List<Pair<PointVe, PointVe>> led = station.get(tar.getName());
        List<Pair<Map<PointVe, Integer>, Map<PointVe, PointVe>>> ans = new ArrayList<>();
        for(Pair<PointVe, PointVe> p: lst)
        {
            if(p.getKey1().getLayin_time() >= Time)
            {
                if(p.getKey1().getLayin_time()-Time <= this.maxWaitTime)
                {
                    graph.set(startin, p.getKey2(), p.getKey1().getLayin_time()-Time);
                }
            }
        }
        Pair<Map<PointVe, Integer>, Map<PointVe, PointVe>> ann = Dijkstra(startin);
        //System.out.println(graph.toString());
        /*
        System.out.println("ans:::\n");
        for(PointVe pv: ann.getKey1().keySet())
        {
            System.out.println(pv.toString()+"\t\twith value:"+ann.getKey1().get(pv));
        }*/
        PointVe targeting=null;
        int min = 0x7f7f7f7f;
        for(Pair<PointVe,PointVe> p:this.station.get(tar.getName()))
        {
            if(ann.getKey1().get(p.getKey1())<min)
            {
                min=ann.getKey1().get(p.getKey1());
                targeting = p.getKey1();
            }
        }
        List<Itinerary> anslst = new ArrayList<>();
        assert targeting!=null;
        anslst.add(dateback(ann, targeting));
        return anslst;
    }

    private Itinerary dateback(Pair<Map<PointVe, Integer>, Map<PointVe, PointVe>> best, PointVe target)
    {
        Map<PointVe, PointVe> dating = best.getKey2();
        PointVe pree = target;
        Stack<Pair<PointVe, Integer>> st = new Stack<>();
        while(pree!=null)
        {
            st.push(new Pair<>(pree, best.getKey1().get(pree)));
            pree = dating.get(pree);
        }
        Integer prenum = st.peek().getKey2();
        PointVe preSt = st.peek().getKey1();
        st.pop();
        Itinerary anst = new Itinerary();
        while(!st.isEmpty())
        {
            PointVe now = st.peek().getKey1();
            Integer time  =st.peek().getKey2();
            st.pop();
            anst.addenhanced(preSt, now, time-prenum);
            preSt = now;
            prenum = time;
        }
        return anst;
    }


    public Pair<Map<PointVe, Integer>, Map<PointVe, PointVe>> Dijkstra(PointVe src)
    {
        int inf = 0x7f7f7f7f;
        Map<PointVe, Integer> dis = new HashMap<>();
        List<Vertex<PointVe>> gra = graph.FetchFromOther();
        //Map<String, Boolean> vis = new HashMap<>();
        Set<PointVe> vis = new HashSet<>();
        Map<PointVe, PointVe> pre = new HashMap<>();
        for(Vertex<PointVe> poi: gra)
        {
            if(poi.masterOfVertex().equals(src))
            {
                Map<PointVe, Integer> connect = poi.targetMap();
                dis.putAll(connect);
                for(PointVe ke:connect.keySet())
                {
                    pre.put(ke, src);
                }
                Set<PointVe> other = graph.vertices();
                dis.put(src,0);
                for(PointVe key:other)
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
        vis.add(src);
        int cnt = 1;
        while(cnt!=graph.vertices().size())
        {
            cnt++;
            int min = inf;
            PointVe poi = null;
            for(PointVe key:dis.keySet())
            {
                if(vis.contains(key)) continue;
                Integer tmp = dis.get(key);
                if(tmp<min)
                {
                    min = tmp;
                    poi = key;
                }
            }
            if(poi==null) break;
            vis.add(poi);
            Map<PointVe, Integer> tar = graph.targets(poi);
            for(PointVe able: tar.keySet())
            {
                Integer wei = dis.get(poi)+tar.get(able);
                if(!vis.contains(able) && wei<dis.get(able))
                {
                    dis.replace(able, wei);
                    pre.replace(able, poi);
                }
            }
        }
        return new Pair<>(dis, pre);
    }

    public boolean dataConstructor()
    {
        checkRep();
        return true;
    }
}
