import java.nio.file.Files;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

public class Assig5 {

    private static Airline graph;
    private static Scanner userInput;
    private static boolean fileChange = false;

    public static void main(String[]args){
        userInput = new Scanner(System.in);
        System.out.println("Enter filename for input");
        String fileName = userInput.nextLine();
        File file = new File(fileName);
        try {
            Scanner fileScan = new Scanner(file);
            graph = new Airline(fileScan);

            int option = -1;
            while (option != 9 ) {
                try {
                    System.out.println();
                    System.out.println("Select an option:");
                    System.out.println("1: List of all distances and prices for city routes");
                    System.out.println("2: Minimum spanning tree based on distances");
                    System.out.println("3: Shortest path based on total miles");
                    System.out.println("4: Shortest path based on price");
                    System.out.println("5: Shortest path based on number of hops");
                    System.out.println("6: List of all trips that satisfy your budget");
                    System.out.println("7: Add a new route");
                    System.out.println("8: Remove a route");
                    System.out.println("9: Quit");

                    option = userInput.nextInt();
                    if(option == 1)
                        System.out.println(graph.toString());
                    if(option == 2)
                        getMST();
                    if(option == 3)
                        useDijkstra(0);
                    if(option == 4)
                        useDijkstra(1);
                    if(option == 5)
                        getBFS();
                    if(option == 6)
                        compareBudget();
                    if(option == 7)
                        addRoute();
                    if(option == 8)
                        removeRoute();
                    else
                        System.out.println("Please enter a number from 1-9");
                }
                catch (InputMismatchException e) {
                    System.out.println("Please enter a NUMBER");
                    continue;
                }
            }
            if(fileChange){
                PrintWriter p = new PrintWriter(fileName);
                p.println(graph.size());
                String[] c = graph.getCities();
                for(int i = 0; i < c.length; i++)
                    p.println(c[i]);

                //Idea here is to get the distance and price arrays from Airline.java and receive the
                //vertex and edge weight values
            }
            System.out.println("Bye");
        }
        catch(IOException e){
            System.out.println("File name not found");
            System.exit(1);
        }
    }

    private static void getMST(){
        System.out.println("MINIMUM SPANNING TREE with edges based on distance");
        double[][] mst = graph.MST();
        String[] cities = graph.getCities();
        for(int i = 0; i < mst.length; i++){
            for(int j = 0; j < mst.length; j++){
                if(mst[i][j] != -1)
                    System.out.println(cities[i] + "," + cities[j] + ": " + mst[i][j]);
            }
        }
    }

    private static void useDijkstra(int option){
        userInput.nextLine();
        System.out.println("Which city do you want to leave from?");
        String source = userInput.nextLine();
        System.out.println("Which city do you want to arrive at?");
        String destination = userInput.nextLine();
        if(option == 0)
            System.out.println("SHORTEST PATH DISTANCE FROM " + source + " to " + destination);
        else
            System.out.println("SHORTEST PATH COST FROM " + source + " to " + destination);
        String dijkstra = graph.Dijkstra(option, source, destination);
        if(dijkstra == null)
            System.out.println("No such path exists");
        System.out.println(dijkstra);
    }

    private static void getBFS(){
        userInput.nextLine();
        System.out.println("Which city do you want to leave from?");
        String source = userInput.nextLine();
        System.out.println("Which city do you want to arrive at?");
        String destination = userInput.nextLine();
        System.out.println("FEWEST HOPS FROM " + source + " to " + destination);
        String path = graph.BFS(source, destination);
        if(path == null)
            System.out.println("No path exists");
        else
            System.out.println(path);
    }

    private static void compareBudget(){
        userInput.nextLine();
        System.out.println("What is your maximum travel cost?");
        double price = userInput.nextDouble();
        graph.findTrips(price);
    }

    private static void addRoute(){
        fileChange = true;
        userInput.nextLine();
        System.out.println("Enter the vertex number you want a route to start from");
        int v = userInput.nextInt();
        System.out.println("Enter the vertex number you want the route to end at");
        int w = userInput.nextInt();
        System.out.println("Enter the distance of the route");
        double dist = userInput.nextDouble();
        System.out.println("Enter the price of the route");
        double price = userInput.nextDouble();
        graph.addEdge(v, w, dist, price);
    }

    private static void removeRoute(){
        fileChange = true;
        userInput.nextLine();
        System.out.println("Enter the vertex number of the route you want to remove");
        int v = userInput.nextInt();
        System.out.println("Enter the other vertex number of the route you want to remove");
        int w = userInput.nextInt();
        graph.removeEdge(v, w);

    }
}
