package me.vibrantrida.whodidit.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.vibrantrida.whodidit.commands.ICommand;
import net.md_5.bungee.api.ChatColor;

public class SpectateSubcommand implements ICommand {
    @Override
    public boolean onCommand(CommandSender src, Command cmd, String label, String[] args) {
        if (!src.hasPermission("whodidit.command.spectate")) {
            src.sendMessage(ChatColor.RED + "Sorry! You can't use that command!");

            return false;
        }

        // if player is not a spectator, change player's game mode to spectate

        src.sendMessage(ChatColor.GREEN + "You used the 'spectate' command!");

        return false;
    }

}