import java.util.*;

/**
 * Graph class represents a graph data structure and includes methods to find the Minimum Spanning Tree (MST)
 * using Kruskal's algorithm.
 @Author Rylin Combs
 Date: 4/26/2024
 */
public class Graph {
    class Edge implements Comparable<Edge> {
        int src, dest, weight; // Source vertex, destination vertex, and weight of the edge

        /**
         * Comparator function used for sorting edges based on their weight.
         *
         * @param compareEdge Another edge to compare with
         * @return The difference in weight between this edge and the compared edge
         */
        public int compareTo(Edge compareEdge) {
            return this.weight - compareEdge.weight;
        }
    }

    // A class to represent a subset for union-find
    class subset {
        int parent, rank; // Parent and rank of the subset
    }

    int vertices, edges; // Number of vertices and edges
    Edge edge[]; // Collection of all edges

    /**
     * Constructor for Graph class.
     *
     * @param v Number of vertices in the graph
     * @param e Number of edges in the graph
     */
    Graph(int v, int e) {
        vertices = v;
        edges = e;
        edge = new Edge[edges];
        for (int i = 0; i < e; ++i)
            edge[i] = new Edge();
    }

    /**
     * A utility function to find set of an element i (uses path compression technique).
     *
     * @param subsets Array of subsets
     * @param i       Element whose subset needs to be found
     * @return Parent of the subset
     */
    int find(subset subsets[], int i) {
        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent);
        return subsets[i].parent;
    }

    /**
     * A function that does union of two sets of x and y (uses union by rank).
     *
     * @param subsets Array of subsets
     * @param x       First element
     * @param y       Second element
     */
    void Union(subset subsets[], int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        if (subsets[xroot].rank < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank > subsets[yroot].rank)
            subsets[yroot].parent = xroot;
        else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }

    /**
     * The main function to construct Minimum Spanning Tree (MST) using Kruskal's algorithm.
     */
    void KruskalMST() {
        Edge result[] = new Edge[vertices]; // This will store the resultant MST
        int e = 0; // Index variable for result[]
        int i = 0; // Index variable for sorted edges

        for (i = 0; i < vertices; ++i)
            result[i] = new Edge();

        // Step 1: Sort all the edges in non-decreasing order of their weight
        Arrays.sort(edge);

        // Allocate memory for creating V subsets
        subset subsets[] = new subset[vertices];
        for (i = 0; i < vertices; ++i)
            subsets[i] = new subset();

        // Create V subsets with single elements
        for (int v = 0; v < vertices; ++v) {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }

        i = 0; // Initialize index for the edges array

        // Number of edges to be taken is equal to V-1
        while (e < vertices - 1) {
            // Step 2: Pick the smallest edge. Check if it forms a cycle with the spanning tree formed so far.
            Edge next_edge = new Edge();
            next_edge = edge[i++];

            int x = find(subsets, next_edge.src);
            int y = find(subsets, next_edge.dest);

            if (x != y) {
                result[e++] = next_edge;
                Union(subsets, x, y);
            }
            // Else discard the next_edge
        }

        // Print the contents of result[] to display the built MST
        System.out.println("Following are the edges in the constructed MST:");
        for (i = 0; i < e; ++i)
            System.out.println(result[i].src + " -- " + result[i].dest + " == " + result[i].weight);
    }

    /**
     * Main method to test the Graph class with sample inputs.
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        int V = 5; // Number of vertices in graph
        int E = 7; // Number of edges in graph
        Graph graph = new Graph(V, E);

        // Add edges to the graph
        graph.edge[0].src = 0; graph.edge[0].dest = 1; graph.edge[0].weight = 4;
        graph.edge[1].src = 0; graph.edge[1].dest = 2; graph.edge[1].weight = 3;
        graph.edge[2].src = 1; graph.edge[2].dest = 2; graph.edge[2].weight = 1;
        graph.edge[3].src = 1; graph.edge[3].dest = 3; graph.edge[3].weight = 2;
        graph.edge[4].src = 2; graph.edge[4].dest = 3; graph.edge[4].weight = 4;
        graph.edge[5].src = 3; graph.edge[5].dest = 4; graph.edge[5].weight = 2;
        graph.edge[6].src = 4; graph.edge[6].dest = 0; graph.edge[6].weight = 4;

        // Function call to find Minimum Spanning Tree (MST)
        graph.KruskalMST();
    }
}
