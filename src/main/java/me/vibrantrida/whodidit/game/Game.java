package me.vibrantrida.whodidit.game;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import me.vibrantrida.whodidit.utils.DebugLogger;
import me.vibrantrida.whodidit.utils.PlayerUtils;

public class Game {
    private UUID uuid = UUID.randomUUID();

    private GameState state = GameState.WAIT;

    private HashMap<UUID, Player> players = new HashMap<>();

    public HashMap<UUID, Player> getPlayers() {
        return players;
    }

    public UUID getUUID() {
        return uuid;
    }

    public GameState getState() {
        return state;
    }

    public GameState setState(GameState newState) {
        state = newState;
        DebugLogger.debug("Game: set state to '" + state.toString() + "'");
        return state;
    }

    public int getPlayerCount() {
        int count = players.size();
        DebugLogger.debug("Game: " + Integer.toString(count) + "players currently in-game");
        return count;
    }

    public Player addPlayer(Player player) {
        if (players.containsKey(player.getUniqueId()) == true) {
            return null;
        }
        DebugLogger.debug("Game: added player with UUID: " + player.getUniqueId().toString());
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
        DebugLogger.debug("Game: removed player with UUID: " + uuid.toString());
        return players.remove(uuid);
    }

    public void startPregame() {
        DebugLogger.debug("Game: started pre-game!");

        DebugLogger.debug("Game: setting state to 'PRE'");
        setState(GameState.PRE);

        DebugLogger.debug("Game: readying players...");
        players.forEach((uuid, player) -> {
            PlayerUtils.readyPlayer(player);
        });

        startGame();
    }

    public void startGame() {
        DebugLogger.debug("Game: started game!");

        DebugLogger.debug("Game: setting state to 'PLAY'");
        setState(GameState.PLAY);
    }
}