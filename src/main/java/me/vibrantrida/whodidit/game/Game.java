package me.vibrantrida.whodidit.game;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

public class Game {
  private UUID uuid = UUID.randomUUID();

  private HashMap<UUID, Player> players = new HashMap<>();

  public HashMap<UUID, Player> getPlayers() {
    return players;
  }

  public UUID getUUID() {
    return uuid;
  }

  public int getPlayerCount() {
    return players.size();
  }

  public Player addPlayer(Player player) {
    if (players.containsKey(player.getUniqueId()) == true) {
      return null;
    }
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
    return players.remove(uuid);
  }

}