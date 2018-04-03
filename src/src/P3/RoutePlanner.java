package src.P3;


import java.util.List;

public interface RoutePlanner
{
    public void checkRep();

    public List<String> findStopsBySubstring(String search_key);

    public List<Itinerary> computeRoute(Stop src, Stop tar,int Time);

    public boolean dataConstructor();
}
