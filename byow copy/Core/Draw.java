package byow.Core;

import WorldGen.PersonalTiles;
import WorldGen.Position;
import WorldGen.TestWorld;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Draw {
    static int WIDTH;
    static int HEIGHT;
    TestWorld GEN;
    TERenderer ter;
    LinkedList<Character> stringKeys;
    boolean stringInput;
    int colorOption = 0;

    long time = 0;
    int hearts = 5;

    int currMain = 0;
    String[] mainImage;

    public Draw(int width, int height, TERenderer ter, boolean stringInput) {
        WIDTH = width;
        HEIGHT = height;
        this.ter = ter;
        stringKeys = new LinkedList<>();
        this.stringInput = stringInput;

        mainImage = new String[3];
        mainImage[0] = "Png/MainMenu.png";
        mainImage[1] = "Png/MainMenu1.png";
        mainImage[2] = "Png/MainMenu2.png";

    }

    public void inputWithString(String input) {
        char[] inputArray = input.toCharArray();
        for (char c : inputArray) {
            stringKeys.addLast(c);
        }
    }

    public static void clear() {
        StdDraw.clear(Color.BLACK);
    }

    public static Font fontBig() {
        Font fontBig = new Font("Monaco", Font.BOLD, 25);
        return fontBig;
    }
    public static Font fontSmall() {
        Font fontSmall = new Font("Monaco", Font.BOLD, 15);
        return fontSmall;
    }

    public void setUpCanvas() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        clear();
        StdDraw.enableDoubleBuffering();
    }

    public void createMenu() {
        clear();
        hearts = 5;
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(fontBig());
        StdDraw.picture(WIDTH / 2, HEIGHT / 2, mainImage[currMain]);
        StdDraw.text(WIDTH / 2, HEIGHT / 1.5, "CS61B: THE GAME");
        StdDraw.setFont(fontSmall());
        StdDraw.text(WIDTH / 2, (HEIGHT / 2), "New Game (N)");
        StdDraw.text(WIDTH / 2, (HEIGHT / 2) - 1, "Choose Theme (T)");
        StdDraw.text(WIDTH / 2, (HEIGHT / 2) - 2, "Load Game (L)");
        StdDraw.text(WIDTH / 2, (HEIGHT / 2) - 3, "Quit (Q)");

        StdDraw.show();
        changeMain();
        nextScreens();
    }

    public void createMenu2() {
        clear();
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(fontBig());

        StdDraw.picture(WIDTH / 2, HEIGHT / 2, mainImage[currMain]);
        StdDraw.text(WIDTH / 2, HEIGHT / 1.5, "CS61B: THE GAME");
        StdDraw.setFont(fontSmall());
        StdDraw.text(WIDTH / 2, (HEIGHT / 2), "New Game (N)");
        StdDraw.text(WIDTH / 2, (HEIGHT / 2) - 1, "Choose Theme (T)");
        StdDraw.text(WIDTH / 2, (HEIGHT / 2) - 2, "Load Game (L)");
        StdDraw.text(WIDTH / 2, (HEIGHT / 2) - 3, "Quit (Q)");

        StdDraw.show();
        changeMain();
    }

    public void changeMain() {
        if (currMain == 2) {
            currMain = 0;
        } else {
            currMain ++;
        }

    }




    public char getNextKey(boolean game, boolean main) {
        while (true) {
            StdDraw.pause(10);
            time += 10;
            if (stringKeys.size() > 0) {
                char currChar = stringKeys.removeFirst();
                return Character.toLowerCase(currChar);
            } else if (StdDraw.hasNextKeyTyped()) {
                char currChar = StdDraw.nextKeyTyped();
                return Character.toLowerCase(currChar);
            }
            if (game) {
                gameText();
                ter.renderFrame(GEN.WORLD);
            } else if (main && time > 1000) {
                time = 0;
                createMenu2();
            }
        }
    }

    public void nextScreens() {
        boolean changed = false;

        while (!changed) {
            char key = getNextKey(false, true);
            if (key == 'n') {
                changed = true;
                createSeed();
            }
            if (key == 'l') {
                changed = true;
                load();
            }
            if (key == 'q') {
                changed = true;
                quit();
            }
            if (key == 't') {
                changed = true;
                theme();
            }
        }
    }

    public void theme() {
        clear();
        StdDraw.text(WIDTH / 2, HEIGHT / 1.5, "Choose Theme of World!");
        /** pink */
        StdDraw.setPenColor(255,105,180);
//        StdDraw.filledCircle(WIDTH / 2, HEIGHT / 2, 3);
        StdDraw.picture(WIDTH / 2, (HEIGHT / 2), "Png/PinkOption.png");
        StdDraw.text(WIDTH / 2, (HEIGHT / 2) - 4, "pink (2)");
        /** purp */
        StdDraw.setPenColor(153,50,204);
//        StdDraw.filledCircle(WIDTH / 2, HEIGHT / 2, 3);
//        StdDraw.filledCircle(WIDTH / 1.5, HEIGHT / 2, 3);
        StdDraw.picture(WIDTH / 1.5, (HEIGHT / 2), "Png/PurpleOption.png");
        StdDraw.text(WIDTH / 1.5, (HEIGHT / 2) - 4, "purple (3)");
        /** green */
        StdDraw.setPenColor(0, 204, 0);
//        StdDraw.filledCircle(WIDTH / 3, HEIGHT / 2, 3);
        StdDraw.picture(WIDTH / 3, (HEIGHT / 2), "Png/GreenOption.png");
        StdDraw.text(WIDTH / 3, (HEIGHT / 2) - 4, "green (1)");

        StdDraw.show();
        themeInput();
    }

    public void themeInput() {
        boolean end = false;

        while (!end) {
            char input = getNextKey(false, false);
            if (input == '1') {
                end = true;
                PersonalTiles.changeColor(0);
            }
            if (input == '2') {
                end = true;
                PersonalTiles.changeColor(1);
                colorOption = 1;
            }
            if (input == '3') {
                end = true;
                PersonalTiles.changeColor(2);
                colorOption = 2;
            }
            if (input == ' ') {
                end = true;
                createMenu();
            }
        }
        createSeed();
    }

    public void createSeed() {
        clear();
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(WIDTH / 2, HEIGHT / 1.5, "Enter Seed");
        StdDraw.text(WIDTH / 2, HEIGHT / 2.5, "Start (s)");
        StdDraw.show();

        String seed = "";
        boolean end = false;

        while (!end) {
            char input = getNextKey(false, false);

            if (input == 's') {
                if (seed == "") {
                    notification("Seed cannot be empty!");
                } else {
                    createWorld(Long.parseLong(seed));
                    end = true;
                }
            } else if(input == ' ') {
                end = true;
                createMenu();
            } else if (!Character.isDigit(input)) {
                notification("Seed must be consist of only numbers!");
            } else{
                String temp = seed + input;
                try {
                    Long.valueOf(temp);
                } catch (Exception NumberFormatException) {
                    notification("Seed too large!");
                    continue;
                }

                seed = temp;
                drawSeed(seed);
            }
        }
    }

    public static void drawSeed(String seed) {
        clear();

        StdDraw.text(WIDTH / 2, HEIGHT / 1.5, "Enter Seed");

        StdDraw.setFont(fontSmall());
        StdDraw.text(WIDTH / 2, HEIGHT / 2, seed);

        StdDraw.text(WIDTH / 2, HEIGHT / 2.5, "Start (s)");
        StdDraw.show();
    }

    public static void notification(String noti) {
        StdDraw.setFont(fontSmall());
        StdDraw.text(WIDTH / 2, HEIGHT - 1, noti);
        StdDraw.show();
        StdDraw.pause(500);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(WIDTH/ 2, HEIGHT - 1, 15, 5);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.show();
    }

    public void createWorld(long seed) {
        GEN = new TestWorld(seed, WIDTH, HEIGHT);
        GEN.createWorld(false);
        gamePrompt();
    }

    public TETile[][] getTile() {
        return GEN.WORLD;
    }

    public void charMove() {
        hearts();
        boolean quit = false;
        time = 0;
        while (!quit) {
            char currChar = getNextKey(true, false);
            if (currChar == 'w') {
                GEN.moveAvatar(GEN.WORLD, 1);
            } else if (currChar == 'd') {
                GEN.moveAvatar(GEN.WORLD, 2);
            } else if (currChar == 's') {
                GEN.moveAvatar(GEN.WORLD, 3);
            } else if (currChar == 'a') {
                GEN.moveAvatar(GEN.WORLD, 4);
            } else if (currChar == ':') {
                quit = options();
            } else if (currChar == ' ') {
                createMenu();
            } else if (currChar == 'e') {
                GEN.turnOff();
            }
            quit = winScreen();
            if (!quit) {
                ter.renderFrame(GEN.WORLD);
            }
        }

        while (true) {
            char currChar = getNextKey(false, false);
            if (currChar == ' ') {
                createMenu();
                return;
            }
        }
    }

    public void hearts() {
        if (time >= 500) {
            for (int x = 0; x <= hearts; x++) {
                GEN.WORLD[x][HEIGHT - 2] = PersonalTiles.heart;
            }
            for (int x = 5; x >= hearts; x--) {
                GEN.WORLD[x][HEIGHT - 2] = PersonalTiles.hud;
            }
            if (!GEN.GM.lightTile()) {
                if (hearts >= 0) {
                    hearts--;
                }
            }
            if (GEN.GM.lightTile()) {
                if (hearts < 5) {
                    hearts++;
                }
            }
            time = 0;
        }
    }

    public void gameOver() {
        clear();
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(fontBig());
        StdDraw.text(WIDTH / 2, (HEIGHT / 2) + 1, "The darkness devoured you!");
        StdDraw.setFont(fontSmall());
        StdDraw.text(WIDTH / 2, (HEIGHT / 2) - 2, "Press space to go back to menu");
        StdDraw.show();

        while (true) {
            char currChar = getNextKey(false, false);
            if (currChar == ' ') {
                createMenu();
                return;
            }
        }
    }

    public void gameText() {

        String tile = mouseTile();
        if (hearts == -1) {
            gameOver();
        }

        int hudHeight = HEIGHT - 2;
        int start = (WIDTH / 2) - 6;

        for (int i = 0; i < WIDTH; i++) {
            if (i < WIDTH - 5) {
                GEN.WORLD[i + 5][hudHeight] = PersonalTiles.hud;
            }
            GEN.WORLD[i][hudHeight + 1] = PersonalTiles.hud;
        }

        hearts();
        GEN.WORLD[start + 1][hudHeight] = PersonalTiles.aPar;
        GEN.WORLD[start + 2][hudHeight] = PersonalTiles.aE;
        GEN.WORLD[start + 3][hudHeight] = PersonalTiles.aParen;

        GEN.WORLD[start + 5][hudHeight] = PersonalTiles.aO;
        GEN.WORLD[start + 6][hudHeight] = PersonalTiles.aN;
        GEN.WORLD[start + 7][hudHeight] = PersonalTiles.aSlash;
        GEN.WORLD[start + 8][hudHeight] = PersonalTiles.aO;
        GEN.WORLD[start + 9][hudHeight] = PersonalTiles.aF;
        GEN.WORLD[start + 10][hudHeight] = PersonalTiles.aF;

        GEN.WORLD[start + 12][hudHeight] = PersonalTiles.aL;
        GEN.WORLD[start + 13][hudHeight] = PersonalTiles.aA;
        GEN.WORLD[start + 14][hudHeight] = PersonalTiles.aM;
        GEN.WORLD[start + 15][hudHeight] = PersonalTiles.aP;
        if (tile == "void") {
            GEN.WORLD[74][hudHeight] = PersonalTiles.hud;
            GEN.WORLD[75][hudHeight] = PersonalTiles.aV;
            GEN.WORLD[76][hudHeight] = PersonalTiles.aO;
            GEN.WORLD[77][hudHeight] = PersonalTiles.aI;
            GEN.WORLD[78][hudHeight] = PersonalTiles.aD;
        }
        if (tile == "wall") {
            GEN.WORLD[74][hudHeight] = PersonalTiles.hud;
            GEN.WORLD[75][hudHeight] = PersonalTiles.aW;
            GEN.WORLD[76][hudHeight] = PersonalTiles.aA;
            GEN.WORLD[77][hudHeight] = PersonalTiles.aL;
            GEN.WORLD[78][hudHeight] = PersonalTiles.aL;
        }
        if (tile == "floor") {
            GEN.WORLD[74][hudHeight] = PersonalTiles.aF;
            GEN.WORLD[75][hudHeight] = PersonalTiles.aL;
            GEN.WORLD[76][hudHeight] = PersonalTiles.aO;
            GEN.WORLD[77][hudHeight] = PersonalTiles.aO;
            GEN.WORLD[78][hudHeight] = PersonalTiles.aR;
        }
        if (tile == "lamp") {
            GEN.WORLD[74][hudHeight]  = PersonalTiles.hud;
            GEN.WORLD[75][hudHeight] = PersonalTiles.aL;
            GEN.WORLD[76][hudHeight] = PersonalTiles.aA;
            GEN.WORLD[77][hudHeight] = PersonalTiles.aM;
            GEN.WORLD[78][hudHeight] = PersonalTiles.aP;
            GEN.WORLD[79][hudHeight] = PersonalTiles.hud;
        }
    }

    public String mouseTile() {
        double xCord = StdDraw.mouseX();
        double yCord = StdDraw.mouseY();
        String type = "void";
        try {
            TETile area = GEN.WORLD[(int) xCord][(int) yCord];
        } catch (Exception ArrayIndexOutOfBounds) {
            return " ";
        }

        TETile area = GEN.WORLD[(int) xCord][(int) yCord];

        if (area == PersonalTiles.pFloor4 || area == PersonalTiles.pFloor3 ||
                area == PersonalTiles.pFloor2 || area == PersonalTiles.pFloorInitial) {
            type = "floor";
            return type;
        } else if (PersonalTiles.checkWall(area)) {
            type = "wall";
            return type;
        } else if (area == PersonalTiles.onLamp || area == PersonalTiles.offLamp) {
            type = "lamp";
            return type;
        }
        return type;
    }

    public boolean options() {

        if (getNextKey(false, false) == 'q') {
            SaveLoad save = new SaveLoad();

            Position p = GEN.avatarCord;
            int[] avatarCord = new int[2];
            avatarCord[0] = p.x;
            avatarCord[1] = p.y;
            save.Save(GEN.SEED, avatarCord, GEN.lampsPosition, GEN.lampsPower, colorOption, hearts);
            quit();
            return true;
        }
        return false;
    }

    public void load() {
        boolean power;
        int[] avatarCords = new int[2];

        SaveLoad load = new SaveLoad();
        long[] loadInfo = load.Load();
        long seed = loadInfo[0];
        avatarCords[0] = (int) loadInfo[1];
        avatarCords[1] = (int) loadInfo[2];
        hearts = (int) loadInfo[4];

        PersonalTiles.changeColor((int)loadInfo[3]);

        GEN = new TestWorld(seed, WIDTH, HEIGHT);

        HashMap<ArrayList<Integer>, Boolean> lampsPower = new HashMap<>();
        ArrayList<Position> lampsPosition= new ArrayList<>();

        for (int i = 5; i < loadInfo.length; i += 3) {
            Position p = new Position( (int) loadInfo[i], (int) loadInfo[i + 1]);
            ArrayList<Integer> cords = new ArrayList<>();
            cords.add(p.x);
            cords.add(p.y);

            if (loadInfo[i + 2] == 1) {
                power = true;
            } else {
                power = false;
            }
            lampsPosition.add(p);
            lampsPower.put(cords, power);
        }

        GEN.createWorld(true);
        GEN.loadWorld(avatarCords, lampsPosition, lampsPower);
        gamePrompt();


    }

    public void quit() {
        if (stringInput) {
            return;
        }
        System.exit(0);
    }

    public void gamePrompt() {
        clear();
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(fontBig());
        StdDraw.text(WIDTH / 2, (HEIGHT / 2), "You're scared of the dark,");
        StdDraw.text(WIDTH / 2, (HEIGHT / 2) - 2, "turn on all the lights!");
        StdDraw.setFont(fontSmall());
        StdDraw.show();
        StdDraw.pause(2500);
        hearts();
        time = 500;
        ter.renderFrame(GEN.WORLD);
        charMove();
    }

    public boolean winScreen() {
        if (GEN.GM.win()) {
            clear();
            Font winFont = new Font("Monaco", Font.BOLD, 35);
            StdDraw.setFont(winFont);
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.picture(WIDTH / 2, HEIGHT / 2, "Png/win.png");
            StdDraw.text(WIDTH / 2, HEIGHT / 2, "You've completed the game!");
            StdDraw.text(WIDTH / 2, HEIGHT / 2.25, "Press space to go back to menu");
            StdDraw.show();
            return true;
        }
        return false;
    }

}
