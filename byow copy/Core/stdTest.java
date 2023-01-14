package byow.Core;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class stdTest {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 60;

    public static void setUpCanvas() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLUE);
        StdDraw.enableDoubleBuffering();
    }

    public static void setUpFont() {
        StdDraw.setPenColor(Color.WHITE);
    }


    public static void menu() {
        StdDraw.clear(Color.BLUE);
        Font fontBig = new Font("Monaco", Font.BOLD, 30);
        Font fontSmall = new Font("Monaco", Font.BOLD, 20);

        StdDraw.setFont(fontBig);
        StdDraw.text(WIDTH / 2, HEIGHT / 1.5, "CS61B: THE GAME");

        StdDraw.setFont(fontSmall);
        StdDraw.text(WIDTH / 2, (HEIGHT / 2), "New Game (N)");
        StdDraw.text(WIDTH / 2, (HEIGHT / 2) - 1, "Load Game (L)");
        StdDraw.text(WIDTH / 2, (HEIGHT / 2) - 2, "Quit (Q)");
        StdDraw.show();

        menuCheck();
    }

    public static void menuCheck() {
        boolean rightPress = false;

        while (!rightPress) {
            if (StdDraw.hasNextKeyTyped()) {
                char currChar = StdDraw.nextKeyTyped();
                if (currChar == 'z') {
                    yay();
                    rightPress = true;

                }
                if (currChar == 'n') {
                    typingSeed();
                    rightPress = true;
                }
            }
        }
    }
    public static void yay() {
        StdDraw.clear(Color.YELLOW);
        StdDraw.setPenColor(Color.BLACK);
        Font fontBig = new Font("Monaco", Font.BOLD, 60);

        StdDraw.text(WIDTH / 2, (HEIGHT / 2), "YAY!!!");
        StdDraw.show();
    }

    public static void typingSeed() {
        StdDraw.clear(Color.BLUE);
        StdDraw.show();

        String seed = "";
        boolean done = false;

        while (!done) {
            if (StdDraw.hasNextKeyTyped()) {
                char currChar = StdDraw.nextKeyTyped();
                if (currChar == 's') {
                    done = true;
                }
                else {
                    seed += currChar;
                }
                drawCenter(seed);
            }
        }
        yay();
    }

    public static void drawCenter(String response) {
        StdDraw.clear(Color.BLUE);
        Font fontBig = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(fontBig);

        StdDraw.text((WIDTH / 2), (HEIGHT / 2), response);
        StdDraw.show();

    }

    public static void typeCheck() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                System.out.println(StdDraw.nextKeyTyped());
            }
        }
    }
    public static void main(String[] args) {
        int[] ints= new int[2];
        ints[0] = 2;
        ints[1] = 3;
        SaveLoad s = new SaveLoad();
    }
}
