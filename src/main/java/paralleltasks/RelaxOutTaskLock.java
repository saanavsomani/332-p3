package paralleltasks;

import cse332.exceptions.NotYetImplementedException;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.locks.ReentrantLock;

import cse332.graph.GraphUtil;
import main.Graph;

public class RelaxOutTaskLock extends RecursiveAction {

    public static final ForkJoinPool pool = new ForkJoinPool();
    public static final int CUTOFF = 1;

    private final int[] D, copiedD, P;
    private final List<Graph.Edge>[] adjList;
    private final int lo, hi;

    private final ReentrantLock[] locks;

    public RelaxOutTaskLock(int[] D, int[] copiedD, int[] P, List<Graph.Edge>[] adjList, int lo, int hi, ReentrantLock[] locks) {
        this.D = D;
        this.copiedD = copiedD;
        this.P = P;
        this.adjList = adjList;
        this.lo = lo;
        this.hi = hi;
        this.locks = locks;
    }

    protected void compute() {
        if (hi - lo <= CUTOFF) {
            sequential(this.D, this.copiedD, this.P, this.adjList, this.lo, this.hi, locks);
        } else {
            int mid = lo + (hi - lo) / 2;
            RelaxOutTaskBad left = new RelaxOutTaskBad(this.D, this.copiedD, this.P, this.adjList, this.lo, mid);
            RelaxOutTaskBad right = new RelaxOutTaskBad(this.D, this.copiedD, this.P, this.adjList, mid, this.hi);

            left.fork();
            right.compute();
            left.join();
        }
    }

    public static void sequential(int[] D, int[] copiedD, int[] P, List<Graph.Edge>[] adjList, int lo, int hi, ReentrantLock[] locks) {
        for (int u = lo; u < hi; u++) {
            for (Graph.Edge e: adjList[u]) {
                locks[u].lock();
                int v = e.getDest();
                int w = e.getWeight();
                if (copiedD[u] != GraphUtil.INF && copiedD[u] + w < D[v]) {
                    D[v] = copiedD[u] + w;
                    P[v] = u;
                }
                locks[u].unlock();
            }
        }
    }

    public static void parallel(int[] D, int[] copiedD, int[] P, List<Graph.Edge>[] adjList, ReentrantLock[] locks) {
        pool.invoke(new RelaxOutTaskLock(D, copiedD, P, adjList, 0, adjList.length, locks));
    }
}
