package me.nahu.badsheep.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import me.nahu.badsheep.player.VideoPlayer;
import me.nahu.badsheep.screen.SheepScreen;
import me.nahu.badsheep.util.JcodecDecoder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jcodec.api.JCodecException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@CommandAlias("videoplayer|play")
public class VideoPlayerCommand extends BaseCommand {
    private final Plugin plugin;

    public VideoPlayerCommand(@NotNull Plugin plugin) {
        this.plugin = plugin;
    }

    @Default
    @CommandCompletion("@files")
    public void play(
        @NotNull Player player,
        @NotNull String fileName
    ) {
        var file = JcodecDecoder.getFileInPath(fileName, plugin.getDataFolder());
        if (file.isEmpty()) {
            player.sendMessage("Could not find a file going by that name.");
            return;
        }

        var sheepScreen = new SheepScreen(32, 18, player.getLocation());
        Bukkit.broadcastMessage("Deployed screen.");

        CompletableFuture.runAsync(() -> {
            try {
                Bukkit.broadcastMessage("Loading frames...");
                var frames = JcodecDecoder.getAllFramesFromVideo(
                    file.get(),
                    sheepScreen.getWidth(),
                    sheepScreen.getHeight()
                );

                Bukkit.broadcastMessage("Starting!");
                var videoPlayer = new VideoPlayer(sheepScreen, frames);

                videoPlayer.start(plugin);
            } catch (IOException | JCodecException exception) {
                player.sendMessage("Could not load video!");
                exception.printStackTrace();
            }
        });
    }
}
