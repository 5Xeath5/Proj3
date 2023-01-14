package byow.Core;

import WorldGen.Position;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;

import java.util.ArrayList;
import java.util.HashMap;

public class SaveLoad {
    public static void Save(long randomSeed, int[] avatarCords, ArrayList<Position> lampPosition,
                            HashMap<ArrayList<Integer>, Boolean> lampPower, int colorOption, int hearts) {
        Out textPrint = new Out("./WorldSaveInfo.txt");
        textPrint.print(randomSeed);
        textPrint.print(' ');
        textPrint.print(avatarCords[0]);
        textPrint.print(' ');
        textPrint.print(avatarCords[1]);
        textPrint.print(' ');
        System.out.println(colorOption);
        textPrint.print(colorOption);
        textPrint.print(' ');
        textPrint.println(hearts);

        for (Position p : lampPosition) {
            ArrayList<Integer> cords = new ArrayList<>();
            cords.add(p.x);
            cords.add(p.y);

            textPrint.print(p.x);
            textPrint.print(' ');
            textPrint.print(p.y);
            textPrint.print(' ');
            if (lampPower.get(cords)) {
                textPrint.println(1);
            } else {
                textPrint.println(0);
            }
        }
    }

    /** @return int[0] = randomSeed
     *          int[1] = avatarXCord
     *          int[2] = avatarYCord*/
    public static long[] Load() {

        In readText = new In("./WorldSaveInfo.txt");
        long[] totalInfo = readText.readAllLongs();

        return totalInfo;
    }
}
