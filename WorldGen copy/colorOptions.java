package WorldGen;

import java.awt.*;

public class colorOptions {
    public static Color zeroPink = new Color(92, 0, 69);
    public static Color onePink= new Color(133, 0, 99);
    public static Color twoPink = new Color(158, 0, 119);
    public static Color threePink =  new Color(184, 0, 138);
    public static Color fourPink = new Color(219, 0, 164);
    public static Color lampPink = new Color(255, 20, 196);

    public static Color zeroPurp = new Color(39, 0, 107);
    public static Color onePurp = new Color(50, 0, 138);
    public static Color twoPurp = new Color(64, 0, 173);
    public static Color threePurp =  new Color(79, 0, 214);
    public static Color fourPurp = new Color(93, 0, 255);
    public static Color lampPurp = new Color(116, 36, 255);

    public static Color[] listColor(int option) {
        if (option == 0) {
            return new Color[0];
        } else if (option == 1) {
            Color[] pinkShades = new Color[6];
            pinkShades[0] = zeroPink;
            pinkShades[1] = onePink;
            pinkShades[2] = twoPink;
            pinkShades[3] = threePink;
            pinkShades[4] = fourPink;
            pinkShades[5] = lampPink;
            return pinkShades;
        } else {
            Color[] purpShades = new Color[6];
            purpShades[0] = zeroPurp;
            purpShades[1] = onePurp;
            purpShades[2] = twoPurp;
            purpShades[3] = threePurp;
            purpShades[4] = fourPurp;
            purpShades[5] = lampPurp;
            return purpShades;
        }
    }
}
