package cc.pollo.gladeus.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Manages assigning scoreboards to players
 */
public class ScoreboardManager {

    private final Map<UUID, Scoreboard> scoreboardMap = new HashMap<>();

    public void showScoreboard(Player player, Scoreboard scoreboard){
        setScoreboard(player, scoreboard);
        scoreboard.show(player);
    }

    public void setScoreboard(Player player, Scoreboard scoreboard){
        if(scoreboardMap.containsKey(player.getUniqueId()))
            clearScoreboard(player);

        scoreboardMap.put(player.getUniqueId(), scoreboard);
    }

    public Scoreboard getScoreboard(Player player){
        return scoreboardMap.get(player.getUniqueId());
    }

    public void clearScoreboard(Player player){
        scoreboardMap.remove(player.getUniqueId());

        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

}
