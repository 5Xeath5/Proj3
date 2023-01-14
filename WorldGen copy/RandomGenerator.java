package WorldGen;

import byow.TileEngine.TETile;

import java.util.*;

public class RandomGenerator {
    private Random RANDOM;
    private TETile[][] WORLD;

    /** first two indecies of values will contain the bottom-left wall coord and top-right wall coord respectively.
     * These coordinates will be useful to determine the direction of a wall cord*/
    private HashMap<Integer, ArrayList<ArrayList<Integer>>> shapeWallCords;
    private HashMap<Integer, ArrayList<ArrayList<Integer>>> roomSpaceCords;
    private HashMap<Integer, HashSet<Integer>> takenCords;
    private ArrayList<Integer> roomKeys;
    private int WIDTH;
    private int HEIGHT;
    private static final int extraSpace = 2;

    /** used as a key for shapeWallCords, allows for specific key per room created */
    private int numRoom = 0;

    public RandomGenerator(long SEED, int width, int height, TETile[][] world) {
        RANDOM = new Random(SEED);
        WORLD = world;
        takenCords = new HashMap<>();
        shapeWallCords = new HashMap<>();
        roomSpaceCords = new HashMap<>();
        roomKeys = new ArrayList<>();

        WIDTH = width;
        HEIGHT = height - 3;
        for (int x = 0; x < width; x++) {
            HashSet<Integer> yCord = new HashSet<>();
            takenCords.put(x, yCord);
        }
    }

    /** returns randomInteger used for determining amount of rooms */
    public int roomCount() {
        int max_room = (WIDTH * HEIGHT) / (48 * (extraSpace * extraSpace));
        int min_room = (WIDTH * HEIGHT) / (64 * (extraSpace * extraSpace));
        int num = RANDOM.nextInt(min_room, max_room);
        return num;
    }

    /** returns randomInteger used for room height*/
    private int width() {
        int w = RANDOM.nextInt(3, 10);
        return w;
    }

    /** returns randomInteger used for room height*/
    private int height() {
        int h = RANDOM.nextInt(3, 10);
        return h;
    }

    /** returns int[] used for initial coordinate of a shape
     * int[0] = xCord
     * int[1] = yCord
     */
    private int[] cord(int width, int height) {
        int x = RANDOM.nextInt(extraSpace, WIDTH - extraSpace - width);
        int y = RANDOM.nextInt(extraSpace, HEIGHT - extraSpace - height);
        int[] cords = new int[2];
        cords[0] = x;
        cords[1] = y;
        return cords;
    }

    /** returns a Collection with info to create a room
     * returns int[]
     * int[0] = xCord
     * int[1] = yCord
     * int[2] = width
     * int[3] = height
     */
    public int[] randomNums() {
        int[] shapeInfo = new int[4];
        int h = height();
        int w = width();
        int[] cords = cord(w, h);
        shapeInfo[0] = cords[0];
        shapeInfo[1] = cords[1];
        shapeInfo[2] = w;
        shapeInfo[3] = h;

        if (!roomCheck(cords, w, h)) {
            return randomNums();
        } else {
            putIntoMap(shapeInfo);
            return shapeInfo;

        }
    }

    /** returns a random wall coordinate based on the key */
    private ArrayList<Integer> randomWall(int key) {
        ArrayList<ArrayList<Integer>> wall = shapeWallCords.get(key);
        /** 2 is the lower bound, as the first two indices of wall are the bottom-left wall coord and top-right wall cord
         * such cords are used for determining direction and not to be used as a hallway starting point */
        int index = RANDOM.nextInt(2, wall.size());
        ArrayList<Integer> shapeCords = wall.get(index);
        ArrayList<Integer> firstCords = wall.get(0);
        return shapeCords;
    }

    /**
     * returns a Collection with info to create a hallway
     * returns int[]
     * int[0] = xCord
     * int[1] = yCord
     * int[2] = width
     * int[3] = height
     * int[4] = wall direction (check wallDirection comment for reference)
     */
    public int[] randomHallway(int key) {
        int width;
        int height;
        roomKeys.remove((Object) key);
        ArrayList<Integer> initialWallCords = randomWall(key);
        ArrayList<ArrayList<Integer>> currWallCords = shapeWallCords.get(key);
        ArrayList<Integer> bottomLeft = currWallCords.get(0);
        ArrayList<Integer> topRight = currWallCords.get(1);

        int direction = wallDirection(bottomLeft, topRight, initialWallCords);

        if (!roomKeys.isEmpty()) {
            int otherKey = roomKeys.get(RANDOM.nextInt(roomKeys.size()));
            ArrayList<Integer> otherWallCords = randomWall(otherKey);

            width = (otherWallCords.get(0)) - initialWallCords.get(0);
            height = (otherWallCords.get(1)) - initialWallCords.get(1);

            width = horiConverter(direction, width);
            height = vertiConverter(direction, height);
            
        } else {
            width = 0;
            height = 0;
        }
        int[] hallInfo = new int[5];


        hallInfo[0] = initialWallCords.get(0);
        hallInfo[1] = initialWallCords.get(1);
        hallInfo[2] = width;
        hallInfo[3] = height;
        hallInfo[4] = direction;

        return hallInfo;
    }

    /** extends horizontal length by 1 based on direction */
    private int horiConverter(int direction, int hori) {
        if (direction == 1 || direction == 3) {
            if (hori > 0) {
                return hori + 1;
            } else {
                return hori - 1;
            }
        }
        return hori;
    }

    /** extends vertical length by 1 based on direction */
    private int vertiConverter(int direction, int verti) {
        if (direction == 2 || direction == 4) {
            if (verti > 0) {
                return verti + 1;
            } else {
                return verti - 1;
            }
        }
        return verti;
    }

    /** returns 1 if the wall is facing up
     * returns 2 if the wall is facing right
     * returns 3 if the wall is facing down
     * returns 4 if the wall is facing left */
    private int wallDirection(ArrayList<Integer> bottomLeftWallCord, ArrayList<Integer> topRightWallCord, ArrayList<Integer> wallCord) {
        int bottomX = bottomLeftWallCord.get(0);
        int bottomY = bottomLeftWallCord.get(1);
        int topX = topRightWallCord.get(0);
        int topY = topRightWallCord.get(1);
        int wallX = wallCord.get(0);
        int wallY = wallCord.get(1);

        if (wallX == bottomX) {
            return 4;
        } else if (wallY == bottomY) {
            return 3;
        } else if (wallX == topX) {
            return 2;
        } else if (wallY == topY) {
            return 1;
        }
        return 0;
    }

    private void putIntoMap(int[] shapeInfo) {
        /** puts the taken up coordinates used by theoretical shape created by
         * shapeInfo into takenCords*/

        int xCord = shapeInfo[0];
        int yCord = shapeInfo[1];
        int width = shapeInfo[2];
        int height = shapeInfo[3];

        /** accounts for walls/bound */
        int xBoundCords = xCord - extraSpace;
        int yBoundCords = yCord - extraSpace;
        int boundWidth = width + extraSpace * 2;
        int boundHeight = height + extraSpace * 2;

        ArrayList<ArrayList<Integer>> cordCollections = new ArrayList<>();
        shapeWallCords.put(numRoom, cordCollections);

        ArrayList<ArrayList<Integer>> roomCord = new ArrayList<>();
        roomSpaceCords.put(numRoom, roomCord);

        /** adds bottom-left corner wall and top-right corner wall into values
         * Reason: can be used to coordinate whether a diff wall is facing left, right, up, down*/
        ArrayList<Integer> bottomLeftWallCord = new ArrayList<>();
        bottomLeftWallCord.add(xCord - 1);
        bottomLeftWallCord.add(yCord - 1);
        cordCollections.add(bottomLeftWallCord);

        ArrayList<Integer> topRightWallCord = new ArrayList<>();
        topRightWallCord.add(xCord + width);
        topRightWallCord.add(yCord + height);
        cordCollections.add(topRightWallCord);

        roomKeys.add(numRoom);

        for (int i = xBoundCords; i < xBoundCords + boundWidth; i++) {
            HashSet<Integer> currSet = takenCords.get(i);
            for (int j = yBoundCords; j < yBoundCords + boundHeight; j++) {
                /** puts walls Cords into map */
                if (checkWallCord(xCord, yCord, i, j, width, height)) {
                    ArrayList<Integer> wallCords = new ArrayList<>();
                    wallCords.add(i);
                    wallCords.add(j);
                    cordCollections.add(wallCords);
                }

                if (checkRoomArea(xCord, yCord, i, j, width, height)) {
                    ArrayList<Integer> areaCords = new ArrayList<>();
                    areaCords.add(i);
                    areaCords.add(j);
                    roomCord.add(areaCords);
                }
                currSet.add(j);
            }
        }
        numRoom++;

    }

    private boolean checkRoomArea(int xCord, int yCord, int currX, int currY, int width, int height) {
        if (currX < xCord + width && currX >= xCord) {
            if (currY < yCord + height && currY >= yCord) {
                return true;
            }
        }
        return false;
    }

    /** returns true if currX and currY represents a wall of a room*/
    private boolean checkWallCord(int xCord, int yCord, int currX, int currY, int width, int height) {
        if (currX == xCord - 1 || currX == xCord + width) {
            if (currY < yCord + height - 1 && currY > yCord - 1 ) {
                return true;
            }
        }
        if (currY == yCord - 1 || currY == yCord + height) {
            if (currX < xCord + width - 1 && currX > xCord - 1) {
                return true;
            }
        }
        return false;
    }

    /** returns true if the room doesn't overlap */
    public boolean roomCheck(int[] cords, int width, int length) {
        int x_cord = cords[0];
        int y_cord = cords[1];

        /** accounts for walls/bound */
        x_cord -= extraSpace;
        y_cord -= extraSpace;
        width += extraSpace * 2;
        length += extraSpace * 2;

        for (int x = x_cord;  x < width + x_cord; x++) {
            HashSet<Integer> yTaken =  takenCords.get(x);
            for (int y = y_cord ; y < length + y_cord; y++) {
               if (yTaken.contains(y)) {
                   return false;
               }
            }
        }
        return true;
    }

    /** returns true if the hallway fits within the screen */
    public boolean hallwayCheck(ArrayList<Integer> cords, int width, int length) {
        if (cords.get(0) + width + 1 > WIDTH - extraSpace || cords.get(0) + width - 1 < extraSpace) {
            return false;
        }
        if (cords.get(1) + length + 1 > HEIGHT - extraSpace || cords.get(1) + length - 1  < extraSpace) {
            return false;
        }
        return true;
    }
    public int[] randomAvatarCord() {
        int[] cords = new int[2];

        boolean valid = false;

        while (!valid) {
            int ranY = RANDOM.nextInt(HEIGHT);
            int ranX = RANDOM.nextInt(WIDTH);
            if (PersonalTiles.checkFloor(WORLD[ranX][ranY])) {
                cords[0] = ranX;
                cords[1] = ranY;
                valid = true;
            }
        }

        return cords;
    }

    public int[] randomLamp(int i ) {
        ArrayList<ArrayList<Integer>> roomCords = roomSpaceCords.get(i);

        int randomIndex = RANDOM.nextInt(roomCords.size());
        ArrayList<Integer> arrayCords = roomCords.get(randomIndex);

        int[] cords = new int[2];
        cords[0] = arrayCords.get(0);
        cords[1] = arrayCords.get(1);
        return cords;
    }
}
