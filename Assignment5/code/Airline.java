/*************************************************************************
 *  Compilation:  javac Airline.java
 *  Execution:    java Airline V E
 *  Dependencies: Bag.java Edge.java
 *
 *  An edge-weighted undirected graph, implemented using adjacency lists.
 *  Parallel edges and self-loops are permitted.
 *
 *************************************************************************/

/**
 *  The <tt>Airline</tt> class represents an undirected graph of vertices
 *  named 0 through V-1, where each edge has a real-valued weight.
 *  It supports the following operations: add an edge to the graph,
 *  in the graph, iterate over all of the neighbors incident to a vertex.
 *  Parallel edges and self-loops are permitted.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/43mst">Section 4.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
import java.util.Iterator;
import java.util.Scanner;

public class Airline {
    private final int V;
    private int E;
    private Bag<Edge>[] distance;
    private Bag<Edge>[] price;
    private String[] cities;

    /**
     * Create an empty edge-weighted graph with V vertices.
     */
    public Airline(int V) {
        if (V < 0) throw new RuntimeException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        cities = new String[V];
        distance = (Bag<Edge>[]) new Bag[V];
        price = (Bag<Edge>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            distance[v] = new Bag<Edge>();
            price[v] = new Bag<Edge>();
        }
    }

    /**
     * Create a weighted graph from input stream.
     */
    public Airline(Scanner in) {
        this(in.nextInt());
        for (int i = 0; i < V; i++)
            cities[i] = in.next();
        while (in.hasNextLine()) {
            int v = in.nextInt() - 1;
            int w = in.nextInt() - 1;
            double distance = in.nextDouble();
            double price = in.nextDouble();
            Edge e1 = new Edge(v, w, distance);
            Edge e2 = new Edge(v, w, price);
            addEdgeDist(e1);
            addEdgePrice(e2);
        }
    }

    /**
     * Return the number of vertices in this graph.
     */
    public int V() {
        return V;
    }

    /**
     * Return the number of edges in this graph.
     */
    public int E() {
        return E;
    }


    /**
     * Add the edge e to this graph.
     */
    private void addEdgeDist(Edge e) {
        int v = e.either();
        int w = e.other(v);
        distance[v].add(e);
        distance[w].add(e);
        E++;
    }

    private void addEdgePrice(Edge e) {
        int v = e.either();
        int w = e.other(v);
        price[v].add(e);
        price[w].add(e);
    }

    public void addEdge(int v, int w, double distance, double price){
        Edge eDist = new Edge(v, w, distance);
        Edge ePrice = new Edge(v, w, price);
        addEdgeDist(eDist);
        addEdgePrice(ePrice);
    }

    public void removeEdge(int v, int w){
        Iterator<Edge> BagIter = distance[v].iterator();
        Iterator<Edge> BagIterw = distance[w].iterator();
        Iterator<Edge> PBagIter = price[v].iterator();
        Iterator<Edge> PBagIterw = price[w].iterator();

        Edge e;
        while(BagIter.hasNext()){
            e = BagIter.next();
            if(e.other(v) == w) {
                distance[v].remove(e);
                break;
            }
        }

        while(BagIterw.hasNext()){
            e = BagIterw.next();
            if(e.other(w) == v) {
                distance[w].remove(e);
                break;
            }
        }

        while(PBagIter.hasNext()){
            e = PBagIter.next();
            if(e.other(v) == w) {
                price[v].remove(e);
                break;
            }
        }

        while(PBagIterw.hasNext()){
            e = PBagIterw.next();
            if(e.other(w) == v) {
                price[w].remove(e);
                break;
            }
        }

    }


    /**
     * Return the edges incident to vertex v as an Iterable.
     * To iterate over the edges incident to vertex v, use foreach notation:
     * <tt>for (Edge e : graph.adj(v))</tt>.
     */
    public Iterable<Edge> adj(int v) {
        return distance[v];
    }

    /**
     * Return all edges in this graph as an Iterable.
     * To iterate over the edges, use foreach notation:
     * <tt>for (Edge e : graph.edges())</tt>.
     */
    public Iterable<Edge> edges() {
        Bag<Edge> list = new Bag<Edge>();
        for (int v = 0; v < V; v++) {
            int selfLoops = 0;
            for (Edge e : adj(v)) {
                if (e.other(v) > v) {
                    list.add(e);
                }
                // only add one copy of each self loop
                else if (e.other(v) == v) {
                    if (selfLoops % 2 == 0) list.add(e);
                    selfLoops++;
                }
            }
        }
        return list;
    }

    public double[][] MST(){
        int[] parent = new int[V]; //Array of parents of the node
        double[] edges = new double[V]; //Array of minimum edges that reach the node
        boolean[] visited = new boolean[V]; //Determine if node already visited

        for(int i = 0; i < V; i++){
            edges[i] = Integer.MAX_VALUE; //Set min edges to max value at first
            visited[i] = false; //Nodes not visited at first
        }
        parent[0] = -1; //Starting at node means no parent
        edges[0] = 0; //Starting at node means value of minimum edge that reads start is technically 0

        for(int j = 0; j < V - 1; j++){
            int minEdgeIndex = minEdge(edges, visited, V); //Find minimum edge value from non-visited nodes
            visited[minEdgeIndex] = true; //Node visited

            for(Edge e: distance[minEdgeIndex]){
                int other = e.other(minEdgeIndex);
                if(visited[other] == false && e.weight() < edges[other]){
                    //If edge exists, node has not been visited, and adjacency matrix value less than current
                    parent[other] = minEdgeIndex; //Set new parent
                    edges[other] = e.weight(); //Set new minimum edge value
                }
            }
        }

        double[][] minMatrix = new double[V][V];
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
        return minMatrix;
    }

    private int minEdge(double[] edges, boolean[] visited, int size){
        double min = Integer.MAX_VALUE;
        int minIndex = -1;
        for(int i = 0; i < size; i++){
            if(visited[i] == false && edges[i] < min) { //If node not visited, and edges matrix value smaller
                min = edges[i]; //Set as new minimum
                minIndex = i; //Update minIndex
            }
        }
        return minIndex;
    }
    
    public String Dijkstra(int option, String start, String end){
        Bag<Edge>[] edgeWeight;
        if(option == 0)
            edgeWeight = distance;
        else
            edgeWeight = price;

        int startVertex = -1;
        int endVertex = -1;
        for(int i = 0; i < cities.length; i++){
            if(cities[i].equals(start))
                startVertex = i;
            else if(cities[i].equals(end))
                endVertex = i;
        }

        if(startVertex < 0 || endVertex < 0)
            return null;

        int[] parent = new int[V]; //Set up parent, distance, visited data structures
        double[] distance = new double[V];
        boolean[] visited = new boolean[V];
        for(int i = 0; i < distance.length; i++){
            if(i != startVertex)
                distance[i] = Integer.MAX_VALUE;
        }
        for(int i = 0; i < parent.length; i++)
            parent[i] = -1;
        for(int i = 0; i < visited.length; i++)
            visited[i] = false;

        int curr = startVertex;
        while(curr != -1){ //Means all possible nodes have been visited

            for(Edge e: edgeWeight[curr]){
                int other = e.other(curr);
                if(!visited[other] && e.weight() != -1){ //Check adjacency list
                    double tent_dist = distance[curr] + e.weight();
                    if(tent_dist < distance[other]) { //See if distance less than in stored array
                        distance[other] = tent_dist; //Update parent and distance arrays
                        parent[other] = curr;
                    }
                }
            }
            visited[curr] = true; //Visited is true
            curr = minDistance(distance, visited, V); //Find unvisited vertex with smallest distance
        }
        String s;
        if(parent[endVertex] == -1)
            return null;
        else{
            curr = endVertex;

            s = "" + cities[endVertex]; //End with destination
            while(curr != startVertex) { //Look for parents until reach startVertex
                s = cities[parent[curr]] + " " + (distance[curr] - distance[parent[curr]]) + " " + s; //Insert the parent at beginning of string
                curr = parent[curr]; //Update curr
            }
            s = "Total weight is " + distance[endVertex] + "\n" + s;
        }
        return s;
    }

    private int minDistance(double[] d, boolean[] b, int size){
        double min = Integer.MAX_VALUE;
        int minIndex = -1;
        for(int i = 0; i < size; i++){
            if(!b[i] && d[i] < min ){ //Update minIndex if distance less than min variable
                minIndex = i;
                min = d[i];
            }
        }
        return minIndex;
    }

    public String[] getCities(){
        return cities;
    }

    public String BFS(String src, String dest) {

        int startVertex = -1;
        int endVertex = -1;
        for(int i = 0; i < V; i++){
            if(cities[i].equals(src))
                startVertex = i;
            else if(cities[i].equals(dest))
                endVertex = i;
        }
        if(startVertex == -1 || endVertex == -1)
            return null;
        return bfs(startVertex, endVertex);
    }

    private String bfs(int s, int d) {
        boolean[] marked = new boolean[V];
        int[] distTo = new int[V];
        int[] edgeTo = new int[V];
        Queue<Integer> q = new Queue<Integer>();
        for (int v = 0; v < V(); v++)
            distTo[v] = Integer.MAX_VALUE;
        distTo[s] = 0;
        marked[s] = true;
        q.enqueue(s);

        int w = -1;
        while (!q.isEmpty() && w != d) {
            if(q.isEmpty())
                return null;
            int v = q.dequeue();
            for (Edge e : distance[v]) {
                w = e.other(v);
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
        if(distance[s].size() == 0)
            return null;

        String str = new String();
        str = cities[d];
        int curr = d;
        while(curr != s) { //Look for parents until reach startVertex
            str = cities[edgeTo[curr]] + " " + str; //Insert the parent at beginning of string
            curr = edgeTo[curr]; //Update curr
        }
        str = new String("Minimum number of hops is " + distTo[d] + "\n") + str;
        return str;
    }

    public void findTrips(double p){
        for(int i = 0; i < V; i++){
            for(Edge e: price[i]){
                //Not complete, but the idea here is to have a Queue of the vertices
                //Check the total edge weight value and determine if it's less than the price.
                //If so, print that trip out.
                //Also, there should be a visited array that determines if a vertex has already been seen
                if(e.weight() <= p)
                    System.out.println("Cost: " + e.weight() + " Path: " + cities[i] +
                            " " + e.weight() + " " + cities[e.other(i)]);
            }
        }
    }

    /**
     * Return a string representation of this graph.
     */
    public String toString() {
        String NEWLINE = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges. Distances:" + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(cities[v] + ": ");
            for (Edge e : distance[v])
                s.append(cities[e.either()] + "-" + cities[e.other(e.either())] + " " + e.weight() + "  ");
            s.append(NEWLINE);
        }
        s.append(NEWLINE);
        s.append("Prices:" + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(cities[v] + ": ");
            for (Edge e : price[v])
                s.append(cities[e.either()] + "-" + cities[e.other(e.either())] + " " + e.weight() + "  ");
            s.append(NEWLINE);
        }
        return s.toString();
    }

    public int size(){
        return V;
    }

    private class Edge implements Comparable<Edge> {

        private final int v;
        private final int w;
        private final double weight;

        /**
         * Create an edge between v and w with given weight.
         */
        public Edge(int v, int w, double weight) {
            this.v = v;
            this.w = w;
            this.weight = weight;
        }

        /**
         * Return the weight of this edge.
         */
        public double weight() {
            return weight;
        }

        /**
         * Return either endpoint of this edge.
         */
        public int either() {
            return v;
        }

        /**
         * Return the endpoint of this edge that is different from the given vertex
         * (unless a self-loop).
         */
        public int other(int vertex) {
            if (vertex == v) return w;
            else if (vertex == w) return v;
            else throw new RuntimeException("Illegal endpoint");
        }

        /**
         * Compare edges by weight.
         */
        public int compareTo(Edge that) {
            if (this.weight() < that.weight()) return -1;
            else if (this.weight() > that.weight()) return +1;
            else return 0;
        }

        /**
         * Return a string representation of this edge.
         */
        public String toString() {
            return String.format("%d-%d %.2f", v, w, weight);
        }

    }
}
