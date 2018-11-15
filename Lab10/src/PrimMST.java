import java.io.File;
import java.io.IOException;
public class PrimMST
{
    final Graph initGraph;
    final Graph minSpanTree;

    public PrimMST(final String filename)
    {
        this(new File(filename));
    }

    public PrimMST(final File file)
    {
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
        this.minSpanTree = this.findMST();
    }

    public final Graph findMST()
    {
        int startV = (int) (Math.random() * initGraph.size());
        int[][] matrix = initGraph.matrix();
        //Choose random vertex to start

        int[] parent = new int[initGraph.size()]; //Array of parents of the node
        int[] edges = new int[initGraph.size()]; //Array of minimum edges that reach the node
        boolean[] visited = new boolean[initGraph.size()]; //Determine if node already visited

        for(int i = 0; i < initGraph.size(); i++){
            edges[i] = Integer.MAX_VALUE; //Set min edges to max value at first
            visited[i] = false; //Nodes not visited at first
        }
        parent[startV] = -1; //Starting at node means no parent
        edges[startV] = 0; //Starting at node means value of minimum edge that reads start is technically 0

        for(int j = 0; j < initGraph.size() - 1; j++){
            int minEdgeIndex = minEdge(edges, visited, initGraph.size()); //Find minimum edge value from non-visited nodes
            visited[minEdgeIndex] = true; //Node visited

            for(int k = 0; k < initGraph.size(); k++){
                if(matrix[minEdgeIndex][k] != -1 && visited[k] == false && matrix[minEdgeIndex][k] < edges[k]){
                    //If edge exists, node has not been visited, and adjacency matrix value less than current
                    parent[k] = minEdgeIndex; //Set new parent
                    edges[k] = matrix[minEdgeIndex][k]; //Set new minimum edge value
                }
            }
        }

        int[][] minMatrix = new int[initGraph.size()][initGraph.size()];
        for(int r = 0; r < minMatrix.length; r++){ //Create adjacency matrix from parent and edges arrays
            int preceding = parent[r];
            if(preceding != -1)
                minMatrix[preceding][r] = edges[r];
            else
                minMatrix[r][r] = -1;
        }
        for(int row = 0; row < minMatrix.length; row++){
            for(int col = 0; col < minMatrix[row].length; col++){
                if(minMatrix[row][col] == 0) //Uninitialized values automatically 0, so set to -1
                    minMatrix[row][col] = -1;
            }
        }
        return new Graph(minMatrix.length, minMatrix);
    }

    private int minEdge(int[] edges, boolean[] visited, int size){
        int min = Integer.MAX_VALUE;
        int minIndex = -1;
        for(int i = 0; i < size; i++){
            if(visited[i] == false && edges[i] < min) { //If node not visited, and edges matrix value smaller
                min = edges[i]; //Set as new minimum
                minIndex = i; //Update minIndex
            }
        }
        return minIndex;
    }

    @Override
    public String toString()
    {
        StringBuilder b = new StringBuilder();
        b.append(minSpanTree.size());
        b.append("\n");
        for(int i = 0; i < minSpanTree.size(); i++){
            for(int j = 0; j < minSpanTree.size(); j++)
                b.append(minSpanTree.matrix()[i][j] + ",");
            b.replace(b.length() - 1, b.length(), "\n");
        }

        return b.toString();
    }

    public static void main(String[] args)
    {
        if(args.length == 0)
        {
            System.out.println("Needs a input text file.");
            return;
        }

        final PrimMST pmst = new PrimMST(args[0]);
        System.out.println(pmst);
    }
}