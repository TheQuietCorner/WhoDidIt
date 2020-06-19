package me.vibrantrida.whodidit;

import me.vibrantrida.whodidit.commands.BaseCommand;
import me.vibrantrida.whodidit.commands.CommandHandler;
import me.vibrantrida.whodidit.commands.subcommands.JoinSubcommand;
import me.vibrantrida.whodidit.commands.subcommands.ListSubcommand;
import me.vibrantrida.whodidit.commands.subcommands.SpectateSubcommand;
import me.vibrantrida.whodidit.files.ConfigFile;
import me.vibrantrida.whodidit.listeners.PlayerListener;
import me.vibrantrida.whodidit.managers.GameManager;
import me.vibrantrida.whodidit.managers.QueueManager;
import me.vibrantrida.whodidit.utils.DebugLogger;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class WhoDidIt extends JavaPlugin {
    private static JavaPlugin instance;

    private void registerEventListeners() {
        for (Listener listener : Arrays.asList(new PlayerListener())) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    private void registerCommands() {
        CommandHandler handler = new CommandHandler().register("whodidit", new BaseCommand())
                .register("join", new JoinSubcommand()).register("list", new ListSubcommand())
                .register("spectate", new SpectateSubcommand());
        PluginCommand command = getCommand("whodidit");
        command.setExecutor(handler);
    }

    @Override
    public void onEnable() {
        instance = this;

        // initialize configuration
        ConfigFile.getInstance().initialize();

        // initialize managers
        QueueManager.getInstance();
        GameManager.getInstance();

        // register event listeners and commands
        registerEventListeners();
        registerCommands();

        DebugLogger.info("WhoDidIt enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static JavaPlugin getInstance() {
        return instance;
    }
}