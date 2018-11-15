import com.sun.org.apache.xerces.internal.impl.XMLDocumentScannerImpl;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class GraphReader
{
    private final File graphFile;
    private Scanner fileScan;

    public GraphReader(final String filename)
    {
        this(new File(filename));
    }

    public GraphReader(final File file)
    {
        if(file == null || !file.exists() || !file.isFile())
        {
            throw new IllegalArgumentException("Invalid file.");
        }
        this.graphFile = file;
    }

    public Graph readGraph() throws IOException
    {
        fileScan = new Scanner(graphFile);
        int size = Integer.parseInt(fileScan.nextLine());
        int[][] adjMatrix = new int[size][size];
        for(int row = 0; row < size; row++){
            String entry = fileScan.nextLine();
            String[] values = entry.split(",");
            for(int col = 0; col < size; col++)
                adjMatrix[row][col] = Integer.parseInt(values[col]);
        }
        return new Graph(size, adjMatrix);
    }
}