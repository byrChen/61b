import java.util.Comparator;

public class FlightComparator implements Comparator<Flight> {
    @Override
    public int compare(Flight f1, Flight f2) {
        return Integer.compare(f2.startTime, f1.startTime);
    }
}
