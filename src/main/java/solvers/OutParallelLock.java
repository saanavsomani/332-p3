package solvers;

import cse332.exceptions.NotYetImplementedException;
import cse332.graph.GraphUtil;
import cse332.interfaces.BellmanFordSolver;
import main.Graph;
import main.Parser;
import paralleltasks.ArrayCopyTask;
import paralleltasks.RelaxOutTaskLock;
import java.util.concurrent.locks.ReentrantLock;

import java.util.List;

public class OutParallelLock implements BellmanFordSolver {

    public List<Integer> solve(int[][] adjMatrix, int source) {
        List<Graph.Edge>[] adjList = Parser.parse(adjMatrix);
        int V = adjList.length;

        int[] D = new int[V];
        int[] copiedD;
        int[] P = new int[V];

        ReentrantLock[] locks = new ReentrantLock[V];

        for (int i = 0; i < V; i++) {
            D[i] = GraphUtil.INF;
            P[i] = -1;
            locks[i] = new ReentrantLock();
        }

        D[source] = 0;

        for (int i = 0; i < V; i++) {
            //parallel array copying
            copiedD = ArrayCopyTask.copy(D);
            //List<Graph.Edge> l = adjList[j];
            //relaxing the edges
            RelaxOutTaskLock.parallel(D, copiedD, P, adjList, locks);
        }
        return GraphUtil.getCycle(P);
    }

}