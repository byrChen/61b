package hw2;

public class UnionFind {

    private int[] vertice;

    // TODO - Add instance variables?

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        // TODO
        vertice = new int[n];
        for (int i = 0; i < n; i++) {
            vertice[i] = -1;
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        // TODO
        if (vertex < 0 || vertex >= this.vertice.length) {
            throw new IllegalArgumentException(vertex
                    + "is not a valid index.");
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        // TODO
        return - vertice[find(v1)];
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        // TODO
        return vertice[v1] < 0 ? v1 : vertice[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO
        int r1 = find(v1);
        int r2 = find(v2);
        int p1 = parent(v1);
        int p2 = parent(v2);

        while (p1 != r1) {
            vertice[v1] = r1;
            v1 = p1;
            p1 = parent(v1);
        }
        while (p2 != r2) {
            vertice[v2] = r2;
            v2 = p2;
            p2 = parent(v2);
        }

        return r1 == r2;
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        // TODO
        validate(v1);
        validate(v2);
        if (!connected(v1, v2)) {
            if (sizeOf(v1) <= sizeOf(v2)) {
                vertice[find(v2)] += vertice[find(v1)];
                vertice[find(v1)] = find(v2);
            } else {
                vertice[find(v1)] += vertice[find(v2)];
                vertice[find(v2)] = find(v1);
            }
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        // TODO
        return vertice[vertex] < 0 ? vertex : find(parent(vertex));
    }

    public int[] getSet() {
        return vertice;
    }
}
