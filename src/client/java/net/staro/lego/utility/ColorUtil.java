package net.staro.lego.utility;

import lombok.experimental.UtilityClass;

import java.awt.*;

@UtilityClass
public class ColorUtil {
    /**
     * Turns a hex string into a color variable.
     * @param hex is a hex string.
     * @return the decoded color.
     * @throws IllegalArgumentException handles the exceptions related to the incorrect hex string.
     */
    public Color hexToColor(String hex) throws IllegalArgumentException {
        if (hex.startsWith("#")) {
            hex = hex.substring(1);
        }

        if (hex.length() != 6) {
            throw new IllegalArgumentException("Invalid hex color code");
        }

        return Color.decode("#" + hex);
    }

}
