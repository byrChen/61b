package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.lab9.MyTrieSet;
import bearmaps.proj2ab.KDTree;
import bearmaps.proj2ab.Point;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    private HashMap<Point, Node> pToNode = new HashMap<>();
    private HashMap<String, List<Node>> nToNodes = new HashMap<>();
    private HashMap<String, List<String>> clToNames = new HashMap<>();
    private KDTree kt;
    private MyTrieSet trie = new MyTrieSet();

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();
        for (Node n : nodes) {
            if (neighbors(n.id()).size() != 0) {
                pToNode.put(new Point(n.lon(), n.lat()), n);
            }
            if (n.name() != null && !n.name().isEmpty()) {
                String clean = cleanString(n.name());
                trie.add(clean);
                if (!clToNames.containsKey(clean)) {
                    clToNames.put(clean, new ArrayList<>());
                }
                if (!clToNames.get(clean).contains(n.name())) {
                    clToNames.get(clean).add(n.name());
                }
                if (!nToNodes.containsKey(n.name())) {
                    nToNodes.put(n.name(), new ArrayList<>());
                }
                nToNodes.get(n.name()).add(n);
            }
        }
        List<Point> points = new ArrayList<>(pToNode.keySet());
        kt = new KDTree(points);
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        return pToNode.get(kt.nearest(lon, lat)).id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        List<String> names = new ArrayList<>();
        for (String cl : trie.keysWithPrefix(cleanString(prefix))) {
            names.addAll(clToNames.get(cl));
        }
        return names;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        List<Map<String, Object>> locations = new ArrayList<>();
        for (String name : clToNames.get(cleanString(locationName))) {
            for (Node n : nToNodes.get(name)) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("lat", n.lat());
                map.put("lon", n.lon());
                map.put("name", n.name());
                map.put("id", n.id());
                locations.add(map);
            }
        }

        return locations;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
