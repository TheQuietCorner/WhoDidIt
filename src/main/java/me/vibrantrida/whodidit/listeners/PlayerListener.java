package me.vibrantrida.whodidit.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import me.vibrantrida.whodidit.managers.GameManager;
import me.vibrantrida.whodidit.utils.DebugLogger;

public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        GameManager.getInstance().addPlayer(player);
        GameManager.getInstance().startGameTask();
        player.setGameMode(GameMode.SPECTATOR);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        GameManager.getInstance().getPlayers().remove(player.getUniqueId());
        if (GameManager.getInstance().getKiller().getUniqueId() == player.getUniqueId()) {
            String message = player.getKiller().getDisplayName() + " has killed the killer! The Survivors, wins!";
            DebugLogger.info(message);
            event.setDeathMessage(message);
            GameManager.getInstance().cancelGameTask();
        } else {
            String message;
            if (GameManager.getInstance().getPlayers().size() > 1) {
                message = player.getDisplayName() + " has perished. Only "
                        + Integer.toString(GameManager.getInstance().getPlayers().size() - 1)
                        + " survivors remaining...";
            } else {
                message = player.getDisplayName() + ", the last survivor, perished. The Killer, wins!";
            }
            DebugLogger.info(message);
            event.setDeathMessage(message);
        }
    }
}
