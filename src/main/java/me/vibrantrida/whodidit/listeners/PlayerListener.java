package me.vibrantrida.whodidit.listeners;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.vibrantrida.whodidit.managers.GameManager;
import me.vibrantrida.whodidit.managers.QueueManager;
import me.vibrantrida.whodidit.managers.GameManager.GameState;
import me.vibrantrida.whodidit.utils.DebugLogger;

public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if ((GameManager.getInstance().getState() == GameState.PLAY)
                || (GameManager.getInstance().getState() == GameState.POST)) {
            QueueManager.getInstance().addPlayer(player);
        } else {
            GameManager.getInstance().addPlayer(player);
            GameManager.getInstance().startGameTask();
        }
        player.setGameMode(GameMode.SPECTATOR);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        GameManager.getInstance().getPlayers().remove(player.getUniqueId());
        if (GameManager.getInstance().getKiller().getUniqueId() == player.getUniqueId()) {
            String message = player.getKiller().getDisplayName() + " has killed the killer! The Survivors, wins!";
            DebugLogger.info(message);
            event.setDeathMessage(ChatColor.YELLOW + message);
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
            event.setDeathMessage(ChatColor.YELLOW + message);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if (GameManager.getInstance().getPlayers().containsKey(player.getUniqueId())) {
            GameManager.getInstance().getPlayers().remove(player.getUniqueId());
        }

        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        player.getInventory().clear();

        player.setGameMode(GameMode.SPECTATOR);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (GameManager.getInstance().getState() != GameState.PLAY) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (GameManager.getInstance().getPlayers().containsKey(player.getUniqueId())) {
            player.getInventory().setHelmet(null);
            player.getInventory().setChestplate(null);
            player.getInventory().setLeggings(null);
            player.getInventory().setBoots(null);
            player.getInventory().clear();

            GameManager.getInstance().getPlayers().remove(player.getUniqueId());
        } else if (QueueManager.getInstance().getPlayer(player.getUniqueId()) != null) {
            player.getInventory().setHelmet(null);
            player.getInventory().setChestplate(null);
            player.getInventory().setLeggings(null);
            player.getInventory().setBoots(null);
            player.getInventory().clear();

            QueueManager.getInstance().removePlayer(player.getUniqueId());
        }
    }
}
