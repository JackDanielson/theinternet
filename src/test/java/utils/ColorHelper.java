package utils;

import java.awt.*;

public class ColorHelper {

    public static String convertHexToIntForCSS(String hex) {
        hex = hex.replace("#", "").trim();
        int resultRed = Integer.valueOf(hex.substring(0, 2), 16);
        int resultGreen = Integer.valueOf(hex.substring(2, 4), 16);
        int resultBlue = Integer.valueOf(hex.substring(4, 6), 16);

        return String.format("rgb(%d, %d, %d)", resultRed, resultGreen, resultBlue);
    }
}
