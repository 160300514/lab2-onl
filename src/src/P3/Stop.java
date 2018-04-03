package src.P3;

import src.P1.graph.Pair;

import java.util.List;

public interface Stop {
    public String getName();

    public String getLatitude();

    public String getLongitude();

    public void addBusTime(String BusName, Integer StopTime);

    public List<Pair<String,Integer>> getBusTimeTable();

    public Boolean isEqual(String LatitudeComp, String LongtitudeComp, String StopNameComp);

    public List<Pair<String, Integer>> anyAbleToCatch(Integer ArriveTime);

    @Override
    public String toString();
}
