package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {

    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final int MAXROWS = 6;
    private static final int MINROWS = 2;
    private static final int MAXCOLS = 6;
    private static final int MINCOLS = 2;
    private static final int MINGAP = 1;
    private static final int MAXITER = 10000;
//    private static final
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] hexTiles = new TETile[WIDTH][HEIGHT];
        addWalls(hexTiles);
        addWorld(hexTiles, 200, MAXITER);
        ter.renderFrame(hexTiles);
    }

    private static void addWalls(TETile[][] tiles) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j]= Tileset.WALL;
            }
        }
    }

    private static void addWorld(TETile[][] tiles, int roomNum, int maxIter) {
        ArrayList<RandomRoom> rooms = new ArrayList<>();
        addRooms(tiles, roomNum, maxIter, rooms);
        addMaze(tiles, rooms);
    }

    private static void addRooms(TETile[][] tiles, int roomNum, int maxIter, List<RandomRoom> rooms) {
        int count = 0;
        for (int i = 0; i < maxIter; i++) {
            if (count == roomNum) return;
            RandomRoom room = new RandomRoom();
            if (addRoom(tiles, room)) {
                count += 1;
                rooms.add(room);
            }
        }
    }

    private static boolean addRoom(TETile[][] tiles, RandomRoom room) {
        if (tiles[room.xPos][room.yPos] == Tileset.FLOOR) return false;

        int x = room.xPos, rows = room.rows, cols = room.cols;
        for (int i = 0; i < rows; i++) {
            int y = room.yPos;
            for (int j = 0; j < cols; j++) {
                if (!isRoom(tiles, x, y, room)) return false;
                y += 1;
            }
            x += 1;
        }

        x = room.xPos;
        for (int i = 0; i < rows; i++) {
            int y = room.yPos;
            for (int j = 0; j < cols; j++) {
                    tiles[x][y] = Tileset.FLOOR;
                    y += 1;
            }
            x += 1;
        }
        return true;
    }

    private static boolean isRoom(TETile[][] tiles, int x, int y, RandomRoom room) {
        if (x >= WIDTH - MINGAP || y >= HEIGHT - MINGAP) return false;
        int xl = room.xPos, yl = room.yPos,
                xr = xl + room.rows - 1, yr = yl + room.cols - 1;
        boolean canPut = tiles[x+1][y] != Tileset.FLOOR && tiles[x][y+1] != Tileset.FLOOR;
        if (x == xl) {
            for (int i = 1; i <= MINGAP; i++) {
                if (!canPut) return false;
                canPut = canPut && tiles[x-i][y] != Tileset.FLOOR;
            }
        }
        if (y == yl) {
            for (int i = 1; i <= MINGAP; i++) {
                if (!canPut) return false;
                canPut = canPut && tiles[x][y - i] != Tileset.FLOOR;
            }
        }
        if (x == xr) {
            for (int i = 1; i <= MINGAP; i++) {
                if (!canPut) return false;
                canPut = canPut && tiles[x + i][y] != Tileset.FLOOR;
            }
        }
        if (y == yr) {
            for (int i = 1; i <= MINGAP; i++) {
                if (!canPut) return false;
                canPut = canPut && tiles[x][y + i] != Tileset.FLOOR;
            }
        }
        return canPut;
    }

    static class RandomRoom {
        private int xPos;
        private int yPos;
        private int rows;
        private int cols;

        RandomRoom() {
            xPos = MINGAP + RANDOM.nextInt(WIDTH-2*MINGAP-MINROWS+1);
            yPos = MINGAP + RANDOM.nextInt(HEIGHT-2*MINGAP-MINCOLS+1);
            rows = MINROWS + RANDOM.nextInt(MAXROWS-MINROWS+1);
            cols = MINCOLS + RANDOM.nextInt(MAXCOLS-MINCOLS+1);
        }
    }

    private static void addMaze(TETile[][] tiles, List<RandomRoom> rooms) {
        addLinks(tiles);
        removeDeadEnd(tiles);
    }

    private static void addLinks(TETile[][] tiles) {

    }

    private static void removeDeadEnd(TETile[][] tiles) {

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
}
