package WorldGen;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.awt.*;
import java.util.ArrayList;

public class PersonalTiles {
    public static Color hudBar = Color.LIGHT_GRAY;

    public static Color zeroBright = new Color(2, 26, 0);
    public static Color oneBright= new Color(2, 36, 0);
    public static Color twoBright =  new Color(3, 51, 0);
    public static Color threeBright = new Color(7, 102, 0);
    public static Color fourBright = new Color(10, 153, 0);
    public static Color lampBright = new Color(12, 179, 0);
    public static Color lampOff = new Color(96, 96, 96);


    public static TETile aF = new TETile('f', Color.BLACK, hudBar, "Letter f");
    public static TETile aL = new TETile('l', Color.BLACK, hudBar, "Letter l");
    public static TETile aO = new TETile('o', Color.BLACK, hudBar, "Letter o");
    public static TETile aR = new TETile('r', Color.BLACK, hudBar, "Letter r");
    public static TETile aW = new TETile('w', Color.BLACK, hudBar, "Letter w");
    public static TETile aA = new TETile('a', Color.BLACK, hudBar, "Letter a");
    public static TETile aV = new TETile('v', Color.BLACK, hudBar, "Letter v");
    public static TETile aI = new TETile('i', Color.BLACK, hudBar, "Letter i");
    public static TETile aD = new TETile('d', Color.BLACK, hudBar, "Letter d");
    public static TETile aM = new TETile('m', Color.BLACK, hudBar, "Letter m");
    public static TETile aP = new TETile('p', Color.BLACK, hudBar, "Letter p");
    public static TETile aN = new TETile('n', Color.BLACK, hudBar, "Letter n");
    public static TETile aSlash = new TETile('/', Color.BLACK, hudBar, "/");
    public static TETile aPar = new TETile('(', Color.BLACK, hudBar, "(");
    public static TETile aParen = new TETile(')', Color.BLACK, hudBar, ")");
    public static TETile aE = new TETile('e', Color.BLACK, hudBar, "e");
    public static TETile heart = new TETile('♥', Color.RED, Color.LIGHT_GRAY, "heart");

    public static TETile pFloorInitial = new TETile('·', new Color(128, 192, 128), zeroBright,
            "personal floor");
    public static TETile pFloor4 = new TETile('·', new Color(128, 192, 128), fourBright,
            "personal floor brightness 4");
    public static TETile pFloor3 = new TETile('·', new Color(128, 192, 128), threeBright,
            "personal floor brightness 3");
    public static TETile pFloor2 = new TETile('·', new Color(128, 192, 128), twoBright,
            "personal floor brightness 2");
    public static TETile pFloor1 = new TETile('·', new Color(128, 192, 128), oneBright,
            "personal floor brightness 1");
    public static TETile onLamp = new TETile('░', lampBright, lampBright,
            "is a torch", "Png/torchfire.png");
    public static TETile offLamp = new TETile('░', lampOff, lampOff,
            "off torch", "Png/torchoff.png");

    public static TETile  pWall4 = new TETile(' ', Color.GRAY, Color.BLACK,"is a bush bright 4",
            "Png/bush4.png");

    public static TETile  pWall3 = new TETile(' ', Color.GRAY, Color.BLACK,"is a bush bright 3",
            "Png/bush3.png");

    public static TETile  pWall2 = new TETile(' ', Color.GRAY, Color.BLACK,"is a bush bright 2",
            "Png/bush2.png");

    public static TETile  pWall1 = new TETile(' ', Color.GRAY, Color.BLACK,"is a bush bright 1",
            "Png/bush1.png");

    public static TETile  pWallInitial = new TETile(' ', Color.GRAY, Color.BLACK,"is a bush bright 0",
            "Png/bush0.png");

    public static TETile pAvatar = new TETile('☺', Color.WHITE, Color.BLACK,
            "personal avatar");

    public static TETile hud = new TETile(' ', Color.GRAY, Color.LIGHT_GRAY, "hud bar");
    public static boolean checkFloor(TETile tile) {
        if (tile == pFloorInitial || tile == pFloor1 || tile == pFloor2 || tile == pFloor3
         || tile == pFloor4 || tile == onLamp || tile == pAvatar || tile == offLamp) {
            return true;
        }
        return false;
    }

    public static boolean checkWall(TETile tile) {
        if (tile == pWall4 || tile == pWall3 || tile == pWall2 || tile == pWall1 || tile == pWallInitial) {
            return true;
        }
        return false;
    }
    public static void changeAvatarBG(TETile currTile) {
        pAvatar = new TETile('☺', Color.WHITE, currTile.backgroundColor, "personal avatar");
    }

    public static ArrayList<TETile> arrayLightTiles() {
        ArrayList<TETile> tiles = new ArrayList<>();
        tiles.add(pFloor1);
        tiles.add(pFloor2);
        tiles.add(pFloor3);
        tiles.add(pFloor4);

        return tiles;
    }

    public static ArrayList<TETile>  arrayListWalls() {
        ArrayList<TETile> tiles = new ArrayList<>();
        tiles.add(pWall1);
        tiles.add(pWall2);
        tiles.add(pWall3);
        tiles.add(pWall4);

        return tiles;
    }

    public static void changeColor(int option) {
        Color[] colors;
        if (option == 0) {
            Color dot = lampBright;
            pFloorInitial = new TETile('·', dot, zeroBright, "personal floor");
            pFloor4 = new TETile('·', dot, fourBright, "personal floor brightness 4");
            pFloor3 = new TETile('·', dot, threeBright, "personal floor brightness 3");
            pFloor2 = new TETile('·', dot, twoBright, "personal floor brightness 2");
            pFloor1 = new TETile('·', dot, oneBright, "personal floor brightness 1");
            onLamp = new TETile('░', dot, lampBright, "personal floor");
            pWall4 = new TETile(' ', Color.GRAY, Color.BLACK,"is a bush bright 4",
                    "Png/bush4.png");
            pWall3 = new TETile(' ', Color.GRAY, Color.BLACK,"is a bush bright 4",
                    "Png/bush3.png");
            pWall2 = new TETile(' ', Color.GRAY, Color.BLACK,"is a bush bright 4",
                    "Png/bush2.png");
            pWall1 = new TETile(' ', Color.GRAY, Color.BLACK,"is a bush bright 4",
                    "Png/bush1.png");
            pWallInitial = new TETile(' ', Color.GRAY, Color.BLACK,"is a bush bright 4",
                    "Png/bush0.png");
            onLamp = new TETile('░', lampBright, dot,
                    "is a torch", "Png/torchfire.png");
            Tileset.NOTHING = new TETile(' ', Color.black, Color.black, "nothing", "Png/GreenVoid.png");
        } else if (option == 1) {
            colors = colorOptions.listColor(1);
            Color dot = colors[5];
            pFloorInitial = new TETile('·', dot, colors[0], "personal floor");
            pFloor1 = new TETile('·', dot, colors[1], "personal floor brightness 1");
            pFloor2 = new TETile('·', dot, colors[2], "personal floor brightness 2");
            pFloor3 = new TETile('·', dot, colors[3], "personal floor brightness 3");
            pFloor4 = new TETile('·', dot, colors[4], "personal floor brightness 4");
            onLamp = new TETile('░', dot, colors[5], "personal floor");

            pWall4 = new TETile(' ', Color.GRAY, Color.BLACK,"is a bush bright 4",
                    "Png/bushpink4.png");
            pWall3 = new TETile(' ', Color.GRAY, Color.BLACK,"is a bush bright 4",
                    "Png/bushpink3.png");
            pWall2 = new TETile(' ', Color.GRAY, Color.BLACK,"is a bush bright 4",
                    "Png/bushpink2.png");
            pWall1 = new TETile(' ', Color.GRAY, Color.BLACK,"is a bush bright 4",
                    "Png/bushpink1.png");
            pWallInitial = new TETile(' ', Color.GRAY, Color.BLACK,"is a bush bright 4",
                    "Png/bushpink0.png");
            onLamp = new TETile('░', lampBright, dot,
                    "is a torch", "Png/torchpink.png");
            Tileset.NOTHING = new TETile(' ', Color.black, Color.black, "nothing", "Png/PinkVoid.png");
        } else {
            colors = colorOptions.listColor(2);
            Color dot = colors[5];
            pFloorInitial = new TETile('·', dot, colors[0], "personal floor");
            pFloor1 = new TETile('·', dot, colors[1], "personal floor brightness 1");
            pFloor2 = new TETile('·', dot, colors[2], "personal floor brightness 2");
            pFloor3 = new TETile('·', dot, colors[3], "personal floor brightness 3");
            pFloor4 = new TETile('·', dot, colors[4], "personal floor brightness 4");
            onLamp = new TETile('░', dot, colors[5], "personal floor");

            pWall4 = new TETile(' ', Color.GRAY, Color.BLACK,"is a bush bright 4",
                    "Png/bushPurple4.png");
            pWall3 = new TETile(' ', Color.GRAY, Color.BLACK,"is a bush bright 4",
                    "Png/bushPurple3.png");
            pWall2 = new TETile(' ', Color.GRAY, Color.BLACK,"is a bush bright 4",
                    "Png/bushPurple2.png");
            pWall1 = new TETile(' ', Color.GRAY, Color.BLACK,"is a bush bright 4",
                    "Png/bushPurple1.png");
            pWallInitial = new TETile(' ', Color.GRAY, Color.BLACK,"is a bush bright 4",
                    "Png/bushPurple0.png");
            onLamp = new TETile('░', lampBright, dot,
                    "is a torch", "Png/torchpurple.png");
            Tileset.NOTHING = new TETile(' ', Color.black, Color.black, "nothing", "Png/PurpleVoid.png");
        }
    }
}
