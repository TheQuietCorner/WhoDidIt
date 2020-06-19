package me.vibrantrida.whodidit.managers;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import me.vibrantrida.whodidit.utils.DebugLogger;

public class QueueManager {
  private static QueueManager instance = new QueueManager();

  private QueueManager() {
    DebugLogger.info("QueueManager initialized");
  }

  public static QueueManager getInstance() {
    return instance;
  }

  private HashMap<UUID, Player> players = new HashMap<>();

  public HashMap<UUID, Player> getPlayers() {
    return players;
  }

  public int getPlayerCount() {
    DebugLogger.debug("QueueManager: player count is " + Integer.toString(players.size()));
    return players.size();
  }

  public Player addPlayer(Player player) {
    if (players.containsKey(player.getUniqueId()) == true) {
      return null;
    }
    DebugLogger.debug("QueueManager: added player with UUID: " + player.getUniqueId().toString());
    return players.put(player.getUniqueId(), player);
  }

  public Player getPlayer(UUID uuid) {
    if (players.containsKey(uuid) == false) {
      return null;
    }
    return players.get(uuid);
  }

  public Player removePlayer(UUID uuid) {
    if (players.containsKey(uuid) == false) {
      return null;
    }
    DebugLogger.debug("QueueManager: removed player with UUID: " + uuid.toString());
    return players.remove(uuid);
  }
}