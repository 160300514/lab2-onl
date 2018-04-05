package test.P3;

import org.junit.Test;
import src.P3.BuildAdapter;
import src.P3.EnhancedBuildAdapter;

import java.io.FileNotFoundException;

public class BuildAdapterTest
{
    BuildAdapter bda = new BuildAdapter();

    @Test
    public void BATest() throws FileNotFoundException {
        bda.build("src/src/P3/tmpdata.csv", 1200);
    }


    EnhancedBuildAdapter ebd = new EnhancedBuildAdapter();
    @Test
    public void BDTest()throws FileNotFoundException
    {
        ebd.build("src/src/P3/tmpdata.csv", 1200);
    }
}
