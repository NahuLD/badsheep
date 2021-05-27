package me.nahu.badsheep;

import co.aikar.commands.BukkitCommandManager;
import me.nahu.badsheep.command.VideoPlayerCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class BadSheepPlugin extends JavaPlugin {
    private BukkitCommandManager commandManager;

    @Override
    public void onEnable() {
        getDataFolder().mkdir(); // Create directory, just in case.

        commandManager = new BukkitCommandManager(this);
        commandManager.getCommandCompletions().registerAsyncCompletion(
            "files",
            context -> Arrays.stream(Optional.ofNullable(getDataFolder().listFiles())
                .orElse(new File[0]))
                .map(File::getName)
                .collect(Collectors.toList())
        );
        commandManager.registerCommand(
            new VideoPlayerCommand(this)
        );
    }

    @Override
    public void onDisable() {
        commandManager.unregisterCommands();
    }
}
