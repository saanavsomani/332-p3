package solvers;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.BellmanFordSolver;
import main.Parser;
import main.Graph;
import cse332.graph.GraphUtil;

import java.util.List;
import paralleltasks.*;

public class OutParallelBad implements BellmanFordSolver {

    public List<Integer> solve(int[][] adjMatrix, int source) {
        Object g = Parser.parse(adjMatrix);

        Graph graph = (Graph) g;

        List<Graph.Edge>[] adjList = graph.getAdjList();
        int V = adjList.length;

        int[] D = new int[V];
        int[] P = new int[V];

        for (int i = 0; i < V; i++) {
            D[i] = GraphUtil.INF;
            P[i] = -1;
        }
        D[source] = 0;
        int[] copiedD = ArrayCopyTask.copy(D);



    }

}