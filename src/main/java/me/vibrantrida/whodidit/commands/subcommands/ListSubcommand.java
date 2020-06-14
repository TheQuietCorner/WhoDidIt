package me.vibrantrida.whodidit.commands.subcommands;

import me.vibrantrida.whodidit.commands.ICommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ListSubcommand implements ICommand {
    @Override
    public boolean onCommand(CommandSender src, Command cmd, String label, String[] args) {
        if (!src.hasPermission("whodidit.command.list")) {
            src.sendMessage(ChatColor.RED + "Sorry! You can't use that command!");

            return false;
        }

        // print all players participating in the current round

        src.sendMessage(ChatColor.GREEN + "You used the 'list' command!");

        return true;
    }
}
