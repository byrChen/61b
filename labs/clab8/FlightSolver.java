import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are >= end times.
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 */
public class FlightSolver {

    PriorityQueue<Flight> pq;

    Comparator<Flight> flightComparator = (f1, f2) -> {
        return Integer.compare(f2.startTime, f1.startTime);
    };

    public FlightSolver(ArrayList<Flight> flights) {
        pq = new PriorityQueue<>(flights.size(), flightComparator);
        for (Flight fl : flights) {
            pq.add(fl);
        }
        /* FIX ME */
    }

    public int solve() {
        /* FIX ME */
        int max = 0;
        while (!pq.isEmpty()) {
            Flight fl = pq.poll();
            int count = fl.passengers;
            PriorityQueue<Flight> npq = new PriorityQueue<>(pq);
            while (!npq.isEmpty()) {
                if (npq.peek().endTime < fl.startTime) break;
                else {
                    count += npq.poll().passengers;
                }
            }
            max = count > max ? count : max;
        }
        return max;
    }
}
