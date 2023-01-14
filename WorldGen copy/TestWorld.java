package WorldGen;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.HashMap;

public class TestWorld {
    public TETile[][] WORLD;
    private int WIDTH;
    private int HEIGHT;
    public long SEED;
    private ShapeGenerator shapeGen;
    private RandomGenerator ranGen;
    public Position avatarCord;
    public TETile storeAvatarTile;

    private int roomCount;

    public static ArrayList<Position> lampsPosition;
    public static HashMap<ArrayList<Integer>, Boolean> lampsPower;

    public GameMech GM;

    public TestWorld(long seed, int width, int height) {
        SEED = seed;
        WIDTH = width;
        HEIGHT = height;
        WORLD = new TETile[WIDTH][HEIGHT];;
        ranGen = new RandomGenerator(SEED, WIDTH, HEIGHT, WORLD);
        shapeGen = new ShapeGenerator();
        lampsPosition = new ArrayList<>();
        lampsPower = new HashMap<>();
    }


    public void fillWithNothing(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    public void randomRoom(TETile[][] tiles) {
        int[] shapeInfo = ranGen.randomNums();
        int xCord = shapeInfo[0];
        int yCord = shapeInfo[1];
        int width = shapeInfo[2];
        int length = shapeInfo[3];
        Position p = new Position(xCord, yCord);
        shapeGen.drawRoom(tiles, p, length, width, PersonalTiles.pWallInitial, PersonalTiles.pFloorInitial);
    }

    public void randomHallway(TETile[][] tiles, int key) {
        int[] shapeInfo = ranGen.randomHallway(key);
        int xCord = shapeInfo[0];
        int yCord = shapeInfo[1];
        int width = shapeInfo[2];
        int height = shapeInfo[3];
        int direction = shapeInfo[4];


        /** if the direction is up or down, then vertical hallways must be created first
         * if the direction is left or right, then horizontal hallways must be created first*/
        if (direction == 1 || direction == 3) {
            Position base = new Position(xCord, yCord);
            Position p = base;

            shapeGen.drawVertHallway(tiles, p, height, PersonalTiles.pWallInitial, PersonalTiles.pFloorInitial);
            Position shifted = p.shift(0, height);
            shapeGen.drawHorizontalHallway(tiles, shifted, width, PersonalTiles.pWallInitial, PersonalTiles.pFloorInitial);
        } else {
            Position base = new Position(xCord, yCord);
            Position p = base;
            shapeGen.drawHorizontalHallway(tiles, p, width, PersonalTiles.pWallInitial, PersonalTiles.pFloorInitial);
            Position shifted = p.shift(width, 0);
            shapeGen.drawVertHallway(tiles, shifted, height, PersonalTiles.pWallInitial, PersonalTiles.pFloorInitial);
        }
    }

    public void drawWorld(TETile[][] tiles, boolean load) {
        fillWithNothing(tiles);
        roomCount = ranGen.roomCount();
        GM = new GameMech(this);
        for (int i = 0; i < roomCount; i++) {
            randomRoom(tiles);
        }
        for (int i = 0; i < roomCount; i++) {
            randomHallway(tiles, i);
        }

        if (!load) {
            for (int i = 0; i < roomCount; i++) {
                int[] lampCords = ranGen.randomLamp(i);
                Position p = new Position(lampCords[0], lampCords[1]);
                drawLamp(tiles, p, false);
            }
        }
        if (!load) {
            drawAvatar(tiles, ranGen.randomAvatarCord());
        }
    }

    public void drawAvatar(TETile[][] tiles, int[] cords) {
        Position p = new Position(cords[0], cords[1]);
        storeAvatarTile = tiles[p.x][p.y];
        PersonalTiles.changeAvatarBG(storeAvatarTile);
        shapeGen.drawAvatar(tiles, p, PersonalTiles.pAvatar);
        avatarCord = p;
    }

    public void drawAvatarOver(TETile[][] tiles, Position p, TETile over) {
        PersonalTiles.changeAvatarBG(over);
        shapeGen.drawAvatar(tiles, p, PersonalTiles.pAvatar);
    }

    public TETile[][] createWorld(boolean load) {
        drawWorld(WORLD, load);
        return WORLD;
    }

    public void loadWorld(int[] avatarCords, ArrayList<Position> lampPosition, HashMap<ArrayList<Integer>, Boolean> lampPower) {
        for (Position i : lampPosition) {
            ArrayList<Integer> cords = pToInt(i);
            drawLamp(WORLD, i, lampPower.get(cords));
        }
        drawAvatar(WORLD, avatarCords);
    }

    public void moveAvatar(TETile[][] tiles, int direction) {
        Position p = avatarCord;

        if (wallCheck(tiles, p, direction)) {
            return;
        }

        if (direction == 1) {
            Position p_new = p.shift(0, 1);
            TETile tempTile = tiles[p_new.x][p_new.y];
            PersonalTiles.changeAvatarBG(tempTile);
            shapeGen.moveAvatar(tiles, p, p_new, PersonalTiles.pAvatar, storeAvatarTile);
            storeAvatarTile = tempTile;
            avatarCord = p_new;
        } else if (direction == 2) {
            Position p_new = p.shift(1, 0);
            TETile tempTile = tiles[p_new.x][p_new.y];
            PersonalTiles.changeAvatarBG(tempTile);
            shapeGen.moveAvatar(tiles, p, p_new, PersonalTiles.pAvatar, storeAvatarTile);
            storeAvatarTile = tempTile;
            avatarCord = p_new;
        } else if (direction == 3) {
            Position p_new = p.shift(0, -1);
            TETile tempTile = tiles[p_new.x][p_new.y];
            PersonalTiles.changeAvatarBG(tempTile);
            shapeGen.moveAvatar(tiles, p, p_new, PersonalTiles.pAvatar, storeAvatarTile);
            storeAvatarTile = tempTile;
            avatarCord = p_new;
        } else if (direction == 4) {
            Position p_new = p.shift(-1, 0);
            TETile tempTile = tiles[p_new.x][p_new.y];
            PersonalTiles.changeAvatarBG(tempTile);
            shapeGen.moveAvatar(tiles, p, p_new, PersonalTiles.pAvatar, storeAvatarTile);
            storeAvatarTile = tempTile;
            avatarCord = p_new;
        }
    }

    public boolean wallCheck(TETile[][] tiles, Position p, int direction) {
        if (direction == 1) {
            return PersonalTiles.checkWall(tiles[p.x][p.y + 1]);
        }
        if (direction == 2) {
            return PersonalTiles.checkWall(tiles[p.x + 1][p.y]);
        }
        if (direction == 3) {
            return PersonalTiles.checkWall(tiles[p.x][p.y - 1]);
        }
        if (direction == 4) {
            return PersonalTiles.checkWall(tiles[p.x - 1][p.y]);
        }
        return false;
    }

    public void drawLamp(TETile[][] tiles, Position cords, boolean power) {
        ArrayList<Integer> cord = pToInt(cords);
       lampsPosition.add(cords);
       lampsPower.put(cord, power);

       lampTracker(power);
       shapeGen.drawLamp(tiles, cords, PersonalTiles.onLamp);
       shapeGen.drawLight(tiles, cords, 3, power);
    }

    public void turnOff() {
        ArrayList<Integer> cords = pToInt(avatarCord);
        if (storeAvatarTile == PersonalTiles.onLamp) {
            lampsPower.put(cords, false);
            storeAvatarTile = PersonalTiles.offLamp;
            lampTracker(false);
            shapeGen.drawLight(WORLD, avatarCord, 3, false);
            drawAvatarOver(WORLD, avatarCord, storeAvatarTile);
        } else if (storeAvatarTile == PersonalTiles.offLamp) {
            lampsPower.put(cords, true);
            storeAvatarTile = PersonalTiles.onLamp;
            lampTracker(true);
            shapeGen.drawLight(WORLD, avatarCord, 3, true);
            drawAvatarOver(WORLD, avatarCord, storeAvatarTile);
        }
    }

    public void check() {
        for (Position p : lampsPosition) {
            ArrayList<Integer> cords = pToInt(p);
            System.out.println(lampsPower.get(cords));
        }
    }

    public ArrayList<Integer> pToInt(Position p) {
        ArrayList<Integer> cords = new ArrayList<>();
        cords.add(p.x);
        cords.add(p.y);

        return cords;
    }

    public void lampTracker(boolean power) {
        if (power) {
            GM.addOnLamps();
        } else if (!power && GM.onLamps > 0){
            GM.subOnLamps();
        }
    }
    public static void main(String args[]) {
        ArrayList<Integer> test1 = new ArrayList<>();
        test1.add(1);
        test1.add(2);

        ArrayList<Integer> test2 = new ArrayList<>();
        test2.add(1);
        test2.add(2);

        System.out.println(test1 == test2);
    }

    public int getTotalLamps() {
        return roomCount;
    }
}
