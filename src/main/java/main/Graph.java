package main;

import java.util.List;
import java.util.ArrayList;
import cse332.graph.GraphUtil;
/**
 * This is an optional class in case you want to implement adjacency list with graph
 */
public class Graph {
    private List<Edge>[] adjList;
    private List<Edge>[] invAdjList;

    public class Edge {
        private int weight;
        private int source;
        public int destination;

        public Edge(int weight, int source, int destination) {
            this.weight = weight;
            this.source = source;
            this.destination = destination;
        }

        public int getWeight() { return weight; }
        public int getSource() { return source; }
        public int getDest() {return destination; }
    }

    public Graph(int[][] adjMatrix) {
        this.adjList = (ArrayList<Edge>[]) new ArrayList[adjMatrix.length];

        for (int i = 0; i < adjMatrix.length; i++) {
            List<Edge> edges = new ArrayList<>();

            for (int j = 0; j < adjMatrix[i].length; j++) {
                int weight = adjMatrix[i][j];
                if (weight != GraphUtil.INF) {
                    edges.add(new Edge(weight,i,j));
                }
            }
            this.adjList[i] = edges;
        }

        this.invAdjList = (ArrayList<Edge>[]) new ArrayList[adjMatrix.length];
        for (int i = 0; i < adjMatrix[0].length; i++) {
            List<Edge> edges = new ArrayList<>();

            for (int j = 0; j < adjMatrix.length; j++) {
                int weight = adjMatrix[j][i];
                if (weight != GraphUtil.INF) {
                    edges.add(new Edge(weight,j,i));
                }
            }
            invAdjList[i] = edges;
        }
    }

    public List<Edge>[] getAdjList() {
        return this.adjList;
    }

    public List<Edge>[] getInvAdjList() { return this.invAdjList; }

    public void printAdjList() {
        for (List<Edge> edges: this.adjList) {
            System.out.print("start: ");
            for (Edge e: edges) {
                System.out.println("weight: " + e.weight + ", source: " + e.source + ", dest: " + e.destination);
            }
            System.out.println("end");
        }
    }
}
