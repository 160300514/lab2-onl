package src.P3;

import src.P1.graph.Pair;

import java.io.StringWriter;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Itinerary
{
    private  Queue<String> que = new ArrayDeque<>();
    private  Queue<Pair<Pair<String,String>, Integer>> TimeQue = new ArrayDeque<>();
    private Set<Stoper> StopSet;
    private Integer wait;
    private Integer takebus;
    private Integer start;
    private Integer end;
    private String startPl;
    private String endPl;
    /*
    3.	Class: Itinerary
        Rep:
             Queue<String> que = new ArrayDeque<>();
            Queue<Pair<Pair<String,String>, Integer>> TimeQue = new ArrayDeque<>();
            private Set<Stoper> StopSet;
            Integer wait;
            Integer takebus;
            Integer start;
            Integer end;
            String startPl;
            String endPl;
        This class uses “public void addPartTime(String From,String To,Integer time)” to add part time of the trip,
         and finally to the TimeQue: <Pair<Pair<String,String>, Integer>>which means:<Pair<Pair<Start ,End>, TimeCost>>.
         And toString: generate the Queue to a string, using the StringWriter.

       AF(Iterinary) = {arrange of the possible route plan}
       RI(It) = possible to be a RoutePlan.
       rep expose:
       private, new
     */
    public Itinerary(Set<Stoper> StopSet)
    {
        wait = 0;
        takebus = 0;
        start = 60*60*24;
        end = 0;
        startPl = null;
        endPl = null;
        this.StopSet = StopSet;
    }
    public void addPartTime(String From,String To,Integer time)
    {
        //start = Math.min(time, start);
        if(start > time)
        {
            start = time;
            startPl = From;
        }
        //end = Math.max(time, end);
        if(end < time)
        {
            end = time;
            endPl = To;
        }
        Pair<String,String> st = new Pair<>(From, To);
        TimeQue.add(new Pair<>(st, time));
        if(From.equals(To))
        {
            wait += time;
        }
        else
        {
            takebus += time;
        }
    }
    public int getStartTime()
    {
        return start;
    }
    public int getEndTime()
    {
        return end;
    }
    public int getWaitTime()
    {
        return wait;
    }
    public Stop getStartLocation()
    {
        for(Stoper stp: this.StopSet)
        {
            if(stp.getName().equals(this.startPl))
            {
                return stp;
            }
        }
        return null;
    }
    public Stop getEndLocation()
    {
        for(Stoper stp: this.StopSet)
        {
            if(stp.getName().equals(this.endPl))
            {
                return stp;
            }
        }
        return null;
    }
    public String getInstructions()
    {
        StringWriter swt = new StringWriter();
        Queue<Pair<Pair<String,String>, Integer>> TimeQues = new ArrayDeque<>(TimeQue);
        while(!TimeQues.isEmpty())
        {
            Pair<Pair<String,String>, Integer> p = TimeQues.poll();
            if(p.getKey1().getKey1().equals(p.getKey1().getKey2()))
            {
                swt.write("Wait @Station:\t"+p.getKey1().getKey1()+"\t\tFor @Seconds\t"+p.getKey2()+"\n");
            }
            else
            {
                swt.write("Travel from @Station:\t"+p.getKey1().getKey1()+"\t\tto @Station:\t"+p.getKey1().getKey2()+"\t\tFor @Seconds:\t"+p.getKey2()+"\n");
            }
        }
        return swt.toString();
    }

    @Override
    public String toString()
    {
        return getInstructions();
    }
}
