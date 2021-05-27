package me.nahu.badsheep.screen;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

public interface Screen {
    /**
     * Get the width of the screen.
     * @return Width in blocks of the screen.
     */
    int getWidth();

    /**
     * Get the height of the screen.
     * @return Height in blocks of the screen.
     */
    int getHeight();

    /**
     * Get the center location for this screen.
     * @return Center location.
     */
    @NotNull
    Location getCenter();

    /**
     * Display the image given.
     * @param bufferedImage Buffered image to display.
     */
    void renderImage(@NotNull BufferedImage bufferedImage);

    /**
     * Check if the screen is properly set up.
     * @return Whether it is set up.
     */
    boolean isValid();

    /**
     * Delete the screen.
     */
    void delete();
}
