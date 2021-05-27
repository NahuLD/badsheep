package me.nahu.badsheep.util;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Comparator;

public class ColorUtil {
    /**
     * Really ugly method written in a pinch to get the closest color to a given Bukkit equivalent.
     * @param bukkitColor Bukkit color to compare.
     * @return Closest color or default to black.
     */
    @NotNull
    public static DyeColor getClosestColor(@NotNull Color bukkitColor) {
        var bukkitRgb = bukkitColor.getRed() + bukkitColor.getGreen() + bukkitColor.getBlue();
        return Arrays.stream(DyeColor.values())
            .min(Comparator.comparingInt(dyeColor -> {
                var color = dyeColor.getColor();
                return Math.abs((color.getRed() + color.getGreen() + color.getBlue()) - bukkitRgb);
            }))
            .orElse(DyeColor.BLACK);
    }
}
