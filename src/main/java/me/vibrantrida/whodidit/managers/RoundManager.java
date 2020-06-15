package me.vibrantrida.whodidit.managers;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import me.vibrantrida.whodidit.WhoDidIt;
import me.vibrantrida.whodidit.files.ConfigFile;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class RoundManager {
    private static RoundManager instance = new RoundManager();

    private HashMap<UUID, Player> players = new HashMap<>();

    private BukkitTask task;

    public enum RoundState {
        WAIT, PLAY, END
    }

    private RoundState state = RoundState.WAIT;

    private RoundManager() {
    }

    public static RoundManager getInstance() {
        return instance;
    }

    public BukkitTask getTask() {
        return task;
    }

    public RoundState getState() {
        return state;
    }

    public RoundState setState(RoundState newState) {
        state = newState;
        return state;
    }

    public HashMap<UUID, Player> getPlayers() {
        return players;
    }

    public int getPlayerCount() {
        return players.size();
    }

    public HashMap<UUID, Player> populatePlayers() {
        players.putAll(QueueManager.getInstance().getPlayers());
        return players;
    }

    public void clearPlayers() {
        players.clear();
    }

    public void startCountdown() {
        if (players.size() < ConfigFile.getInstance().getConfig().getInt("game.minimum-players")) {
            populatePlayers();
        } else {
            task = Bukkit.getServer().getScheduler().runTaskTimer(WhoDidIt.getInstance(), new Runnable() {
                int i = ConfigFile.getInstance().getConfig().getInt("game.countdown") + 1;

                @Override
                public void run() {
                    i--;

                    if (i % 10 == 0 || i <= 10) {
                        players.forEach((uuid, player) -> {
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                                    new ComponentBuilder("Starting in " + Integer.toString(i)).create());
                        });
                    }
                    if (i <= 1) {
                        RoundManager.getInstance().getTask().cancel();
                        startGame();
                    }
                }

            }, 0L, 20L);
        }
    }

    public void startGame() {
        players.forEach((uuid, player) -> {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder("Game has started!").create());
        });
        // TODO: pick a killer
    }
}