package me.vibrantrida.whodidit.commands.subcommands;

import me.vibrantrida.whodidit.commands.ICommand;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class JoinSubcommand implements ICommand {
    @Override
    public boolean onCommand(CommandSender src, Command cmd, String label, String[] args) {
        if (!src.hasPermission("whodidit.command.join")) {
            src.sendMessage(ChatColor.RED + "Sorry! You can't use that command!");

            return false;
        }

        // check if current round is on-going
        // and if so, check if player is already queued for the next round,
        // - and if so, tell player that they are already queued for the next round
        // - else, queue player for the next round
        // else, check if player is already in the current round
        // - and if so, tell player that they are already playing
        // - else, add player to the current round

        src.sendMessage(ChatColor.GREEN + "You used the 'join' command!");

        return true;
    }
}
