package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.*;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double solutionWeight = 0;
    private List<Vertex> solution = new ArrayList<>();
    private double timeSpent;
    private HashMap<Vertex, Double> distTo = new HashMap<>();
    private HashMap<Vertex, Vertex> edgeTo = new HashMap<>();
    private HashSet<Vertex> visited = new HashSet<>();

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        ArrayHeapMinPQ<Vertex> pq = new ArrayHeapMinPQ<>();
        distTo.put(start, 0.0);
        pq.add(start, distTo.get(start) + input.estimatedDistanceToGoal(start, end));
        Stopwatch sw = new Stopwatch();
        while (pq.size() != 0 && sw.elapsedTime() <= timeout) {
            if (pq.getSmallest().equals(end)) {
                break;
            }
            Vertex v = pq.removeSmallest();
            visited.add(v);
//            if (!visited.contains(v)) visited.add(v);
            for (WeightedEdge<Vertex> e : input.neighbors(v)) {
                Vertex q = e.to(); double w = e.weight();
                if (visited.contains(q)) continue;
                double d = distTo.get(v) + w;
                if (distTo.containsKey(q) && d >= distTo.get(q)) continue;
                distTo.put(q, d);
                edgeTo.put(q, v);
                pq.add(q, d + input.estimatedDistanceToGoal(q, end));
            }
        }
        timeSpent = sw.elapsedTime();
        if (timeSpent > timeout) outcome = SolverOutcome.TIMEOUT;
        else outcome = pq.size() == 0 ? SolverOutcome.UNSOLVABLE : SolverOutcome.SOLVED;
        if (outcome == SolverOutcome.SOLVED) {
            solutionWeight = distTo.get(end);
            solution.add(0, end);
            while (!edgeTo.get(end).equals(start)) {
                end = edgeTo.get(end);
                solution.add(0, end);
            }
            solution.add(0, start);
        }
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    @Override
    public int numStatesExplored() {
        return visited.size();
    }

    @Override
    public double explorationTime() {
        return timeSpent;
    }
}
