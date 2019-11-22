package byow.lab12;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] hexTiles = new TETile[WIDTH][HEIGHT];
        allNothing(hexTiles);
        addHexagons(hexTiles, 24, 0, 5, 2);
        ter.renderFrame(hexTiles);
    }

    private static void allNothing(TETile[][] tiles) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j]= Tileset.NOTHING;
            }
        }
    }

    private static void addHexagons(TETile[][] tiles, int x, int y, int s1, int s2) {
        int count = 0;
        for (int i = 0; i < s1; i++) {
            int yj = y;
            for (int j = 0; j < 2*s1 - count - 1; j++) {
                addHexagon(tiles, x, yj, s2, randomTile());
                addHexagon(tiles, x+i*(4*s2-2), yj, s2, randomTile());
                yj += 2 * s2;
            }
            x -= 2 * s2 - 1;
            y += s2;
            count += 1;
        }
    }

    private static void addHexagon(TETile[][] tiles, int x, int y, int s, TETile tile) {
        int count = 0;
        for (int i = y; i < y + s; i++) {
            for (int j = x; j < x +  s + 2 * count; j++) {
                tiles[j][i] = tile;
                tiles[j][i+2*(s-count)-1] = tile;
            }
            x -= 1;
            count += 1;
        }
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(3);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.AVATAR;
            default: return Tileset.FLOOR;
        }
    }

//    /**
//     * Computes the width of row i for a size s hexagon.
//     * @param s The size of the hex.
//     * @param i The row number where i = 0 is the bottom row.
//     * @return
//     */
//    public static int hexRowWidth(int s, int i) {
//        int effectiveI = i;
//        if (i >= s) {
//            effectiveI = 2 * s - 1 - effectiveI;
//        }
//
//        return s + 2 * effectiveI;
//    }
//
//    /**
//     * Computesrelative x coordinate of the leftmost tile in the ith
//     * row of a hexagon, assuming that the bottom row has an x-coordinate
//     * of zero. For example, if s = 3, and i = 2, this function
//     * returns -2, because the row 2 up from the bottom starts 2 to the left
//     * of the start position, e.g.
//     *   xxx
//     *  xxxxx
//     * xxxxxxx
//     * xxxxxxx <-- i = 2, starts 2 spots to the left of the bottom of the hex
//     *  xxxxx
//     *   xxx
//     *
//     * @param s size of the hexagon
//     * @param i row num of the hexagon, where i = 0 is the bottom
//     * @return
//     */
//    public static int hexRowOffset(int s, int i) {
//        int effectiveI = i;
//        if (i >= s) {
//            effectiveI = 2 * s - 1 - effectiveI;
//        }
//        return -effectiveI;
//    }
//
//    /** Adds a row of the same tile.
//     * @param world the world to draw on
//     * @param p the leftmost position of the row
//     * @param width the number of tiles wide to draw
//     * @param t the tile to draw
//     */
//    public static void addRow(TETile[][] world, Position p, int width, TETile t) {
//        for (int xi = 0; xi < width; xi += 1) {
//            int xCoord = p.x + xi;
//            int yCoord = p.y;
//            world[xCoord][yCoord] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
//        }
//    }
//
//    /**
//     * Adds a hexagon to the world.
//     * @param world the world to draw on
//     * @param p the bottom left coordinate of the hexagon
//     * @param s the size of the hexagon
//     * @param t the tile to draw
//     */
//    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
//
//        if (s < 2) {
//            throw new IllegalArgumentException("Hexagon must be at least size 2.");
//        }
//
//        // hexagons have 2*s rows. this code iterates up from the bottom row,
//        // which we call row 0.
//        for (int yi = 0; yi < 2 * s; yi += 1) {
//            int thisRowY = p.y + yi;
//
//            int xRowStart = p.x + hexRowOffset(s, yi);
//            Position rowStartP = new Position(xRowStart, thisRowY);
//
//            int rowWidth = hexRowWidth(s, yi);
//
//            addRow(world, rowStartP, rowWidth, t);
//
//        }
//    }
}

