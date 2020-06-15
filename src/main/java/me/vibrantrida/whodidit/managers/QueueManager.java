package me.vibrantrida.whodidit.managers;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

public class QueueManager {
    private static QueueManager instance = new QueueManager();

    private HashMap<UUID, Player> players = new HashMap<>();

    private QueueManager() {
    }

    public static QueueManager getInstance() {
        return instance;
    }

    public HashMap<UUID, Player> getPlayers() {
        return players;
    }

    public Player getPlayer(UUID uuid) {
        if (players.containsKey(uuid)) {
            return players.get(uuid);
        }

        return null;
    }

    public int getPlayerCount() {
        return players.size();
    }

    public Player getPlayer(Player player) {
        if (players.containsValue(player)) {
            return players.get(player.getUniqueId());
        }

        return null;
    }

    public HashMap<UUID, Player> addPlayer(Player player) {
        if (players.containsValue(player) == false) {
            players.put(player.getUniqueId(), player);
            return players;
        }
        return null;
    }

    public HashMap<UUID, Player> removePlayer(UUID uuid) {
        if (players.containsKey(uuid)) {
            players.remove(uuid);
            return players;
        }
        return null;
    }
}