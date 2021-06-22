package cc.pollo.gladeus.scoreboard.builder;

import cc.pollo.gladeus.scoreboard.ScoreboardEntry;
import net.kyori.adventure.text.Component;

import java.util.Map;

public interface ScoreboardBuilder {

    /**
     * Adds a Scoreboard entry that is identified by it's id and has a simple component
     *
     * @param id id of the entry
     * @param component component to display
     * @return this builder
     */
    ScoreboardBuilder id(String id, Component component);

    /**
     * Adds a Scoreboard entry that is identified by it's id and has an entry
     *
     * @param id id of the entry
     * @param entry scoreboard entry
     * @return this builder
     */
    ScoreboardBuilder id(String id, ScoreboardEntry entry);

    /**
     * Adds a Scoreboard entry which uses the plain content of <code>key</code><br>
     * and is formatted by the defined format function
     *
     * @param key key of the entry
     * @param value value of the entry
     * @return this builder
     */
    ScoreboardBuilder formattedPlain(Component key, Component value);

    /**
     * Adds a Scoreboard entry which uses the value of {@link this#getTranslatablePrefix()} and <code>id</code><br>
     * to construct a translatable component, and is formatted by the defined format function
     *
     * @param id id of the entry
     * @param value value of the entry
     * @return this builder
     */
    ScoreboardBuilder formattedTranslatable(String id, Component value);

    /**
     * Adds a Scoreboard entry which is formatted by the defined format function
     * @param id id of the entry
     * @param key key of the entry
     * @param value value of the entry
     * @return this builder
     */
    ScoreboardBuilder formatted(String id, Component key, Component value);

    /**
     * Adds all the entries from another builder
     * @param builder builder to add entries from
     * @return this builder
     */
    ScoreboardBuilder all(ScoreboardBuilder builder);

    /**
     * Adds an empty entry
     * @return this builder
     */
    ScoreboardBuilder empty();

    /**
     * Gets the prefix which is added to the key of all translatable entries
     * @return translatable prefix
     */
    String getTranslatablePrefix();

    /**
     * Gets all the entries of this builder
     * @return map of entries
     */
    Map<String, ScoreboardEntry> getEntries();

    static ScoreboardBuilder builder(){
        return builder(DefaultScoreboardBuilder.DEFAULT_FORMAT);
    }

    static ScoreboardBuilder builder(FormatFunction formatFunction){
        return builder(formatFunction, DefaultScoreboardBuilder.DEFAULT_TRANSLATABLE_PREFIX);
    }

    static ScoreboardBuilder builder(FormatFunction formatFunction, String translatablePrefix){
        return new DefaultScoreboardBuilder(formatFunction, translatablePrefix);
    }

    /**
     * A function that defines the format of how entries <br>
     * should be displayed on the scoreboard
     */
    @FunctionalInterface
    interface FormatFunction {

        /**
         * Generates text according to the format
         * @param key key of the entry, eg. "Players"
         * @param value value of the entry, eg. "2"
         * @return resulting text, eg "Players: 2"
         */
        Component format(Component key, Component value);
    }

}