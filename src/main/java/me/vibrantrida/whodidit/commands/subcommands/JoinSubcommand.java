package me.vibrantrida.whodidit.commands.subcommands;

import me.vibrantrida.whodidit.commands.ICommand;
import me.vibrantrida.whodidit.managers.QueueManager;
import me.vibrantrida.whodidit.managers.RoundManager;
import me.vibrantrida.whodidit.managers.RoundManager.RoundState;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

        if (RoundManager.getInstance().getState() == RoundState.PLAY) {
            if (QueueManager.getInstance().getPlayer((Player) src) != null) {
                src.sendMessage(ChatColor.YELLOW + "You already queued for the next round.");
                
                return false;
            } else {
                QueueManager.getInstance().addPlayer((Player) src);
                src.sendMessage(ChatColor.GREEN + "You queued for the next round!");
            }
        } else if (RoundManager.getInstance().getState() == RoundState.WAIT) {
            if (QueueManager.getInstance().getPlayer((Player) src) != null) {
                src.sendMessage(ChatColor.YELLOW + "You already joined this round.");

                return false;
            } else {
                QueueManager.getInstance().addPlayer((Player) src);
                src.sendMessage(ChatColor.GREEN + "You joined this round!");
                
                RoundManager.getInstance().startCountdown();
            }
        }

        return true;
    }
}
