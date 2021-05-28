package me.nahu.badsheep.screen;

import me.nahu.badsheep.util.ColorUtil;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Sheep;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Objects;

public class SheepScreen implements Screen {
    private final Location center;
    private final Sheep[] pixels;

    private final int width;
    private final int height;

    public SheepScreen(int width, int height, @NotNull Location center) {
        this.width = width;
        this.height = height;
        this.center = center;
        this.pixels = new Sheep[width * height];
        build();
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @NotNull
    @Override
    public Location getCenter() {
        return center;
    }

    @Override
    public void renderImage(@NotNull BufferedImage bufferedImage) {
        for (int index = 0; index < pixels.length; index++) {
            var color = new Color(
                bufferedImage.getRGB(index % width, height - index / width - 1)
            );
            pixels[index].setColor(
                ColorUtil.getClosestColor(
                    org.bukkit.Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue())
                )
            );
        }
    }

    @Override
    public void delete() {
        Arrays.stream(pixels)
            .filter(Objects::nonNull)
            .filter(Entity::isValid)
            .forEach(Entity::remove);
    }

    private void build() {
        for (int index = 0; index < pixels.length; index++) {
            var location = center.clone();
            var offset = new Vector(0, index / width, index % width)
                .multiply(0.8); // sheep width, kinda
            location.add(offset);

            var sheep = getCenter().getWorld().spawn(location, Sheep.class, it -> {
                it.setGravity(false);
                it.setSheared(false);
                it.setAI(false);
                it.setCollidable(false);
                it.setPersistent(true);
                it.setColor(DyeColor.GREEN);
            });
            pixels[index] = sheep;
        }
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
