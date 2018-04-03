package test.P3;

import org.junit.Test;
import src.P3.BuildAdapter;
import src.P3.Itinerary;
import src.P3.PathFinder;
import src.P3.Stoper;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class PathFinderTest
{
    BuildAdapter bda = new BuildAdapter();
    /*
    Test method:

    test bench:
    61C,5
        as we can,0.9902,90.0000,6200
        as we do,0.2133,92.3233,7100
        start dash,0.0212,93.4344,7900
        where is it,-0.9022,94.4444,8500
        how can i do,-1.2332,100.3333,10900
    78D,3
        as you can see,1.7222,80.0999,6000
        as we can,0.9902,90.0000,7100
        how are you,1.0002,91.0902,8900
    67R,4
        how was it,0.0001,90.0000,6700
        as we do,0.2133,92.3233,8900
        how are you,1.0002,91.0902,10300
        how can it be,0.2332,100.3333,15900
    61C,5
        as we can,0.9902,90.0000,7500
        as we do,0.2133,92.3233,8000
        start dash,0.0212,93.4344,9200
        where is it,-0.9022,94.4444,9800
        how can i do,-1.2332,100.3333,12200

   **************************************************************
   * Using test: time = 7000, from as we can, to how can it be
   *
   * results:
   * Travel from @Station:	Your Start Point		to @Station:	as we can_61C_7500		For @Seconds:	500
     Travel from @Station:	as we can_61C_7500		to @Station:	as we do_61C_8000		For @Seconds:	500
     Travel from @Station:	as we do_61C_8000		to @Station:	as we do_67R_8900		For @Seconds:	900
     Travel from @Station:	as we do_67R_8900		to @Station:	how are you_67R_10300		For @Seconds:	3600
     Travel from @Station:	how are you_67R_10300		to @Station:	how can it be_67R_15900		For @Seconds:	9200

     ******
     Format: @Station: Station_BusLine_Time to @Station   ...., For @Seconds: SEC(Integer)
     */
    @Test
    public void testMain()throws FileNotFoundException
    {
        PathFinder pf = bda.build("src/src/P3/tmpdata.csv", 1200);
        Map<String, Integer> ann =  pf.Dijkstra("as we can_61C_6200");
        for(String key:ann.keySet())
        {
            System.out.println(key+"\t\t"+ann.get(key));
        }
        List<Itinerary> ans = pf.ProvidedForTest("as we can","how can it be",7000);
        Map<String, String> mp = pf.getSep();
        for(Itinerary anss: ans)
        {
            System.out.println(anss.toString());
        }

        //Itinerary ans = pf.computeRoute(new Stoper("as we can","0.9902","90.0000",1200), new Stoper("how can it be","0.2332","100.3333",1200), 5800);
        //System.out.println("Wait: "+ans.getWaitTime());
        //System.out.println("Instruction:  "+ans.getInstructions());
    }
}
