package me.vibrantrida.whodidit.commands.subcommands;

import me.vibrantrida.whodidit.commands.ICommand;
import me.vibrantrida.whodidit.managers.RoundManager;
import me.vibrantrida.whodidit.managers.RoundManager.RoundState;

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

        if (RoundManager.getInstance().getState() == RoundState.PLAY) {
            src.sendMessage(
                    ChatColor.GREEN + Integer.toString(RoundManager.getInstance().getPlayerCount()) + " Players:");
            RoundManager.getInstance().getPlayers().forEach((uuid, player) -> {
                src.sendMessage(ChatColor.GREEN + player.getDisplayName());
            });
        } else if (RoundManager.getInstance().getState() == RoundState.WAIT) {
            src.sendMessage(ChatColor.YELLOW + "No players found! Join to play in this round!");
        } else {
            src.sendMessage(ChatColor.YELLOW + "No players found! Join to queue for the next round!");
        }

        return true;
    }
}
