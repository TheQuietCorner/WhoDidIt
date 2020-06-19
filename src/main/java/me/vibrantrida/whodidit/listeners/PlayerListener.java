package me.vibrantrida.whodidit.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.vibrantrida.whodidit.managers.QueueManager;

public class PlayerListener implements Listener {
    private void clearInventory(Player player) {
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        player.getInventory().clear();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        QueueManager.getInstance().addPlayer(player);

        clearInventory(player);
        player.setGameMode(GameMode.SPECTATOR);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = (Player) event.getEntity();

        event.setDeathMessage(null);

        player.setGameMode(GameMode.SPECTATOR);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        QueueManager.getInstance().removePlayer(player.getUniqueId());
    }

    @EventHandler
    public void onHungerDeplete(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
}
