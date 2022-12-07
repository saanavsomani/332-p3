package main;

import cse332.exceptions.NotYetImplementedException;
import java.util.List;

public class Parser {

    /**
     * Parse an adjacency matrix into an adjacency list.
     * @param adjMatrix Adjacency matrix
     * @return Adjacency list
     */
    public static List<Graph.Edge>[] parse(int[][] adjMatrix) {
        Graph g = new Graph(adjMatrix);
        return g.getAdjList();
    }

    /**
     * Parse an adjacency matrix into an adjacency list with incoming edges instead of outgoing edges.
     * @param adjMatrix Adjacency matrix
     * @return Adjacency list with incoming edges
     */
    public static List<Graph.Edge>[] parseInverse(int[][] adjMatrix) {
        Graph g = new Graph(adjMatrix);
        return g.getInvAdjList();
    }
}
