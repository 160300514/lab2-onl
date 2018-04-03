package test.P2;

import static org.junit.Assert.*;
import org.junit.Test;
import src.P2.FriendshipGraph;
import src.P2.Person;

import java.util.Scanner;

public class FriendshipGraphTest {
    public static void Main(String[] argu)
    {
        FriendshipGraph fg = new FriendshipGraph();
        Scanner sc = new Scanner(System.in);
        while(true)
        {
            System.out.println("Continue?(y/n)");
            String input = sc.nextLine();
            if(input.equals("n"))
            {
                break;
            }
            else
            {
                System.out.println("Input of the Group of Name or add name to the set before.");
                while(true)
                {
                    System.out.println("Continue input of Name?(y/not:nn)");
                    String Name = sc.nextLine();
                    if(Name.equals("not:nn"))
                        break;
                    fg.addVertex(new Person(Name));
                }
                while(true)
                {
                    System.out.println("Continue input of Relationship: Name Name?(y/n)");
                    String Names = sc.nextLine();
                    if(Names.equals("n"))
                        break;
                    else
                    {
                        String[] name = Names.split(" ");
                        fg.addEdge(new Person(name[0]), new Person(name[1]));
                    }
                }
            }
        }
        while(true)
        {
            System.out.println("Input Vertex distance: v1 v2?(y/n)");
            String ss = sc.nextLine();
            if(ss.equals("n"))
                break;
            else
            {
                String[] s = ss.split(" ");
                System.out.println(fg.getDistance(new Person(s[0]), new Person(s[1])));
            }
        }
    }
	/**
     * Tests that assertions are enabled.
     */
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled()
    {
        assert false;
    }

    /**
     * Tests calculateRegularPolygonAngle.
     */
    @Test
    public  void calculateTest() {
        FriendshipGraph gra = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        gra.addVertex(rachel);
        gra.addVertex(kramer);
        gra.addVertex(ben);
        gra.addVertex(ross);
        gra.addEdge(rachel, ross);
        gra.addEdge(ross, rachel);
        gra.addEdge(ross, ben);
        gra.addEdge(ben, ross);
        assertEquals(1, gra.getDistance(rachel, ross), 0.001);
        assertEquals(2, gra.getDistance(rachel, ben), 0.001);
        assertEquals(0, gra.getDistance(rachel, rachel), 0.001);
        assertEquals(-1, gra.getDistance(rachel, kramer), 0.001);
    }
}
