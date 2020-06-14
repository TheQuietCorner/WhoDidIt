package me.vibrantrida.whodidit.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface ICommand {
    boolean onCommand(CommandSender src, Command cmd, String label, String[] args);
}
