import java.io.File;
import java.io.IOException;

public class DijkstraAPSP
{
    private final Graph map;
    private final Graph initGraph;

    public DijkstraAPSP(final String filename)
    {
        this(new File(filename));
    }

    public DijkstraAPSP(final File file)
    {
        map = null;
        final GraphReader reader = new GraphReader(file);
        try
        {
            this.initGraph = reader.readGraph();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            throw new IllegalArgumentException("Invalid file.");
        }
    }

    //returns all shortest paths from the start vertex
    //from the start vertex, each index represents the vertex number of the end of the path
    //each entry represents a string that represents the shortest path (of your design)
    //the array space is null if no path exists to the endpoint vertex
    public String[] findAPSP(final int startVertex)
    {
        //Check if start vertex even exists in the graph

        //algorithm and path storage

        //return all shortest paths
        return null;
    }

    //returns the length of the path from the given String representing a path (of your design)
    //returns -1 if it is not a valid path
    public int findPathLength(final String path)
    {
        return 0;
    }

    public static void main(String[] args)
    {
        if(args.length < 2)
        {
            System.out.println("Needs a input text file and a start vertex value.");
            return;
        }

        final DijkstraAPSP dapsp = new DijkstraAPSP(args[0]);
        final String[] shortestPaths = dapsp.findAPSP(Integer.parseInt(args[1]));

        for(int i = 0; i < shortestPaths.length; i++)
        {
            System.out.println(args[1] + "-->" + i + ":len=" + dapsp.findPathLength(shortestPaths[i]) + ":" + shortestPaths[i]);
        }
    }
}