package src.P3;

import java.io.FileNotFoundException;

public interface RoutePlannerBuilder
{
    public RoutePlanner build(String fileName, Integer maxWaitTime) throws FileNotFoundException;

}
