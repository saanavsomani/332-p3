package solvers;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.BellmanFordSolver;
import main.Parser;
import main.Graph;
import cse332.graph.GraphUtil;


import java.util.List;

public class OutSequential implements BellmanFordSolver {

    public List<Integer> solve(int[][] adjMatrix, int source) {
        List<Graph.Edge>[] adjList = Parser.parse(adjMatrix);
        int V = adjList.length;

        int[] D = new int[V];
        int[] copiedD = new int[V];
        int[] P = new int[V];

        for (int i = 0; i < V; i++) {
            D[i] = GraphUtil.INF;
            P[i] = -1;
        }
        D[source] = 0;

        for (int i = 0; i < V - 1; i++) {
            for (int j = 0; j < V; j++) {
                //manual array copying
                for (int w = 0; w < V; w++) {
                    copiedD[w] = D[w];
                }

                List<Graph.Edge> l = adjList[j];

                //relaxing the edges
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