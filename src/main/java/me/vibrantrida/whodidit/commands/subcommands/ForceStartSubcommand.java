package me.vibrantrida.whodidit.commands.subcommands;

import me.vibrantrida.whodidit.commands.ICommand;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ForceStartSubcommand implements ICommand {
	@Override
	public boolean onCommand(CommandSender src, Command cmd, String label, String[] args) {
        if (!src.hasPermission("whodidit.command.forcestart")) {
            src.sendMessage(ChatColor.RED + "Sorry! You can't use that command!");

            return false;
        }

        // check if current round is waiting for players
        // and if so, force start the round regardless of the number of participating players 
        // else, tell player that the round is already on-going

        src.sendMessage(ChatColor.GREEN + "You used the 'forcestart' command!");

        return true;
	}
    
}