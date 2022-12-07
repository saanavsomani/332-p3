package solvers;

import cse332.exceptions.NotYetImplementedException;
import cse332.graph.GraphUtil;
import cse332.interfaces.BellmanFordSolver;
import main.Graph;
import main.Parser;
import paralleltasks.ArrayCopyTask;
import paralleltasks.RelaxInTask;

import java.util.List;

public class InParallel implements BellmanFordSolver {

    public List<Integer> solve(int[][] adjMatrix, int source) {
        List<Graph.Edge>[] adjList = Parser.parseInverse(adjMatrix);
        int V = adjList.length;

        int[] D = new int[V];
        int[] copiedD;
        int[] P = new int[V];

        for (int i = 0; i < V; i++) {
            D[i] = GraphUtil.INF;
            P[i] = -1;
        }
        D[source] = 0;

        for (int i = 0; i < V; i++) {
            //parallel array copying
            copiedD = ArrayCopyTask.copy(D);
            //relaxing the edges
            RelaxInTask.parallel(D, copiedD, P, adjList);
        }

        return GraphUtil.getCycle(P);
    }

}