package solvers;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.BellmanFordSolver;
import main.Parser;
import main.Graph;
import cse332.graph.GraphUtil;

import java.util.List;
import paralleltasks.ArrayCopyTask;
import paralleltasks.RelaxOutTaskBad;

public class OutParallelBad implements BellmanFordSolver {

    public List<Integer> solve(int[][] adjMatrix, int source) {
        List<Graph.Edge>[] adjList = Parser.parse(adjMatrix);
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
            //List<Graph.Edge> l = adjList[j];
            //relaxing the edges
            RelaxOutTaskBad.parallel(D, copiedD, P, adjList);
        }
        return GraphUtil.getCycle(P);
    }

}