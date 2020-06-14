package me.vibrantrida.whodidit.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.vibrantrida.whodidit.utils.DebugLogger;

import java.util.HashMap;

public class CommandHandler implements CommandExecutor {
    private final static HashMap<String, ICommand> COMMANDS = new HashMap<String, ICommand>();

    public boolean exists(String sublabel) {
        return COMMANDS.containsKey(sublabel);
    }

    public CommandHandler register(String sublabel, ICommand cmd) {
        if (!exists(sublabel)) {
            COMMANDS.put(sublabel, cmd);
        }

        return this;
    }

    public ICommand getSubcommand(String sublabel) {
        return COMMANDS.get(sublabel);
    }

    @Override
    public boolean onCommand(CommandSender src, Command cmd, String label, String[] args) {
        if (src instanceof Player == false) {
            DebugLogger.info("Only a player can use that command!");

            return false;
        }

        if (args.length == 0) {
            getSubcommand("whodidit").onCommand(src, cmd, label, args);

            return true;
        }

        if (exists(args[0])) {
            getSubcommand(args[0]).onCommand(src, cmd, label, args);

            return true;
        } else {
            src.sendMessage(ChatColor.RED + "Unknown sub-command '" + args[0] + "'");

            return true;
        }
    }
}
