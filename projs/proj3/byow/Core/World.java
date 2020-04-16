package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random

public class World {
    private static final int WIDTH = 40;
    private static final int HEIGHT = 40;
    private static final int MAXROWS = 5;
    private static final int MINROWS = 3;
    private static final int MAXCOLS = 5;
    private static final int MINCOLS = 3;
    private static final int MINGAP = 1;
    private static final int MAXITER = 50000;
    private static final TETile FLOOR = Tileset.FLOOR;
    private static final long SEED = 28123;
    private static Random RANDOM = new Random(SEED);
    private static Direction direction = new Direction();
    private static int windingPercent = 30;
    private static TETile[][] worldTile = new TETile[WIDTH][HEIGHT];

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

//        TETile[][] worldTile = new TETile[WIDTH][HEIGHT];
        fillWall();
        addWorld(worldTile, 30, MAXITER);
        ter.renderFrame(worldTile);
    }

    private static void fillWall() {
        for (int i = 0; i < worldTile.length; i++) {
            for (int j = 0; j < worldTile[0].length; j++) {
                worldTile[i][j]= Tileset.WALL;
            }
        }
    }

    private static void addWorld(TETile[][] tiles, int roomNum, int maxIter) {
        ArrayList<RandomRoom> rooms = new ArrayList<>();
        addRooms(roomNum, maxIter, rooms);
        addMaze(tiles);
        connectRegions(tiles);
        removeDeadEnd(tiles);
    }

    private static void addRooms(int roomNum, int maxIter, List<RandomRoom> rooms) {
        int count = 0;
        for (int i = 0; i < maxIter; i++) {
            if (count == roomNum) return;
            RandomRoom room = new RandomRoom();
            while (room.rows < MINROWS || room.cols < MINCOLS) {
                room = new RandomRoom();
            }
            if (addRoom(room)) {
                count += 1;
                rooms.add(room);
            }
        }
    }

    private static boolean addRoom(RandomRoom room) {
        int xl = room.xl, yl = room.yl, xr = room.xr, yr = room.yr;
        if (worldTile[xl][yl] == FLOOR ||
                worldTile[xl][yr] == FLOOR ||
                worldTile[xr][yl] == FLOOR ||
                worldTile[xr][yr] == FLOOR) return false;

        for (int i = 1; i <= MINGAP; i++) {
            if (worldTile[xl-i][yl-i] == FLOOR ||
                    worldTile[xl-i][yr+i] == FLOOR ||
                    worldTile[xr+i][yl-i] == FLOOR ||
                    worldTile[xr+i][yr+i] == FLOOR) return false;
        }

        for (int x = xl; x <= xr; x++) {
            for (int y = yl; y <= yr; y++) {
                if (!canPutRoom(new Position(x, y), room)) return false;
            }
        }

        for (int x = xl; x <= xr; x++) {
            for (int y = yl; y <= yr; y++) {
                worldTile[x][y] = FLOOR;
            }
        }
//
//        for (int y = yl-1; y <= yr+1; y++) {
//            tiles[xl-1][y] = Tileset.WALL;
//            tiles[xr+1][y] = Tileset.WALL;
//        }
//        for (int x = xl-1; x <= xr+1; x++) {
//            tiles[x][yl-1] = Tileset.WALL;
//            tiles[x][yr+1] = Tileset.WALL;
//        }
        return true;
    }

    private static boolean canPutRoom(Position pos, RandomRoom room) {
        int xl = room.xl, yl = room.yl, xr = room.xr, yr = room.yr;
        int x = pos.xp, y = pos.yp;
        boolean canPut = worldTile[x+1][y] != FLOOR && worldTile[x][y+1] != FLOOR;

        if (x == xl) {
            for (int i = 1; i <= MINGAP; i++) {
                if (!canPut) return false;
                canPut = canPut && worldTile[x-i][y] != FLOOR;
            }
        }
        if (y == yl) {
            for (int i = 1; i <= MINGAP; i++) {
                if (!canPut) return false;
                canPut = canPut && worldTile[x][y - i] != FLOOR;
            }
        }
        if (x == xr) {
            for (int i = 1; i <= MINGAP; i++) {
                if (!canPut) return false;
                canPut = canPut && worldTile[x + i][y] != FLOOR;
            }
        }
        if (y == yr) {
            for (int i = 1; i <= MINGAP; i++) {
                if (!canPut) return false;
                canPut = canPut && worldTile[x][y + i] != FLOOR;
            }
        }
        return canPut;
    }

    static class RandomRoom {
        private int xl;
        private int yl;
        private int xr;
        private int yr;
        private int rows;
        private int cols;

        RandomRoom() {
            xl = MINGAP + RANDOM.nextInt(WIDTH-2*MINGAP-MINROWS+1);
            yl = MINGAP + RANDOM.nextInt(HEIGHT-2*MINGAP-MINCOLS+1);
            rows = MINROWS + RANDOM.nextInt(MAXROWS-MINROWS+1);
            cols = MINCOLS + RANDOM.nextInt(MAXCOLS-MINCOLS+1);
            xr = xl + rows - 1;
            xr = xr >= WIDTH - MINGAP ? WIDTH - 1 - MINGAP : xr;
            rows = xr - xl + 1;
            yr = yl + cols - 1;
            yr = yr >= HEIGHT - MINGAP ? HEIGHT - 1 - MINGAP : yr;
            cols = yr - yl + 1;
        }
    }

    private static void addMaze(TETile[][] tiles) {
        for (int x = 1; x < WIDTH-1; x += 2) {
            for (int y = 1; y < HEIGHT-1; y += 2) {
                if (tiles[x-1][y] == Tileset.WALL &&
                    tiles[x+1][y] == Tileset.WALL &&
                    tiles[x][y-1] == Tileset.WALL &&
                    tiles[x][y+1] == Tileset.WALL) growMaze(tiles, new Position(x, y));
            }
        }
        removeDeadEnd(tiles);
    }

    private static class Direction {
        private final Position UP = new Position(0, 1);
        private final Position DOWN = new Position(0, -1);
        private final Position LEFT = new Position(-1, 0);
        private final Position RIGHT = new Position(1, 0);
        private final Position UPLEFT = new Position(-1 ,1);
        private final Position UPRIGHT = new Position(1, 1);
        private final Position DOWNLEFT = new Position(-1, -1);
        private final Position DOWNRIGHT = new Position(1, -1);
        private Position[] cardinal = {UP, DOWN, LEFT, RIGHT};
        private Position[] fullCardinals = {UP, DOWN, LEFT, RIGHT, UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT};

    }

    private static void growMaze(TETile[][] tiles, Position pos) {
        LinkedList<Position> cells = new LinkedList<>();
        Position lastDir = null;
        tiles[pos.xp][pos.yp] = FLOOR;
        cells.add(pos);
        while (!cells.isEmpty()) {
            Position cell = cells.getLast();
            ArrayList<Position> unmadeCells = new ArrayList<>();

            for (Position dir : direction.cardinal) {
                if (canCarve(cell, dir)) {
                    unmadeCells.add(dir);
                }
            }

            if (!unmadeCells.isEmpty()) {
                Position dir;
                if (unmadeCells.contains(lastDir) && RandomUtils.uniform(RANDOM, 100) > windingPercent) {
                    dir = lastDir;
                } else {
                    dir = unmadeCells.get(RandomUtils.uniform(RANDOM, unmadeCells.size()));
                }

                carve(cell.plus(dir));
                carve(cell.plus(dir).plus(dir));
                if (isValidatePos(cell.plus(dir).plus(dir))) {
                    cells.add(cell.plus(dir).plus(dir));
                } else {
                    cells.add(cell.plus(dir));
                }
                lastDir = dir;
            } else {
                cells.removeLast();
                lastDir = null;
            }
        }
    }

    static class Position {
        int xp;
        int yp;

        Position(int x, int y) {
            xp = x;
            yp = y;
        }

        private Position plus(Position p) {
            return new Position(this.xp + p.xp, this.yp + p.yp);
        }

        private Position subtract(Position p) {
            return new Position(this.xp - p.xp, this.yp - p.yp);
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (o.getClass() != Position.class) {
                return false;
            }
            Position p =(Position) o;
            return (this.xp == p.xp && this.yp == p.yp);
        }

        @Override
        public int hashCode() {
            return this.xp + this.yp * WIDTH;
        }

        private int distance(Position p) {
            Position pos = this.subtract(p);
            return (pos.xp * pos.xp + pos.yp * pos.yp);
        }
    }

    private static void connectRegions(TETile[][] tiles) {

    }

    private static void removeDeadEnd(TETile[][] tiles) {

    }

//    private static TETile randomTile() {
//        int tileNum = RANDOM.nextInt(3);
//        switch (tileNum) {
//            case 0: return Tileset.WALL;
//            case 1: return Tileset.FLOWER;
//            case 2: return Tileset.AVATAR;
//            default: return Tileset.FLOOR;
//        }
//    }
}
