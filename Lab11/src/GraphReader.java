import java.io.File;
import java.io.IOException;

public class GraphReader
{
    private final File graphFile;

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
        return null;
    }
}