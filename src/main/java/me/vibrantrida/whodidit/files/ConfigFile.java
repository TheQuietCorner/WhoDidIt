package me.vibrantrida.whodidit.files;

import me.vibrantrida.whodidit.WhoDidIt;
import me.vibrantrida.whodidit.utils.DebugLogger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class ConfigFile {
    private static ConfigFile instance = new ConfigFile();

    public static ConfigFile getInstance() {
        return instance;
    }

    private ConfigFile() {
    }

    private static JavaPlugin plugin = WhoDidIt.getInstance();
    private static FileConfiguration config;
    private static File configFile;

    public enum DebugLevel {
        ERROR, WARN, INFO, DEBUG,
    }

    public ConfigFile initialize() {
        config = plugin.getConfig();
        config.options().copyDefaults(true);
        configFile = new File(plugin.getDataFolder(), "config.yml");
        saveConfig();
        return this;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public ConfigFile saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            DebugLogger.error("Failed to save config.yaml!");
            e.printStackTrace();
        }
        return this;
    }

    public DebugLevel getDebugLevel() {
        String level = config.getString("debug-level");
        Bukkit.getLogger().info("debug level is: " + level);
        switch (level) {
            case "error":
                return DebugLevel.ERROR;
            case "warn":
                return DebugLevel.WARN;
            case "info":
                return DebugLevel.INFO;
            case "debug":
                return DebugLevel.DEBUG;
        }
        return DebugLevel.INFO;
    }

    public ConfigFile reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
        return this;
    }
}
