package me.vibrantrida.whodidit.managers;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import me.vibrantrida.whodidit.WhoDidIt;
import me.vibrantrida.whodidit.utils.DebugLogger;

public class GameManager {
    private static GameManager instance = new GameManager();

    private HashMap<UUID, Player> players;
    private Player killer;
    private int minimumPlayers;

    private BukkitTask task;

    public enum GameState {
        WAIT, PRE, PLAY, POST
    }

    private GameState state = GameState.WAIT;

    private GameManager() {
        players = new HashMap<>();
        minimumPlayers = WhoDidIt.getInstance().getConfig().getInt("game.minimum-players");
    }

    public static GameManager getInstance() {
        return instance;
    }

    public GameState getState() {
        return state;
    }

    public int getMinimumPlayers() {
        return minimumPlayers;
    }

    public HashMap<UUID, Player> getPlayers() {
        return players;
    }

    public HashMap<UUID, Player> addPlayer(Player player) {
        if (getPlayers().containsValue(player) == true) {
            return null;
        }
        players.put(player.getUniqueId(), player);
        return getPlayers();
    }

    public void startGameTask() {
        if (task == null) {
            task = Bukkit.getServer().getScheduler().runTaskTimer(WhoDidIt.getInstance(), new Runnable() {
                int countdown = WhoDidIt.getInstance().getConfig().getInt("game.countdown") + 1;

                @Override
                public void run() {
                    if (getState() == GameState.WAIT) {
                        if (getPlayers().size() < getMinimumPlayers()) {
                            String message = "Waiting for "
                                    + Integer.toString(getMinimumPlayers() - getPlayers().size())
                                    + " or more players...";
                            DebugLogger.info(message);
                            broadcastMessage(message);
                            return;
                        }
                        String message = "Minimum player count required met/exceeded! Starting pre-game...";
                        DebugLogger.info(message);

                        getPlayers().forEach((uuid, player) -> {
                            player.setGameMode(GameMode.SURVIVAL);
                        });

                        state = GameState.PRE;
                        return;
                    } else if (getState() == GameState.PRE) {
                        if (getPlayers().size() < getMinimumPlayers()) {
                            countdown = WhoDidIt.getInstance().getConfig().getInt("game.countdown");
                            state = GameState.WAIT;
                        } else {
                            countdown--;
                            if (countdown % 10 == 0) {
                                String message = "A killer will be chosen in " + Integer.toString(countdown)
                                        + " seconds";
                                DebugLogger.info(message);
                                broadcastMessage(message);
                                return;
                            }
                            if (countdown <= 10 && countdown > 1) {
                                String message = "A killer will be chosen in " + Integer.toString(countdown);
                                DebugLogger.info(message);
                                broadcastMessage(message);
                                return;
                            }
                            if (countdown <= 1) {
                                String message = "A killer has been chosen...";
                                DebugLogger.info(message);
                                broadcastMessage(message);

                                UUID killerUUID = pickKiller();

                                killer = getPlayers().get(killerUUID);
                                killer.sendMessage("...and it is YOU!");
                                cancelGameTask();
                            }
                            return;
                        }
                        return;
                    }
                    return;
                }
            }, 20L, 20L);
        }
    }

    public void cancelGameTask() {
        if (task == null) {
            return;
        }
        task.cancel();
        task = null;
    }

    public UUID pickKiller() {
        Object[] keySet = getPlayers().keySet().toArray();
        UUID randomUUID = (UUID) keySet[new Random().nextInt(getPlayers().keySet().toArray().length)];

        return randomUUID;
    }

    public Player getKiller() {
        if (killer == null) {
            return null;
        }
        return killer;
    }

    public void broadcastMessage(String message) {
        if (getPlayers().size() > 0) {
            getPlayers().forEach((uuid, player) -> {
                player.sendMessage(message);
            });
        }
    }
}