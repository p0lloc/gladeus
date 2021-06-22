package cc.pollo.gladeus.scoreboard;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.scoreboard.Team;

/**
 * Represents a entry in the scoreboard, holds team and display text state
 */
public class ScoreboardEntry {

    private Component text;

    private NamedTextColor associatedColour;
    private Team team;

    public ScoreboardEntry(Component text){
        this.text = text;
    }

    /**
     * Updates the value of this entry
     * @param text new value
     */
    public void update(Component text){
        this.text = text;

        if(team != null)
            team.prefix(buildPrefix(associatedColour, text));
    }

    /**
     * Gets the display text of this entry
     * @return display component
     */
    public Component getText() {
        return text;
    }

    /**
     * Creates a new scoreboard entry that has blank display text
     * @return blank entry
     */
    public static ScoreboardEntry blank(){
        return new ScoreboardEntry(Component.space());
    }

    public static Component buildPrefix(NamedTextColor color, Component component){
        return Component.text("")
                .append(Component.text("", color)
                                 .append(component));
    }

    public void setAssociatedColour(NamedTextColor associatedColour) {
        this.associatedColour = associatedColour;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

}