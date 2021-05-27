package me.nahu.badsheep.player;

import me.nahu.badsheep.screen.Screen;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;
import java.util.List;

public class VideoPlayer extends BukkitRunnable {
    private static final int FRAME_RATE = 1; // about 20 frames per second
    private final Screen screen;

    private final List<BufferedImage> frames;
    private int currentFrame = 0;

    public VideoPlayer(@NotNull Screen screen, @NotNull List<BufferedImage> frames) {
        this.screen = screen;
        this.frames = frames;
    }

    @Override
    public void run() {
        if (currentFrame >= frames.size() || frames.isEmpty()) {
            stop();
            return;
        }

        var image = frames.get(currentFrame);
        screen.renderImage(image);

        currentFrame++;
    }

    public void start(@NotNull Plugin plugin) {
        runTaskTimer(plugin, 1, FRAME_RATE);
    }

    public void stop() {
        frames.clear();
        cancel();
    }
}
