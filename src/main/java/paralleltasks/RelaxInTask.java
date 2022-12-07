package paralleltasks;

import cse332.exceptions.NotYetImplementedException;
import cse332.graph.GraphUtil;
import main.Graph;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class RelaxInTask extends RecursiveAction {

    public static final ForkJoinPool pool = new ForkJoinPool();
    public static final int CUTOFF = 1;

    private final int[] D, copiedD, P;
    private final List<Graph.Edge>[] adjList;
    private final int lo, hi;

    public RelaxInTask(int[] D, int[] copiedD, int[] P, List<Graph.Edge>[] adjList, int lo, int hi) {
        this.D = D;
        this.copiedD = copiedD;
        this.P = P;
        this.adjList = adjList;
        this.lo = lo;
        this.hi = hi;
    }

    protected void compute() {
        if (hi - lo <= CUTOFF) {
            sequential(this.D, this.copiedD, this.P, this.adjList, this.lo, this.hi);
        } else {
            int mid = lo + (hi - lo) / 2;
            RelaxInTask left = new RelaxInTask(this.D, this.copiedD, this.P, this.adjList, this.lo, mid);
            RelaxInTask right = new RelaxInTask(this.D, this.copiedD, this.P, this.adjList, mid, this.hi);

            left.fork();
            right.compute();
            left.join();
        }
    }

    public static void sequential(int[] D, int[] copiedD, int[] P, List<Graph.Edge>[] adjList, int lo, int hi) {
        for (int v = lo; v < hi; v++) {
            for (Graph.Edge e : adjList[v]) {
                int u = e.getSource();
                int w = e.getWeight();
                if (copiedD[u] != GraphUtil.INF && copiedD[u] + w < D[v]) {
                    D[v] = copiedD[u] + w;
                    P[v] = u;
                }
            }
        }
    }

    public static void parallel(int[] D, int[] copiedD, int[] P, List<Graph.Edge>[] adjList) {
        pool.invoke(new RelaxInTask(D, copiedD, P, adjList, 0, adjList.length));
    }
}

