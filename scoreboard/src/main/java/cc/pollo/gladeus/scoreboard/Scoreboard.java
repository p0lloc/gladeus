package cc.pollo.gladeus.scoreboard;

import cc.pollo.gladeus.scoreboard.builder.ScoreboardBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Scoreboard
 */
public abstract class Scoreboard {

    private Map<String, ScoreboardEntry> entries;

    protected Component title;
    private final Random random = ThreadLocalRandom.current();

    private static final Set<NamedTextColor> TEXT_COLORS = NamedTextColor.NAMES.values();

    public Scoreboard(Component title) {
        this.title = title;
    }

    /**
     * Creates the Bukkit scoreboard for the player
     *
     * @return generated Bukkit scoreboard
     */
    private org.bukkit.scoreboard.Scoreboard create() {

        entries = build().getEntries();

        // TODO this really needs refactoring, pretty messy

        org.bukkit.scoreboard.Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("s_" + random.nextInt(999999999), "dummy", title);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        List<NamedTextColor> colors = new ArrayList<>(TEXT_COLORS);

        int i = 0;
        for (Map.Entry<String, ScoreboardEntry> entry : entries.entrySet()) {
            NamedTextColor associatedColour = colors.get(random.nextInt(colors.size()));
            colors.remove(associatedColour); // Colour combination must be unique

            ScoreboardEntry value = entry.getValue();

            String teamEntry = ChatColor.valueOf(associatedColour.toString().toUpperCase(Locale.ROOT)) + ChatColor.RESET.toString();
            Team team = scoreboard.registerNewTeam("st_" + random.nextInt(999999999));

            team.addEntry(teamEntry);
            team.prefix(ScoreboardEntry.buildPrefix(associatedColour, value.getText()));
            objective.getScore(teamEntry).setScore(16 - i);
            value.setAssociatedColour(associatedColour);
            value.setTeam(team);

            i++;
        }

        return scoreboard;
    }

    /**
     * Constructs and shows the scoreboard to the player
     *
     * @param player player to show scoreboard to
     */
    public void show(Player player) {
        player.setScoreboard(create());
    }

    /**
     * Updates the scoreboard title, reconstructs and shows the scoreboard to the player
     *
     * @param title  new title
     * @param player player to update for
     */
    public void updateTitle(Component title, Player player) {
        this.title = title;
        show(player);
    }

    public ScoreboardEntry getEntryById(String id){
        return entries.get(id);
    }

    public abstract ScoreboardBuilder build();

}