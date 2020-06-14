package me.vibrantrida.whodidit.utils;

import me.vibrantrida.whodidit.files.ConfigFile;
import me.vibrantrida.whodidit.files.ConfigFile.DebugLevel;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class DebugLogger {
    private static Logger log = Bukkit.getLogger();
    private static DebugLevel level = ConfigFile.getInstance().getDebugLevel();

    public static void error(String string) {
        if (DebugLevel.ERROR.compareTo(level) <= 0) {
            log.info(ChatColor.RED + "[ERROR] " + string);
        }
    }

    public static void warn(String string) {
        if (DebugLevel.WARN.compareTo(level) <= 0) {
            log.info(ChatColor.YELLOW + "[WARN] " + string);
        }
    }

    public static void info(String string) {
        if (DebugLevel.INFO.compareTo(level) <= 0) {
            log.info(ChatColor.GREEN + "[INFO] " + string);
        }
    }

    public static void debug(String string) {
        if (DebugLevel.DEBUG.compareTo(level) <= 0) {
            log.info(ChatColor.LIGHT_PURPLE + "[DEBUG] " + string);
        }
    }
}
