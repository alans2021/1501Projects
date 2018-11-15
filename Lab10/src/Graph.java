//Edges are undirected
//-1 means no edge
//no self-looping edges allowed


public class Graph{
    //data
    private final int[][] adjacencyMatrix;
    private final int numVertices;

    public Graph(final int size, final int[][] adjacencyMatrix){
        if(adjacencyMatrix == null)
            throw new IllegalArgumentException("Edges can't be null");
        if(size <= 0)
            throw new IllegalArgumentException("Number of vertices must be positive");
        if(size != adjacencyMatrix.length || size != adjacencyMatrix[0].length)
            throw new IllegalArgumentException("Adjacency matrix length must equal number of vertices");

        //no self-looping edges
        //edges must be positive or -1

        this.adjacencyMatrix = adjacencyMatrix;
        numVertices = size;
        //createNodes();
        //numEdges = size;
    }

    public int size(){
        return numVertices;
    }

    public int[][] matrix(){
        return adjacencyMatrix;
    }

}