package solvers;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.BellmanFordSolver;
import main.Parser;
import main.Graph;
import cse332.graph.GraphUtil;


import java.util.List;

public class OutSequential implements BellmanFordSolver {

    public List<Integer> solve(int[][] adjMatrix, int source) {
        Object g = Parser.parse(adjMatrix);

        Graph graph = (Graph) g;

        List<Graph.Edge>[] adjList = graph.getAdjList();
        int V = adjList.length;

        int[] D = new int[V];
        int[] copiedD = new int[V];
        int[] P = new int[V];

        for (int i = 0; i < V; i++) {
            D[i] = GraphUtil.INF;
            P[i] = -1;
        }
        D[source] = 0;

        for (int i = 0; i < V; i++) {
            copiedD[i] = D[i];
        }

        for (int i = 0; i < V - 1; i++) {
            for (int j = 0; j < V; j++) {
                List<Graph.Edge> l = adjList[j];
                for (Graph.Edge e: l) {
                    int u = e.getSource();
                    int v = e.getDest();
                    int w = e.getWeight();
                    if (D[u] != GraphUtil.INF && copiedD[u] + w < D[v]) {
                        D[v] = copiedD[u] + w;
                        P[v] = u;
                    }
                }
            }
        }
        return GraphUtil.getCycle(P);

    }

}