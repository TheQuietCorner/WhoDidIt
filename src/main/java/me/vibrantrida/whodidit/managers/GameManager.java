package me.vibrantrida.whodidit.managers;

import java.util.Map;
import java.util.UUID;

import org.bukkit.scheduler.BukkitTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.vibrantrida.whodidit.WhoDidIt;
import me.vibrantrida.whodidit.files.ConfigFile;
import me.vibrantrida.whodidit.game.Game;
import me.vibrantrida.whodidit.utils.DebugLogger;

public class GameManager {
    private static GameManager instance = new GameManager();

    private GameManager() {
        DebugLogger.info("GameManager initialized");
        task = Bukkit.getServer().getScheduler().runTaskTimer(WhoDidIt.getInstance(), new Runnable() {
            @Override
            public void run() {
                matchPlayers();
            }
        }, 1200L, 1200L);
    }

    public static GameManager getInstance() {
        return instance;
    }

    private Game game = new Game();
    private BukkitTask task;

    public void matchPlayers() {
        DebugLogger.debug("GameManager: matching players...");
        if (QueueManager.getInstance().getPlayerCount() > 0) {
            DebugLogger.debug("GameManager: players found in queue!");
            DebugLogger.debug("GameManager: game uuid: " + game.getUUID().toString());
            if (game.getPlayerCount() < ConfigFile.getInstance().getMaximumPlayers()) {
                DebugLogger.debug("GameManager: game player count is less than maximum players");
                int numberOfPlayersToMatch = ConfigFile.getInstance().getMaximumPlayers() - game.getPlayerCount();
                DebugLogger.debug("GameManager: to meet maximum player count, "
                        + Integer.toString(numberOfPlayersToMatch) + " players are needed");
                if (QueueManager.getInstance().getPlayerCount() >= numberOfPlayersToMatch) {
                    DebugLogger.debug(
                            "GameManager: the player queue has enough players to fulfill the open slots in this game! adding players...");
                    for (int i = 0; i < numberOfPlayersToMatch; i++) {
                        Map.Entry<UUID, Player> entry = QueueManager.getInstance().getPlayers().entrySet().iterator()
                                .next();
                        UUID uuid = entry.getKey();
                        Player player = entry.getValue();

                        QueueManager.getInstance().removePlayer(uuid);
                        game.addPlayer(player);
                        DebugLogger.debug("GameManager: added player: " + uuid.toString());
                    }
                } else {
                    DebugLogger.debug("GameManager: no queued players");
                }
            } else {
                DebugLogger.debug("GameManager: game is full!");
            }
        }
    }
}