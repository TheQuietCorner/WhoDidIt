package me.vibrantrida.whodidit.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class BaseCommand implements ICommand {
    @Override
    public boolean onCommand(CommandSender src, Command cmd, String label, String[] args) {
        if (!src.hasPermission("whodidit.player")) {
            src.sendMessage(ChatColor.RED + "Sorry! You can't use that command!");

            return false;
        }

        src.sendMessage(ChatColor.GREEN + "Hi from WhoDidIt!");

        return true;
    }

}
