package WorldGen;

import byow.TileEngine.TETile;
import java.util.ArrayList;

public class ShapeGenerator {
    private static ArrayList<TETile> lightTiles = PersonalTiles.arrayLightTiles();
    private static ArrayList<TETile> lightWall = PersonalTiles.arrayListWalls();

    /** draws a row of tiles right-wise from the given position
     * tiles that are already floors will not be overwritten */
    public static void drawRowRight(TETile[][] tiles, Position p, TETile tile, int length) {
        for (int i = 0; i < length; i++) {
            if (!PersonalTiles.checkFloor(tiles[p.x + i][p.y])) {
                tiles[p.x + i][p.y] = tile;
            }
        }

    }

    /** draws a row of tiles left-wise from the given position
     * tiles that are already floors will not be overwritten */
    public static void drawRowLeft(TETile[][] tiles, Position p, TETile tile, int length) {
        for (int i = 0; i < length; i++) {
            if (!PersonalTiles.checkFloor(tiles[p.x - i][p.y])) {
                tiles[p.x - i][p.y] = tile;
            }
        }
    }

    /** draws a row of tiles up-wise from the given position
     * tiles that are already floors will not be overwritten */
    public static void drawColUp(TETile[][] tiles, Position p, TETile tile, int length) {
        for (int i = 0; i < length; i++) {
            if (!PersonalTiles.checkFloor(tiles[p.x][p.y + i])) {
                tiles[p.x][p.y + i] = tile;
            }
        }
    }

    /** draws a row of tiles down-wise from the given position
     * tiles that are already floors will not be overwritten */
    public static void drawColDown(TETile[][] tiles, Position p, TETile tile, int length) {
        for (int i = 0; i < length; i++) {
            if (!PersonalTiles.checkFloor(tiles[p.x][p.y - i])) {
                tiles[p.x][p.y - i] = tile;
            }
        }
    }


    /** draws a room (square/rectangle) based on the given inputs*/
    public static void drawRoom(TETile[][] world, Position p, int length, int width, TETile wallType, TETile floorType) {
        /** bottom wall */
        Position shiftedUp = p.shift(-1, -1);
        drawRowRight(world, shiftedUp, wallType, width + 2);

        for (int y = 0; y < length; y++) {
            /** floor */
            Position shiftedDown = p.shift(0, y);
            drawRowRight(world, shiftedDown, floorType, width);
            /** left wall */
            Position shiftedLeft = shiftedDown.shift(-1, 0);
            drawRowRight(world, shiftedLeft, wallType, 1);
            /** right wall */
            Position shiftedRight = shiftedDown.shift(width, 0);
            drawRowRight(world, shiftedRight, wallType, 1);
        }

        /** top wall */
        Position shiftedDown = p.shift(-1, length);
        drawRowRight(world, shiftedDown, wallType, width + 2);

    }

    /** draws a Vertizontal hallway (along with walls) based on the given info
     * @param size -> if size is positive, the hallway will be made right-wise.
     *                if size is negative, the hallway will be made left-wise.*/
    public static void drawHorizontalHallway(TETile[][] world, Position p, int size, TETile wallType, TETile floorType) {
        if (size > 0) {
            /** hallway walls */
            Position topWall = p.shift(-1, 1);
            drawRowRight(world, topWall, wallType, (size + 2)); //top hallway
            Position bottomWall = p.shift(-1, -1);
            drawRowRight(world, bottomWall, wallType, (size + 2)); //bottom hallway
            Position endWall = p.shift(size, 0);
            drawRowRight(world, endWall, wallType, 1); // end hallway
            Position frontWall = p.shift(-1, 0);
            drawRowRight(world, frontWall, wallType, 1); // front hallway
            /** floor */
            drawRowRight(world, p, floorType, size);
        } else {
            /** hallway walls */
            Position topWall = p.shift(1, 1);
            drawRowLeft(world, topWall, wallType, (size - 2) * - 1); //top hallway
            Position bottomWall = p.shift(1, -1);
            drawRowLeft(world, bottomWall, wallType, (size - 2) * - 1); //bottom hallway
            Position endWall = p.shift(size, 0);
            drawRowLeft(world, endWall, wallType, 1); // end hallway
            Position frontWall = p.shift(1, 0);
            drawRowLeft(world, frontWall, wallType, 1); // front hallway
            /** floor */
            drawRowLeft(world, p, floorType, size * -1);
        }
    }

    /** draws a vertical hallway (along with walls) based on the given info
     * @param size -> if size is positive, the hallway will be made up-wise.
     *                if size is negative, the hallway will be made down-wise.*/
    public static void drawVertHallway(TETile[][] world, Position p, int size, TETile wallType, TETile floorType) {
        if (size > 0) {
            /** hallway walls */
            Position leftWall = p.shift(-1, -1);
            drawColUp(world, leftWall, wallType, size + 2); //left hallway
            Position rightWall = p.shift(1, -1);
            drawColUp(world, rightWall, wallType, size + 2); //right hallway
            Position endWall = p.shift(0, size);
            drawColUp(world, endWall, wallType, 1); // end hallway
            Position frontWall = p.shift(0, -1);
            drawColUp(world, frontWall, wallType, 1);
            /** floor */
            drawColUp(world, p, floorType, size);

        } else {
            /** hallway walls */
            Position leftWall = p.shift(-1, 1);
            drawColDown(world, leftWall, wallType, (size - 2) * -1); //left hallway
            Position rightWall = p.shift(1, 1);
            drawColDown(world, rightWall, wallType, (size - 2) * -1); //right hallway
            Position endWall = p.shift(0, size);
            drawColDown(world, endWall, wallType, 1); // end hallway
            Position frontWall = p.shift(0, 1);
            drawColDown(world, frontWall, wallType, 1); // front hallway
            /** floor */
            drawColDown(world, p, floorType, size * -1);
        }
    }

    public static void drawAvatar(TETile[][] world, Position p, TETile tileType) {
        world[p.x][p.y] = tileType;
    }

    public static void moveAvatar(TETile[][] world, Position p_old, Position p_new, TETile tileType, TETile currFloor) {
        world[p_old.x][p_old.y] = currFloor;
        world[p_new.x][p_new.y] = tileType;
    }

    public static void drawLamp(TETile[][] world, Position p, TETile lamp) {
        world[p.x][p.y] = lamp;
    }

    public static void drawLight(TETile[][] world, Position p, int brightness, boolean power) {
        lightTiles = PersonalTiles.arrayLightTiles();
        lightWall = PersonalTiles.arrayListWalls();
        drawLightVertiInitial(world, p, brightness, power);
        drawLightHoriInitial(world, p, brightness, power);
    }
    public static void drawLightVertiInitial(TETile[][] world, Position p, int brightness, boolean power) {
        if (checkBrightness(brightness) || !checkCords(world, p.x, p.y) || checkVoid(world[p.x][p.y])) {
            return;
        }

        int[] yShift = new int[2];
        yShift[0] = 1;
        yShift[1] = -1;

        if (!power) {
            world[p.x][p.y] = PersonalTiles.offLamp;
        } else {
            world[p.x][p.y] = PersonalTiles.onLamp;
        }

        for (int i = 0; i < 2; i++) {
            int y = yShift[i];
            Position left = p.shift(-1, y);
            Position right = p.shift(1, y);
            Position center = p.shift(0, y);

            drawLightVertiCenter(world, center, brightness, y, power);
            drawLightVertiRight(world, right, brightness, y, power);
            drawLightVertiLeft(world, left, brightness, y, power);
        }
    }
    public static void drawLightVertiCenter(TETile[][] world, Position p, int brightness, int yShift, boolean power) {
        if (checkBrightness(brightness) || !checkCords(world, p.x, p.y) || checkVoid(world[p.x][p.y])) {
            return;
        }

        Position center = p.shift(0, yShift);

        if (power) {
            if (PersonalTiles.checkWall(world[p.x][p.y])) {
                world[p.x][p.y] = lightWall.get(brightness);
                return;
            } else {
                world[p.x][p.y] = lightTiles.get(brightness);
            }
        } else {
            if (PersonalTiles.checkWall(world[p.x][p.y])) {
                world[p.x][p.y] = PersonalTiles.pWallInitial;
                return;
            } else {
                world[p.x][p.y] = PersonalTiles.pFloorInitial;
            }
        }
        drawLightVertiCenter(world, center, brightness - 1, yShift, power);
    }

    public static void drawLightVertiRight(TETile[][] world, Position p, int brightness, int yShift, boolean power) {
        if (checkBrightness(brightness) || !checkCords(world, p.x, p.y) || checkVoid(world[p.x][p.y])) {
            return;
        }

        Position up = p.shift(0, yShift);
        Position right = p.shift(1, yShift);

        if (power) {
            if (PersonalTiles.checkWall(world[p.x][p.y])) {
                world[p.x][p.y] = lightWall.get(brightness);
                return;
            } else {
                world[p.x][p.y] = lightTiles.get(brightness);
            }
        } else {
            if (PersonalTiles.checkWall(world[p.x][p.y])) {
                world[p.x][p.y] = PersonalTiles.pWallInitial;
                return;
            } else {
                world[p.x][p.y] = PersonalTiles.pFloorInitial;
            }
        }
        drawLightVertiCenter(world, up, brightness - 1, yShift, power);
        drawLightVertiRight(world, right, brightness - 1, yShift, power);
    }

    public static void drawLightVertiLeft(TETile[][] world, Position p, int brightness, int yShift, boolean power) {
        if (checkBrightness(brightness) || !checkCords(world, p.x, p.y) || checkVoid(world[p.x][p.y])) {
            return;
        }

        Position up = p.shift(0, yShift);
        Position left = p.shift(-1, yShift);

        if (power) {
            if (PersonalTiles.checkWall(world[p.x][p.y])) {
                world[p.x][p.y] = lightWall.get(brightness);
                return;
            } else {
                world[p.x][p.y] = lightTiles.get(brightness);
            }
        } else {
            if (PersonalTiles.checkWall(world[p.x][p.y])) {
                world[p.x][p.y] = PersonalTiles.pWallInitial;
                return;
            } else {
                world[p.x][p.y] = PersonalTiles.pFloorInitial;
            }
        }
        drawLightVertiCenter(world, up, brightness - 1, yShift, power);
        drawLightVertiLeft(world, left, brightness - 1, yShift, power);
    }

    public static void drawLightHoriInitial(TETile[][] world, Position p, int brightness, boolean power) {
        if (checkBrightness(brightness) || !checkCords(world, p.x, p.y) || checkVoid(world[p.x][p.y])) {
            return;
        }

        int[] xShift = new int[2];
        xShift[0] = 1;
        xShift[1] = -1;

        for (int i = 0; i < 2; i++) {
            int x = xShift[i];
            Position center = p.shift(x, 0);

            Position left = p.shift(2 * x, -1);
            Position right = p.shift(2 * x, 1);
            Position leftBefore = p.shift(x, -1);
            Position rightBefore = p.shift(x, 1);

            drawLightHoriCenter(world, center, brightness, x, power);
            if (!PersonalTiles.checkWall(world[leftBefore.x][leftBefore.y])) {
                drawLightHoriLeft(world, left, brightness - 1, x, power);
            }
            if (!PersonalTiles.checkWall(world[rightBefore.x][rightBefore.y])) {
                drawLightHoriRight(world, right, brightness - 1, x, power);
            }
        }
    }

    public static void drawLightHoriCenter(TETile[][] world, Position p, int brightness, int xShift, boolean power) {
        if (checkBrightness(brightness) || !checkCords(world, p.x, p.y) || checkVoid(world[p.x][p.y])) {
            return;
        }

        Position center = p.shift(xShift, 0);

        if (power) {
            if (PersonalTiles.checkWall(world[p.x][p.y])) {
                world[p.x][p.y] = lightWall.get(brightness);
                return;
            } else {
                world[p.x][p.y] = lightTiles.get(brightness);
            }
        } else {
            if (PersonalTiles.checkWall(world[p.x][p.y])) {
                world[p.x][p.y] = PersonalTiles.pWallInitial;
                return;
            } else {
                world[p.x][p.y] = PersonalTiles.pFloorInitial;
            }
        }
        drawLightHoriCenter(world, center, brightness - 1, xShift, power);
    }

    public static void drawLightHoriLeft(TETile[][] world, Position p, int brightness, int xShift, boolean power) {
        if (checkBrightness(brightness) || !checkCords(world, p.x, p.y) || checkVoid(world[p.x][p.y])) {
            return;
        }

        Position up = p.shift(xShift, 0);
        Position left = p.shift(xShift, -1);

        if (power) {
            if (PersonalTiles.checkWall(world[p.x][p.y])) {
                world[p.x][p.y] = lightWall.get(brightness);
                return;
            } else {
                world[p.x][p.y] = lightTiles.get(brightness);
            }
        } else {
            if (PersonalTiles.checkWall(world[p.x][p.y])) {
                world[p.x][p.y] = PersonalTiles.pWallInitial;
                return;
            } else {
                world[p.x][p.y] = PersonalTiles.pFloorInitial;
            }
        }
        drawLightHoriCenter(world, up, brightness - 1, xShift, power);
        drawLightHoriLeft(world, left, brightness - 1, xShift, power);
    }

    public static void drawLightHoriRight(TETile[][] world, Position p, int brightness, int xShift, boolean power) {
        if (checkBrightness(brightness) || !checkCords(world, p.x, p.y) || checkVoid(world[p.x][p.y])) {
            return;
        }

        Position up = p.shift(xShift, 0);
        Position right = p.shift(xShift, 1);

        if (power) {
            if (PersonalTiles.checkWall(world[p.x][p.y])) {
                world[p.x][p.y] = lightWall.get(brightness);
                return;
            } else {
                world[p.x][p.y] = lightTiles.get(brightness);
            }
        } else {
            if (PersonalTiles.checkWall(world[p.x][p.y])) {
                world[p.x][p.y] = PersonalTiles.pWallInitial;
                return;
            } else {
                world[p.x][p.y] = PersonalTiles.pFloorInitial;
            }
        }
        drawLightHoriCenter(world, up, brightness - 1, xShift, power);
        drawLightHoriRight(world, right, brightness - 1, xShift, power);
    }

    public static boolean checkBrightness(int brightness) {
        if (brightness == -1) {
            return true;
        }
        return false;
    }

    public static boolean checkCords(TETile[][] world, int x, int y) {
        int width = world.length;
        int height = world[0].length;

        if (x >= 0 && y >= 0 && x < width && y < height) {
            return true;
        }
        return false;
    }

    public static boolean checkVoid(TETile tile) {
        return !PersonalTiles.checkFloor(tile) && !PersonalTiles.checkWall(tile);
    }


}

