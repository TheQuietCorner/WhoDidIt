package me.vibrantrida.whodidit.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.vibrantrida.whodidit.files.ConfigFile;
import me.vibrantrida.whodidit.game.Game;
import me.vibrantrida.whodidit.game.GameState;
import me.vibrantrida.whodidit.managers.GameManager;
import me.vibrantrida.whodidit.managers.QueueManager;
import me.vibrantrida.whodidit.utils.DebugLogger;
import me.vibrantrida.whodidit.utils.PlayerUtils;

public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        QueueManager.getInstance().addPlayer(player);

        PlayerUtils.clearInventory(player);
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
        DebugLogger.debug("PlayerListener: A player disconnected!");
        Player player = event.getPlayer();

        if (QueueManager.getInstance().getPlayer(player.getUniqueId()) != null) {
            DebugLogger.debug("PlayerListener: Removing disconnected player from queue...");
            QueueManager.getInstance().removePlayer(player.getUniqueId());
        }
        if (GameManager.getInstance().getGame().getPlayer(player.getUniqueId()) != null) {
            DebugLogger.debug("PlayerListener: Removing disconnected player from game...");
            Game game = GameManager.getInstance().getGame();
            game.removePlayer(player.getUniqueId());
            if ((game.getState() == GameState.PRE) || (game.getState() == GameState.PLAY)) {
                if (game.getPlayerCount() < ConfigFile.getInstance().getMinimumPlayers()) {
                    DebugLogger.debug("PlayerListener: Players in-game is less than required minimum!");
                    game.setState(GameState.WAIT);

                    DebugLogger.debug("PlayerListener: Switching players into spectator mode...");
                    game.getPlayers().forEach((uuid, gamePlayer) -> {
                        PlayerUtils.clearInventory(gamePlayer);
                        gamePlayer.setGameMode(GameMode.SPECTATOR);
                    });
                }
            }
        }
    }

    @EventHandler
    public void onHungerDeplete(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
}
